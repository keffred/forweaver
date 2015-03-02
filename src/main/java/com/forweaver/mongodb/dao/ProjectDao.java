package com.forweaver.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.forweaver.domain.Project;

@Repository
public class ProjectDao {
	
	@Autowired private MongoTemplate mongoTemplate;
	
	/** 프로젝트를 생성 메서드
	 * @param project
	 */
	public void insert(Project project) {
		
		if (!mongoTemplate.collectionExists(Project.class)) {
			mongoTemplate.createCollection(Project.class);
		}
		mongoTemplate.insert(project);
	}
	
	/** 프로젝트명으로 강의를 가져옴
	 * @param projectName
	 * @return
	 */
	public Project get(String projectName) {
		Query query = new Query(Criteria.where("_id").is(projectName));
		return mongoTemplate.findOne(query, Project.class);
	}
	
	/** 프로젝트 삭제
	 * @param project
	 */
	public void delete(Project project) {
		mongoTemplate.remove(project);
	}
	
	/** 프로젝트 수정하기
	 * @param project
	 */
	public void update(Project project) {
		Query query = new Query(Criteria.where("_id").is(project.getName()));
		Update update = new Update();
		update.set("category", project.getCategory());
		update.set("description", project.getDescription());
		update.set("tags", project.getTags());
		update.set("push", project.getPush());
		update.set("adminWeavers", project.getAdminWeavers());
		update.set("joinWeavers", project.getJoinWeavers());
		update.set("childProjects", project.getChildProjects());
		mongoTemplate.updateFirst(query, update, Project.class);
	}
	
	/** 프로젝트를 검색하고 수를 셈.
	 * @param tags
	 * @param search
	 * @param creator
	 * @param sort
	 * @return
	 */
	public long countProjects(
			List<String> tags,
			String search,
			String sort) {
		Criteria criteria = new Criteria();
		
		if(search != null)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));
		
		if(tags != null)
			criteria.and("tags").all(tags);
			
		this.filter(criteria, sort);

		return mongoTemplate.count(new Query(criteria), Project.class);
	}
	
	/** 프로젝트를 검색
	 * @param tags
	 * @param search
	 * @param creator
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Project> getProjects( 
			List<String> tags,
			String search,
			String sort,
			int page, 
			int size) {
		Criteria criteria = new Criteria();
		
		if(search != null)
			criteria.orOperator(new Criteria("name").regex(search),
					new Criteria("description").regex(search));
		
		if(tags != null)
			criteria.and("tags").all(tags);
		
		this.filter(criteria, sort);
		
		Query query = new Query(criteria);
		query.with(new PageRequest(page-1, size));

		this.sorting(query, sort);
		return mongoTemplate.find(query, Project.class);
	}
	
	/** 로그인한 회원이 자신의 프로젝트를 가져올 때 활용.
	 * @param projectNames
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Project> getProjects(
			List<String> projectNames,
			int page, 
			int size) {
		Criteria criteria = new Criteria("name").in(projectNames);
		Query query = new Query(criteria);
		query.with(new PageRequest(page-1, size));

		return mongoTemplate.find(query, Project.class);
	}
	
	
	
	/** 검색할 때 필터를 적용.
	 * @param criteria
	 * @param sort
	 */
	public void filter(Criteria criteria,String sort){
		if (sort.equals("push-many")) {
			criteria.and("push").gt(0);
		} else if (sort.equals("push-null")) {
			criteria.and("push").is(0);
		} else if (sort.equals("fork")) {
			criteria.and("originalProject").exists(true);
		} else if (sort.equals("solo")) {
			criteria.where("adminWeavers").size(1).and("joinWeavers").size(0);
		}
	}
	
	/** 검색할 때 정렬함.
	 * @param query
	 * @param sort
	 */
	public void sorting(Query query,String sort){
		if (sort.equals("opendate-asc")) {
			query.with(new Sort(Sort.Direction.ASC, "openingDate"));
		} else if (sort.equals("push-null")) {
			query.with(new Sort(Sort.Direction.ASC, "push"));
		} else if (sort.equals("push-many")) {
			query.with(new Sort(Sort.Direction.DESC, "push"));
		} else
			query.with(new Sort(Sort.Direction.DESC, "openingDate"));
	}

}
