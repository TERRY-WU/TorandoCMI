package com.torando.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtils {

<<<<<<< HEAD:src/main/java/com/torando/utils/MyBatisUtils.java
    public static SqlSession getSqlSession()  {
=======
    public static SqlSession getSqlSession() {

        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("dbconfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
>>>>>>> 3a341343ee001a583d88ad77fd2d8cedfc654a70:src/main/java/com/torando/utils/DatabaseUtil.java

        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("dbconfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //sqlSession执行配置文件中的sql语句。
        SqlSession sqlSession = factory.openSession();

        return sqlSession;
    }

}
