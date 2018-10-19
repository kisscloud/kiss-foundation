package status;


import java.io.UnsupportedEncodingException;
import java.util.*;


public class Message {

    private static Map<String,Map<Object,String>> messageMap = new HashMap<String,Map<Object,String>>();

    public static String getMessage(String lang,Object code) {
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
            Map<Object,String> codeMessageMap = new HashMap<Object,String>();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = bundle.getString(key);
                codeMessageMap.put(Integer.parseInt(value),new String(key.getBytes("ISO-8859-1"),"utf-8"));
            }

            messageMap.put(language,codeMessageMap);
        }
    }
}
