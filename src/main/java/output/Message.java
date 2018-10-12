package output;


import java.io.UnsupportedEncodingException;
import java.util.*;


public class Message {

    private static Map<String,Map<Integer,String>> messageMap = new HashMap<String,Map<Integer,String>>();

    public static String getMessage(String lang,Integer code) {
        return messageMap.get(lang).get(code);
    }

    public Message() throws UnsupportedEncodingException {

        ResourceBundle langBundle = ResourceBundle.getBundle("language");
        Enumeration<String> langKeys = langBundle.getKeys();
        Map<String,String> langMap = new HashMap<String,String>();

        while (langKeys.hasMoreElements()) {
            String key = langKeys.nextElement();
            String value = langBundle.getString(key);
            langMap.put(key,value);
        }

        for (Map.Entry<String,String> entry : langMap.entrySet()) {
            String language = entry.getValue();
            ResourceBundle bundle = ResourceBundle.getBundle("messages/messages",new Locale(language));
            Enumeration<String> keys = bundle.getKeys();
            Map<Integer,String> codeMessageMap = new HashMap<Integer,String>();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = bundle.getString(key);
                codeMessageMap.put(Integer.parseInt(value),new String(key.getBytes("ISO-8859-1"),"utf-8"));
            }

            messageMap.put(language,codeMessageMap);
        }
    }
}
