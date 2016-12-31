package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Factory
{
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    private Factory()
    {
    }
}
