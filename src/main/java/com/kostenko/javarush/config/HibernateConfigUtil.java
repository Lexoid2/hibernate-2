package com.kostenko.javarush.config;

import com.kostenko.javarush.model.entity.*;
import org.hibernate.cfg.Configuration;

public class HibernateConfigUtil {
    private static Configuration getBaseConfiguration() {
        return new Configuration()
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true")
                .setProperty("hibernate.use_sql_comments", "true")

                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .addAnnotatedClass(Text.class);
    }

    public static SessionFactoryProvider getProvider(String dbType) {
        return switch (dbType.toLowerCase()) {
            case "mysql" -> () -> getBaseConfiguration().buildSessionFactory();
            case "postgres" -> () -> getBaseConfiguration().configure().buildSessionFactory();
            default -> throw new UnsupportedOperationException("Unsupported DB type: " + dbType);
        };
    }
}
