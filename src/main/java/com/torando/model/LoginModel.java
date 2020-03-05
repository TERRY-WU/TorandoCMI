package com.torando.model;

import lombok.Data;

@Data
public class LoginModel {
    private Integer id;
    private String s;
    private String username;
    private String password;
    private String priority;
    private String precondition;
    private String is_allow_many;
    private String client;
    private String app_key;
    private String expected_result;
    private String description;
}