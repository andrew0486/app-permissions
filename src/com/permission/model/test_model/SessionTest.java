package com.permission.model.test_model;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.permission.conf.HibernateUtil;
import com.permission.model.UserSession;

public class SessionTest {
	
	public void listarPersonas(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserSession> listUserSession = session.getNamedQuery("UserSession.findAll").list();//createQuery("from UserSession").list();
        Iterator it = listUserSession.iterator();
        
        while (it.hasNext()) {
            UserSession userSession = (UserSession)it.next();
            String texto = (userSession.getUser() != null ? userSession.getUsername()+" - FULLNAME: "+userSession.getUser().getLastNameOne()+" "+userSession.getUser().getLastNameTwo()+" "+userSession.getUser().getFirstName() : userSession.getUsername());
            System.out.println(texto);
        }
        session.close();
        System.exit(1);
	}
	
	public static void main(String[] args) {
		SessionTest test = new SessionTest();
		test.listarPersonas();
	}
	
}