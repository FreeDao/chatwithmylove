package com.cwml.hibernate;

/**
 * Diarycomment entity. @author MyEclipse Persistence Tools
 */
public class Diarycomment extends AbstractDiarycomment implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public Diarycomment() {
	}

	/** full constructor */
	public Diarycomment(String diaryid, String commentusername, String time,
			String content) {
		super(diaryid, commentusername, time, content);
	}

}
