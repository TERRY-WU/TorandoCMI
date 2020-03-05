package com.torando.cases;

import com.torando.config.TestConfig;
import com.torando.model.LoginModel;
import com.torando.utils.DatabaseUtil;
import com.torando.utils.HttpClientUtil;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class Login {

    public static String expected_result;

    @BeforeTest(groups = "login", description = "数据初始化")
    public void setUp() {
        InputStream config = Login.class.getClassLoader().getResourceAsStream("login.properties");
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (Exception e) {
            System.out.println("读取 config 文件出错..." + e);
        }
        TestConfig.login_url = properties.getProperty("login.url");
        TestConfig.login_s = properties.getProperty("login.s");
        TestConfig.login_username=properties.getProperty("login.username");
        TestConfig.login_password = properties.getProperty("login.password");
        TestConfig.login_app_key = properties.getProperty("login.app_key");
        TestConfig.login_sign = properties.getProperty("login.sign");
        TestConfig.login_is_allow_many = properties.getProperty("login.is_allow_many");
        TestConfig.login_client = properties.getProperty("login.client");
    }

    @Test
    public void test01() throws IOException {
        run(1);
    }
    @Test
    public void test02() throws IOException {
        run(2);
    }

    @Test
    public void test03() throws IOException {
        run(3);
    }

    @Test
    public void test04() throws IOException {
        run(4);
    }

    @Test
    public void test05() throws IOException {
        run(5);
    }

    @Test
    public void test06() throws IOException {
        run(6);
    }

    @Test
    public void test07() throws IOException {
        run(7);
    }

    @Test
    public void test08() throws IOException {
        run(8);
    }

    @Test
    public void test09() throws IOException {
        run(9);
    }

    @AfterTest
    public void tearDown(){
        System.out.println("Test done...");
    }

    public void run(Integer number) throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        LoginModel model = sqlSession.selectOne("login", number);
        expected_result = model.getExpected_result();
        String expected_err_code = getErrCode(expected_result, "ret");
        String res = getResult(model);
        String actual_err_code = getErrCode(res, "ret");
        Assert.assertEquals(actual_err_code, expected_err_code);
        System.out.println(res);
    }

    public static String getErrCode(String key, String value){
        JSONObject jsonObject = new JSONObject(key);
        return jsonObject.get(value).toString();
    }

    public static String getResult(LoginModel model) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(TestConfig.login_s, model.getS());
        map.put(TestConfig.login_username, model.getUsername());
        map.put(TestConfig.login_password, model.getPassword());
        map.put(TestConfig.login_app_key, model.getApp_key());
        map.put(TestConfig.login_is_allow_many, model.getIs_allow_many());
        map.put(TestConfig.login_client, model.getClient());
        map.put(TestConfig.login_app_key, model.getApp_key());
        return HttpClientUtil.doGet(TestConfig.login_url, map);
    }

}
