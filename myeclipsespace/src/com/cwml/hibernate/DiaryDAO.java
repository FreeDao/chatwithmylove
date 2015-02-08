package com.cwml.hibernate;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Diary
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.cwml.hibernate.Diary
 * @author MyEclipse Persistence Tools
 */

public class DiaryDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(DiaryDAO.class);
	// property constants
	public static final String CONTENT = "content";
	public static final String TITLE = "title";
	public static final String TIME = "time";
	public static final String USERNAME = "username";
	public static final String OTHER = "other";

	protected void initDao() {
		// do nothing
	}

	public void save(Diary transientInstance) {
		log.debug("saving Diary instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Diary persistentInstance) {
		log.debug("deleting Diary instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Diary findById(java.lang.Integer id) {
		log.debug("getting Diary instance with id: " + id);
		try {
			Diary instance = (Diary) getHibernateTemplate().get(
					"com.cwml.hibernate.Diary", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Diary instance) {
		log.debug("finding Diary instance by example");
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
		log.debug("finding Diary instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Diary as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByOther(Object other) {
		return findByProperty(OTHER, other);
	}

	public List findAll() {
		log.debug("finding all Diary instances");
		try {
			String queryString = "from Diary";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Diary merge(Diary detachedInstance) {
		log.debug("merging Diary instance");
		try {
			Diary result = (Diary) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Diary instance) {
		log.debug("attaching dirty Diary instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Diary instance) {
		log.debug("attaching clean Diary instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DiaryDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DiaryDAO) ctx.getBean("DiaryDAO");
	}
}