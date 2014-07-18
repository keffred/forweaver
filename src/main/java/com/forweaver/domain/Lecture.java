package com.forweaver.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Lecture implements Serializable {

	static final long serialVersionUID = 42313124234L;
	@Id
	private String name; //강의 이름 이게 기본 키
	private String description; // 강의 소개
	private Date openingDate; // 강의 시작일
	@DBRef
	private Weaver creator;
	
	@Transient
	private boolean isJoin;
	
	private List<String> tags = new ArrayList<String>(); // 강의의 태그 모음
	
	@DBRef
	private List<Weaver> adminWeavers = new ArrayList<Weaver>(); // 관리자들
	@DBRef
	private List<Weaver> joinWeavers = new ArrayList<Weaver>(); // 비 관리자 회원들
	
	private List<Repo> repos = new ArrayList<Repo>(); 
	
	public Lecture() {
		
	}
	
	public Lecture(String name, String description,
			Weaver weaver,List<String> tagList) {
		super();
		this.name = name;
		this.description = description;
		this.openingDate = new Date();
		this.creator = weaver;
		this.adminWeavers.add(weaver);
		this.tags = tagList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public String getCreatorName() {
		return this.creator.getId();
	}


	
	public String getOpeningDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd");
		return df.format(this.openingDate); 
	}

	public String getCreatorEmail() {
		return this.creator.getEmail();
	}

	public Weaver getCreator() {
		return creator;
	}

	public void setCreator(Weaver creator) {
		this.creator = creator;
	}

	public void addAdminWeaver(Weaver weaver){
		this.adminWeavers.add(weaver);
	}
	
	public void addJoinWeaver(Weaver weaver){
		this.joinWeavers.add(weaver);
	}
	
	public void removeJoinWeaver(Weaver weaver){
		this.joinWeavers.remove(weaver);
	}
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<Weaver> getAdminWeavers() {
		return adminWeavers;
	}

	public void setAdminWeavers(List<Weaver> adminWeavers) {
		this.adminWeavers = adminWeavers;
	}

	public List<Weaver> getJoinWeavers() {
		return joinWeavers;
	}

	public void setJoinWeavers(List<Weaver> joinWeavers) {
		this.joinWeavers = joinWeavers;
	}

	public List<Repo> getRepos() {
		return repos;
	}

	public void setRepos(List<Repo> repos) {
		this.repos = repos;
	}
	
	public void addRepo(Repo repo){
		this.repos.add(repo);
	}
	
	public void removeRepo(Repo repo){
		this.repos.remove(repo);
	}
	
	public Repo getRepo(String repoName){
		for(Repo repo: this.repos)
			if(repo.getName().equals(repoName))
				return repo;
		return null;
	}

	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}
	public String getImgSrc(){
		return creator.getImgSrc();
	}
	
	
}
