//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fastRPC.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class PropertyUtil {
    private static Properties properties;
    private static String path = "fastrpc-config.properties";
    private static Resource resource;

    public PropertyUtil() {
    }
    public PropertyUtil(Resource resource) {
        this.resource = resource;
    }

    public PropertyUtil(String path) {
        this.path = path;
        loadProperty();
    }

    private static synchronized void loadProperty() {
        if (properties == null) {
            properties = new Properties();
            Object in = null;

            try {
                if(resource != null){
                    properties = PropertiesLoaderUtils.loadProperties(resource);
                }else {
                    properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        ((InputStream)in).close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            initVar();
        }
    }

    public static String getProp(String key) {
        if(properties == null){loadProperty();}
        return properties != null && key != null ? properties.getProperty(key) : null;
    }

    public static void setProperties(String key, String value) {
        if (properties == null) {
            loadProperty();
        }

        properties.setProperty(key, value);
    }

    private static void initVar() {
        Enumeration enumeration = properties.propertyNames();
        Pattern pattern = Pattern.compile("\\$\\{.*?}");

        while(enumeration.hasMoreElements()) {
            String key = String.valueOf(enumeration.nextElement());
            String value = properties.getProperty(String.valueOf(key));
            Matcher matcher = pattern.matcher(value);
            String tempStr = value;

            while(matcher.find()) {
                StringBuilder t = new StringBuilder(matcher.group());
                t.deleteCharAt(0).deleteCharAt(0).deleteCharAt(t.lastIndexOf("}"));
                if (getProp(t.toString()) != null) {
                    tempStr = tempStr.replaceFirst("\\$\\{.*?}", getProp(t.toString()));
                } else {
                    tempStr = tempStr.replaceFirst("\\$\\{.*?}", "null");
                }
            }

            setProperties(key, tempStr);
        }

    }


}
