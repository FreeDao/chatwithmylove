package com.cwml.hibernate;

/**
 * AbstractDiarycomment entity provides the base persistence definition of the
 * Diarycomment entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractDiarycomment implements java.io.Serializable {

	// Fields

	private Integer id;
	private String diaryid;
	private String commentusername;
	private String time;
	private String content;

	// Constructors

	/** default constructor */
	public AbstractDiarycomment() {
	}

	/** full constructor */
	public AbstractDiarycomment(String diaryid, String commentusername,
			String time, String content) {
		this.diaryid = diaryid;
		this.commentusername = commentusername;
		this.time = time;
		this.content = content;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDiaryid() {
		return this.diaryid;
	}

	public void setDiaryid(String diaryid) {
		this.diaryid = diaryid;
	}

	public String getCommentusername() {
		return this.commentusername;
	}

	public void setCommentusername(String commentusername) {
		this.commentusername = commentusername;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}