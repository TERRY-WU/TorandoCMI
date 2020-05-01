package com.torando.utils;

import org.json.JSONObject;

public class GetErrorCode {

    public static String getErrCode(String key, String value) {
        JSONObject jsonObject = new JSONObject(key);
        return jsonObject.get(value).toString();
    }

}
