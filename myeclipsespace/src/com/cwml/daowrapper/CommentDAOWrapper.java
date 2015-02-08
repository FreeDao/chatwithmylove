package com.cwml.daowrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cwml.hibernate.Diarycomment;
import com.cwml.hibernate.DiarycommentDAO;
import com.cwml.utils.ExecuteSQL;

public class CommentDAOWrapper {
	private static CommentDAOWrapper mCommentDAOWrapper = new CommentDAOWrapper();
	private static final Logger log = LoggerFactory.getLogger(CommentDAOWrapper.class);
	public DiarycommentDAO mCommentDao;
	private  ExecuteSQL mExecuteSQL;
	public static CommentDAOWrapper getInstance(){
		return mCommentDAOWrapper;
	}
	
	private CommentDAOWrapper(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		mCommentDao = DiarycommentDAO.getFromApplicationContext(context);
		setExecuteSQL();
	}
	
	
	public void setExecuteSQL(){
		mExecuteSQL = new ExecuteSQL(mCommentDao,Diarycomment.class);
	}

	protected void executeUpdate(final String sql, final Object...values){
		log.debug("update diarycomment instance");
		try {
			mExecuteSQL.executeUpdateSQL(sql, values);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	
	protected void executeDelete(final String sql, Object...values){
		log.debug("delete diarycomment instance");
		try {
			mExecuteSQL.executeUpdateSQL(sql, values);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	protected List executeQuery(final String sql, Object...values){
		log.debug("query diarycomment instance");
		try {
			log.debug("query successful");
			return mExecuteSQL.executeQuerySQL(sql, values);
		} catch (RuntimeException re) {
			log.error("query failed", re);
			throw re;
		}
	}
	

	
	public List queryAllDiaryComment(){
		return mCommentDao.findAll();
	}
	
	
	public Diarycomment getDiaryCommentById(int id){
		return mCommentDao.findById(id);
	}
	public void addComment(Diarycomment diarycomment){
		mCommentDao.save(diarycomment);
		log.info("添加日记成功");
	}
	public List getDiaryCommentByDiaryId(String id){
		
		return mCommentDao.findByDiaryid(id);
	}

}
