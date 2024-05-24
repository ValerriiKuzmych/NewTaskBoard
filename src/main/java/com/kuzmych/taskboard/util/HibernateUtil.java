package com.kuzmych.taskboard.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	public static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {

		try {

			Configuration configuration = new Configuration().configure();

			return configuration.buildSessionFactory();

		} catch (HibernateException ex) {

			// TODO: Handle the exception appropriately

			System.out.println(ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {

		return sessionFactory;
	}

}
