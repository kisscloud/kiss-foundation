package com.kiss.foundation.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BeanCopyUtil {

    public static List<String> defaultFieldNames = new ArrayList<>();

    static {
        defaultFieldNames.add("createdAt");
        defaultFieldNames.add("updatedAt");
    }

    public static <T> T copy(Object source, Class<T> targetClass) {

        return copy(source, targetClass, null);
    }

    public static <T> T copy(Object source, Class<T> targetClass, List<String> fieldNames) {

        try {
            T target = targetClass.newInstance();

            if (source != null) {
                BeanUtils.copyProperties(source, target);
            }

            formatDateField(source, target, fieldNames);

            return target;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> copyList(List<?> source, Class<T> targetClass) {

        return copyList(source, targetClass, null);
    }

    public static <T> List<T> copyList(List<?> source, Class<T> targetClass, List<String> fieldNames) {

        List<T> targetList = new ArrayList<T>();

        try {

            if (source != null && source.size() != 0) {
                for (Object object : source) {
                    T target = targetClass.newInstance();
                    BeanUtils.copyProperties(object, target);
                    formatDateField(object, target, fieldNames);
                    targetList.add(target);
                }
            }

            return targetList;
        } catch (Exception e) {
            e.printStackTrace();
            return targetList;
        }
    }

    private static Object formatDateField(Object source, Object target, List<String> fieldNames) {

        try {
            if (fieldNames != null) {

                for (String fieldName : fieldNames) {
                    Field field = target.getClass().getDeclaredField(fieldName);

                    if (field != null) {
                        Method method = source.getClass().getMethod(getMethodName(fieldName, "get"));
                        field.setAccessible(true);
                        Object date = (Date) method.invoke(source);

                        if (date == null) {
                            continue;
                        }

                        field.set(target, ((Date) date).getTime());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return target;
        }
    }

    private static String getMethodName(String fieldName, String prefix) {

        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return prefix + new String(items);
    }
}
