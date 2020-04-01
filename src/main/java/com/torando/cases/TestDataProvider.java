package com.torando.cases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestDataProvider {
    @DataProvider(name = "provider")
    public Object[][] provider() {
        Object[][] provider = new Object[5][2];
        for (int i = 0; i < provider.length; i++) {
            provider[i][0] = "name " + i;
            provider[i][1] = i + 10;
        }
        return provider;
    }

    @Test(dataProvider = "provider")
    public void getName(String name, int age) {
        System.out.println(name + "_" + age);
    }

}
