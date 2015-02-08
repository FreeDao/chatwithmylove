package com.cwml.hibernate;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Diarycomment entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.cwml.hibernate.Diarycomment
 * @author MyEclipse Persistence Tools
 */

public class DiarycommentDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(DiarycommentDAO.class);
	// property constants
	public static final String DIARYID = "diaryid";
	public static final String COMMENTUSERNAME = "commentusername";
	public static final String TIME = "time";
	public static final String CONTENT = "content";

	protected void initDao() {
		// do nothing
	}

	public void save(Diarycomment transientInstance) {
		log.debug("saving Diarycomment instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Diarycomment persistentInstance) {
		log.debug("deleting Diarycomment instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Diarycomment findById(java.lang.Integer id) {
		log.debug("getting Diarycomment instance with id: " + id);
		try {
			Diarycomment instance = (Diarycomment) getHibernateTemplate().get(
					"com.cwml.hibernate.Diarycomment", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Diarycomment instance) {
		log.debug("finding Diarycomment instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Diarycomment instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Diarycomment as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDiaryid(Object diaryid) {
		return findByProperty(DIARYID, diaryid);
	}

	public List findByCommentusername(Object commentusername) {
		return findByProperty(COMMENTUSERNAME, commentusername);
	}

	public List findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all Diarycomment instances");
		try {
			String queryString = "from Diarycomment";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Diarycomment merge(Diarycomment detachedInstance) {
		log.debug("merging Diarycomment instance");
		try {
			Diarycomment result = (Diarycomment) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Diarycomment instance) {
		log.debug("attaching dirty Diarycomment instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Diarycomment instance) {
		log.debug("attaching clean Diarycomment instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DiarycommentDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DiarycommentDAO) ctx.getBean("DiarycommentDAO");
	}
}