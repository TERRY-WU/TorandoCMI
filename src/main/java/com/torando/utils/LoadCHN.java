package com.mobile.utils;

import java.io.*;
import java.util.Properties;

public class LoadCHN {

    public static String getValue(String filename, String key) throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\data\\";
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(path + filename);
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
        properties.load(bf);
        System.out.println(properties.getProperty(key));
        return properties.getProperty(key);
    }

}
