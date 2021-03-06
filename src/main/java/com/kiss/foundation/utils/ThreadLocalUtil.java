package com.kiss.foundation.utils;

import com.kiss.foundation.entity.Guest;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {

    private static ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();

    private static ThreadLocal<Guest> guestThreadLocal = new ThreadLocal<>();

    /**
     * @Title: 获取线程变量value
     * @Description: TODO
     */
    public static String getString(String key) {

        Map<String, String> map = threadLocal.get();

        if (map == null) {
            return null;
        }

        return map.get(key);
    }

    /**
     * @Title:设置线程变量
     * @Description: TODO
     */
    public static void setString(String key, String value) {

        Map<String, String> map = threadLocal.get();

        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }

        map.put(key, value);
    }

    public static void removeMap() {

        threadLocal.remove();
    }

    /**
     * @Title:获取当前语言lang
     * @Description: TODO
     */
    public static String getLang() {

        String lang = getString("X-LANGUAGE");
        return StringUtils.isEmpty(lang) ? lang : "zh-CN";
    }

    public static void setGuest(Guest guest) {

        guestThreadLocal.set(guest);
    }

    public static void remove() {

        guestThreadLocal.remove();
    }

    public static Guest getGuest() {

        return guestThreadLocal.get();
    }
}

