package com.kostenko.javarush.config;

import org.hibernate.SessionFactory;

public interface SessionFactoryProvider {
    SessionFactory createSessionFactory();
}
