package com.cwml.classcontent;

public abstract class AbstractDiary implements java.io.Serializable{

	// Fields

	private Integer id;
	private String content;
	private String title;
	private String time;
	private String username;
	private String other;

	// Constructors

	/** default constructor */
	public AbstractDiary() {
	}

	/** full constructor */
	public AbstractDiary(String content, String title, String time,
			String username, String other) {
		this.content = content;
		this.title = title;
		this.time = time;
		this.username = username;
		this.other = other;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
