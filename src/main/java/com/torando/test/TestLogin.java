package com.torando.test;

import com.torando.config.TestConfig;
import com.torando.model.LoginModel;
import com.torando.utils.DatabaseUtil;
import com.torando.utils.HandleProsFile;
import com.torando.utils.HttpClientUtil;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestLogin {

    public static String expected_result;
    public String methodName;

    @BeforeTest(groups = "login", description = "Data init")
    public void setUp() {
        TestConfig.login_url = HandleProsFile.getValues("login.properties", "login.url");
        TestConfig.login_s = HandleProsFile.getValues("login.properties","login.s");
        TestConfig.login_username = HandleProsFile.getValues("login.properties","login.username");
        TestConfig.login_password = HandleProsFile.getValues("login.properties","login.password");
        TestConfig.login_app_key = HandleProsFile.getValues("login.properties","login.app_key");
        TestConfig.login_sign = HandleProsFile.getValues("login.properties","login.sign");
        TestConfig.login_is_allow_many = HandleProsFile.getValues("login.properties","login.is_allow_many");
        TestConfig.login_client = HandleProsFile.getValues("login.properties","login.client");
    }

    @AfterMethod
    public void afterMethod(ITestResult testResult){
        methodName = testResult.getMethod().getMethodName();
        if (testResult.getStatus() == ITestResult.FAILURE){
            System.out.println("Failed: "+testResult.getMethod().getMethodName());
        }
        if (testResult.getStatus()== ITestResult.SUCCESS){
            System.out.println("Success: "+testResult.getName());
        }
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
