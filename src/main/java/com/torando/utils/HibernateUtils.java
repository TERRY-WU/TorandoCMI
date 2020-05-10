package com.torando.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    private HibernateUtils(){
    }

    static{
        Configuration cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    public static void closeSessionFactory(){
        sessionFactory.close();
    }
    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
