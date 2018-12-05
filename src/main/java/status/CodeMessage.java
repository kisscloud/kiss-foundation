package status;


import locale.MessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import utils.ApplicationUtil;
import utils.ThreadLocalUtil;

import java.util.*;

public class CodeMessage {

    @Autowired
    protected MessageSource messageSource;

    public String getMessage(Integer code) {

        String language = ApplicationUtil.getHttpServletRequest().getHeader("X-LANGUAGE");
        String message = messageSource.getMessage(String.valueOf(code) , null, new Locale(language==null ? "zh_cn":language.replace("-","_")));

        return message;
    }

    public String getEnumsMessage(String key,String option) {

        ThreadLocalUtil.setString(MessageResource.I18N_ATTRIBUTE,"enums");
        String language = ApplicationUtil.getHttpServletRequest().getHeader("X-LANGUAGE");
        System.out.println("=====1====" + language);
        System.out.println(new Locale(language == null ? "zh_cn":language.replace("-","_")).getLanguage());
        String message = messageSource.getMessage(key + option, null, new Locale(language == null ? "zh_cn":language.replace("-","_")));

        return message;
    }
}
