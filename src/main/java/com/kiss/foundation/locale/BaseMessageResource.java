package com.kiss.foundation.locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import com.kiss.foundation.utils.ThreadLocalUtil;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class BaseMessageResource extends ResourceBundleMessageSource {

    @Value("${spring.messages.baseFolder:i18n}")
    public String baseFolder;

    @Value("${spring.messages.basename:codes}")
    public String basename;

    public static String I18N_ATTRIBUTE = "attribute";

    @PostConstruct
    public void init() {

        if (!StringUtils.isEmpty(baseFolder)) {
            try {
                this.setBasenames(getAllBaseNames(baseFolder));
                this.setDefaultEncoding("utf-8");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        ResourceBundleMessageSource parent = new ResourceBundleMessageSource();
        parent.setBasename(basename);
        parent.setDefaultEncoding("utf-8");
        this.setParentMessageSource(parent);
    }

    private String[] getAllBaseNames(String folderName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource(folderName);

        if (null == url) {
            throw new RuntimeException("无法获取资源文件路径");
        }

        List<String> baseNames = new ArrayList<>();

        if (url.getProtocol().equalsIgnoreCase("file")) {
            File file = new File(url.getFile());
            if (file.exists() && file.isDirectory()) {
                baseNames = Files.walk(file.toPath())
                        .filter(path -> path.toFile().isFile())
                        .map(Path::toString)
                        .map(path -> path.substring(path.indexOf(folderName)))
                        .map(this::getI18FileName)
                        .distinct()
                        .collect(Collectors.toList());
            } else {
                logger.error("指定的baseFile不存在或者不是文件夹");
            }

        } else if (url.getProtocol().equalsIgnoreCase("jar")) {

            String jarPath = url.getFile().substring(url.getFile().indexOf(":") + 1, url.getFile().indexOf("!"));
            JarFile jarFile = new JarFile(new File(jarPath));
            List<String> baseJars = jarFile.stream()
                    .map(ZipEntry::toString)
                    .filter(jar -> jar.endsWith(folderName + "/")).collect(Collectors.toList());
            if (baseJars.isEmpty()) {
                logger.info("不存在" + folderName + "资源文件夹");
                return new String[0];
            }

            baseNames = jarFile.stream().map(ZipEntry::toString)
                    .filter(jar -> baseJars.stream().anyMatch(jar::startsWith))
                    .filter(jar -> jar.endsWith(".properties"))
                    .map(jar -> jar.substring(jar.indexOf(folderName)))
                    .map(this::getI18FileName)
                    .distinct()
                    .collect(Collectors.toList());

        }
        return baseNames.toArray(new String[0]);
    }

    private void getAllFile(List<String> basenames, File folder, String path) {
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                this.getAllFile(basenames, file, path + folder.getName() + File.separator);
            }
        } else {
            String i18Name = this.getI18FileName(path + folder.getName());
            if (!basenames.contains(i18Name)) {
                basenames.add(i18Name);
            }

        }
    }

    private String getI18FileName(String filename) {
        filename = filename.replace(".properties", "");
        for (int i = 0; i < 2; i++) {
            int index = filename.lastIndexOf("_");
            if (index != -1) {
                filename = filename.substring(0, index);
            }
        }
        return filename.replace("\\", "/");
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {

        final String i18File = ThreadLocalUtil.getString(I18N_ATTRIBUTE);
        if (!StringUtils.isEmpty(i18File)) {
            String basename = getBasenameSet().stream()
                    .filter(name -> StringUtils.endsWithIgnoreCase(name, i18File))
                    .findFirst().orElse(null);
            if (!StringUtils.isEmpty(basename)) {
                ResourceBundle bundle = getResourceBundle(basename, locale);

                if (bundle != null) {
                    return getStringOrNull(bundle, code);
                }
            }
        }
        return null;
    }
}
