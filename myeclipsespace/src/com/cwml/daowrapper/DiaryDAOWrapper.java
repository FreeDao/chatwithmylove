package com.cwml.daowrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwml.hibernate.Diary;
import com.cwml.hibernate.DiaryDAO;
import com.cwml.hibernate.User;
import com.cwml.utils.ExecuteSQL;

public class DiaryDAOWrapper {
	private static DiaryDAOWrapper mDiaryDaoWrapper = new DiaryDAOWrapper();
	private static final Logger log = LoggerFactory.getLogger(UserDaoWrapper.class);
	public DiaryDAO mDiaryDao;
	private  ExecuteSQL mExecuteSQL;
	public static DiaryDAOWrapper getInstance(){
		return mDiaryDaoWrapper;
	}
	
	private DiaryDAOWrapper(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		mDiaryDao = DiaryDAO.getFromApplicationContext(context);
		setExecuteSQL();
	}
	
	
	public void setExecuteSQL(){
		mExecuteSQL = new ExecuteSQL(mDiaryDao,User.class);
	}

	protected void executeUpdate(final String sql, final Object...values){
		log.debug("update diary instance");
		try {
			mExecuteSQL.executeUpdateSQL(sql, values);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	
	protected void executeDelete(final String sql, Object...values){
		log.debug("delete diary instance");
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
	

	
	public List queryAllDiary(){
		return mDiaryDao.findAll();
	}
	
	
	public Diary getDiaryById(int id){
		return mDiaryDao.findById(id);
	}
	public void addDiary(Diary diary){
		mDiaryDao.save(diary);
		log.info("添加日记成功");
	}
	public List getDiaryByUserName(String name){
		
		return mDiaryDao.findByUsername(name);
	}
}
