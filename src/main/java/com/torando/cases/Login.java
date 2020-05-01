package com.torando.cases;

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

public class Login {

    private String methodName;

    @BeforeTest(groups = "login", description = "Data init")
    public void setUp() {
        String fileName = "login.properties";
        TestConfig.login_url = HandleProsFile.getValues(fileName, "login.url");
        TestConfig.login_s = HandleProsFile.getValues(fileName,"login.s");
        TestConfig.login_username = HandleProsFile.getValues(fileName,"login.username");
        TestConfig.login_password = HandleProsFile.getValues(fileName,"login.password");
        TestConfig.login_app_key = HandleProsFile.getValues(fileName,"login.app_key");
        TestConfig.login_sign = HandleProsFile.getValues(fileName,"login.sign");
        TestConfig.login_is_allow_many = HandleProsFile.getValues(fileName,"login.is_allow_many");
        TestConfig.login_client = HandleProsFile.getValues(fileName,"login.client");
    }

    @AfterMethod
    public void afterMethod(ITestResult testResult) {
        methodName = testResult.getMethod().getMethodName();
        if (testResult.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failed: " + testResult.getMethod().getMethodName());
        }
        if (testResult.getStatus() == ITestResult.SUCCESS) {
            System.out.println("Success: " + testResult.getName());
        }
    }

    @AfterTest
    public void tearDown() {
        System.out.println("Test done...");
    }

    @DataProvider(name = "data")
    public Object[][] provider() {
        Object[][] provider = new Object[9][2];
        for (int i = 0; i < provider.length; i++) {
            provider[i][0] = "login";
            provider[i][1] = i+1;
        }
        return provider;
    }

    @Test(dataProvider = "data")
    public void run(String id, Integer number) throws IOException {
        System.out.println(methodName);
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        LoginModel model = sqlSession.selectOne(id, number);
        String expected_result = model.getExpected_result();
        String expected_err_code = getErrCode(expected_result, "ret");
        String res = getResult(model);
        String actual_err_code = getErrCode(res, "ret");
        Assert.assertEquals(actual_err_code, expected_err_code);
    }

    public static String getErrCode(String key, String value) {
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
