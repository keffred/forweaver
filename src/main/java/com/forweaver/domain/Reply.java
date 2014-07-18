package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Reply implements Serializable {

	static final long serialVersionUID = 121134L;

	@DBRef
	private Weaver writer;
	private Date created;
	private String content;
	private int number;
	
	public Reply() {
	}
	
	public Reply(Weaver writer, String content) {
		super();
		this.writer = writer;
		this.created = new Date();
		this.content = content;
	}
	
	public Weaver getWriter() {
		return writer;
	}

	public void setWriter(Weaver writer) {
		this.writer = writer;
	}

	public String getWriterName() {
		return this.writer.getId();
	}
	public String getWriterEmail() {
		return this.writer.getEmail();
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getFormatCreated() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		return df.format(created); 
	}
	
}
