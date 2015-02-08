package com.cwml.daowrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwml.hibernate.User;
import com.cwml.hibernate.UserDAO;
import com.cwml.utils.ExecuteSQL;



public class UserDaoWrapper{

	private static UserDaoWrapper mMyself = new UserDaoWrapper();
	private static final Logger log = LoggerFactory.getLogger(UserDaoWrapper.class);
	public UserDAO mUserDAO;
	private  ExecuteSQL mExecuteSQL;
	public static UserDaoWrapper getInstance(){
		return mMyself;
	}
	
	private UserDaoWrapper(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		mUserDAO = UserDAO.getFromApplicationContext(context);
		setExecuteSQL();
	}
	
	
	public void setExecuteSQL(){
		mExecuteSQL = new ExecuteSQL(mUserDAO,User.class);
	}

	protected void executeUpdate(final String sql, final Object...values){
		log.debug("update User instance");
		try {
			mExecuteSQL.executeUpdateSQL(sql, values);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	
	protected void executeDelete(final String sql, Object...values){
		log.debug("delete User instance");
		try {
			mExecuteSQL.executeUpdateSQL(sql, values);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	protected List executeQuery(final String sql, Object...values){
		log.debug("query User instance");
		try {
			log.debug("query successful");
			return mExecuteSQL.executeQuerySQL(sql, values);
		} catch (RuntimeException re) {
			log.error("query failed", re);
			throw re;
		}
	}
	

	
	public List queryAllUser(){
		return mUserDAO.findAll();
	}
	
	

	public User getUserByUserName(String username){
		return mUserDAO.findById(username);
	}
	
	public List getVipByUserId(int uid){
		return mUserDAO.findByUid(uid);
	}
	public void addUser(User user){
		mUserDAO.save(user);
		log.info("添加用户成功");
	}
	

}
