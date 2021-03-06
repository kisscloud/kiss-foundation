package com.kiss.foundation.utils;

import com.kiss.foundation.locale.BaseMessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.*;

public class BaseLangUtil {

    @Autowired
    protected MessageSource messageSource;

    public String getCodeMessage(Integer code) {

        String language = ApplicationUtil.getHttpServletRequest().getHeader("X-LANGUAGE");
        String message = messageSource.getMessage(String.valueOf(code) , null, new Locale(language==null ? "zh_cn":language.replace("-","_")));

        return message;
    }

    public String getEnumsMessage(String key,String option) {

        ThreadLocalUtil.setString(BaseMessageResource.I18N_ATTRIBUTE, "com/kiss/foundation/status");
        String language = ApplicationUtil.getHttpServletRequest().getHeader("X-LANGUAGE");
        String message = messageSource.getMessage(key + option, null, new Locale(language == null ? "zh_cn":language.replace("-","_")));

        return message;
    }



}
