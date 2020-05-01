package com.torando.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HandleProsFile {

    public static String getValues(String filename, String key) {
//        InputStream inputStream = HandlePropertiesFile.class.getClassLoader().getResourceAsStream("elements.properties");
        String path;
        String os = System.getProperties().getProperty("os.name");
        if (os.equals("Mac OS X") || os.equals("Linux")) {
            path = System.getProperty("user.dir") + "/src/main/resources/data/";
        } else {
            path = System.getProperty("user.dir") + "\\src\\main\\resources\\data\\";
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}