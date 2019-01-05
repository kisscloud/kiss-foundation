package utils;

import locale.MessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.*;

public class LangUtil {

    @Autowired
    protected MessageSource messageSource;

    public String getCodeMessage(Integer code) {

        String language = ApplicationUtil.getHttpServletRequest().getHeader("X-LANGUAGE");
        String message = messageSource.getMessage(String.valueOf(code) , null, new Locale(language==null ? "zh_cn":language.replace("-","_")));

        return message;
    }

    public String getEnumsMessage(String key,String option) {

        ThreadLocalUtil.setString(MessageResource.I18N_ATTRIBUTE,"enums");
        String language = ApplicationUtil.getHttpServletRequest().getHeader("X-LANGUAGE");
        String message = messageSource.getMessage(key + option, null, new Locale(language == null ? "zh_cn":language.replace("-","_")));

        return message;
    }



}
