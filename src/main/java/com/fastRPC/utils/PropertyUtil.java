//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fastRPC.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;


public class PropertyUtil {

    private static Logger logger = Logger.getLogger(PropertyUtil.class.getName());

    private static Properties properties = null;
    private static String path = "/fastrpc-config.properties";

    public PropertyUtil(String path) {
        this.path = path;
        loadProperty();
    }

    private static synchronized void loadProperty() {
        if (properties == null) {
            try {
                InputStream resourceAsStream = PropertyUtil.class.getResourceAsStream(path);
                properties = new Properties();
                properties.load(resourceAsStream);
            } catch (IOException e) {
                logger.warning(String.format("读取文件失败,%s", e.getMessage()));
            }

        }
    }

    public static String getProp(String key) {
        if (properties == null) {
            loadProperty();
        }
        return properties != null && key != null ? properties.getProperty(key) : null;
    }

    public static void setProperties(String key, String value) {
        if (properties == null) {
            loadProperty();
        }

        properties.setProperty(key, value);
    }

}
