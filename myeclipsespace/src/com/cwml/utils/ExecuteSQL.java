package com.cwml.utils;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ExecuteSQL {

	private  HibernateDaoSupport mHibernateDao;
	private  Class<?> mClass;
	public ExecuteSQL(HibernateDaoSupport dao, Class<?> cla){
		mHibernateDao = dao;
		mClass = cla;
	}
	
	public List executeQuerySQL(final String sql, final Object... values){
		List vipList = mHibernateDao.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {  
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {  
		         SQLQuery query = session.createSQLQuery(sql);    
		    /*query.setString(USERNAME, usrOrEmail);
		    query.setString(EMAIL, usrOrEmail);*/
		        if (values != null) {
		            for (int i = 0; i < values.length; i++) {
		             query.setParameter(i, values[i]);
		            }
		         }
		        return query.addEntity(mClass).list();  
		            }  
		        });
		return vipList;
	}
	
	public void executeUpdateSQL(final String sql, final Object...values){
		mHibernateDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {  
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {  
		         SQLQuery query = session.createSQLQuery(sql);    
		        if (values != null) {
		            for (int i = 0; i < values.length; i++) {
		             query.setParameter(i, values[i]);
		            }
		         }
		         query.executeUpdate(); 
		         return null;
		            }  
		        });
	}
	
}
