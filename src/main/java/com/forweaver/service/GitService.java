package com.forweaver.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;

import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forweaver.domain.Weaver;
import com.forweaver.domain.git.GitCommitLog;
import com.forweaver.domain.git.GitFileInfo;
import com.forweaver.domain.git.GitSimpleCommitLog;
import com.forweaver.domain.git.GitSimpleFileInfo;
import com.forweaver.domain.git.statistics.GitParentStatistics;
import com.forweaver.mongodb.dao.WeaverDao;
import com.forweaver.util.GitInfo;
import com.forweaver.util.GitUtil;

@Service
public class GitService {

	@Autowired
	private WeaverDao weaverDao;


	public GitFileInfo getFileInfo(String parentDirctoryName,String repositoryName,
			String commitID,String filePath){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		filePath = filePath.replace('>', '/');
		filePath = filePath.replace(',', '.');
		GitFileInfo gitFileInfo = gitUtil.getFileInfor(commitID, filePath);
		gitFileInfo.setGitBlames(gitUtil.getBlame(filePath, commitID));
		return gitFileInfo;
	}

	public void hideBranch(String parentDirctoryName,String repositoryName,String weaverName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		gitUtil.hideNotUserBranches(weaverName);
		gitUtil.checkOutBranch(weaverName);
	}

	public void showBranch(String parentDirctoryName,String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		gitUtil.showBranches();
		gitUtil.checkOutMasterBranch();
	}

	public List<String> getBranchList(String parentDirctoryName,
			String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		List<String> branchList = gitUtil.getSimpleBranchAndTagNameList();
		return branchList;
	}

	public boolean existCommit(String parentDirctoryName,
			String repositoryName,String commit){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		try{
			RevCommit revCommit = gitUtil.getCommit(commit);
			if(revCommit == null)
				return false;
			else
				return true;
		}catch(Exception e){
			return false;
		}
	}

	public int getCommitListCount(String parentDirctoryName,
			String repositoryName,String commit){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		return gitUtil.getCommitListCount(commit);
	}

	public List<GitSimpleFileInfo> getGitSimpleFileInfoList(String parentDirctoryName,
			String repositoryName,String commitID) {
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		List<GitSimpleFileInfo> gitFileInfoList = gitUtil.getGitFileInfoList(commitID);
		return gitFileInfoList;
	}

	public List<GitSimpleCommitLog> getGitCommitLogList(String parentDirctoryName,
			String repositoryName,String branchName,int page,int number) {	
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		List<GitSimpleCommitLog> gitCommitLogList = gitUtil.getCommitLogList(branchName,page,number);
		return gitCommitLogList;
	}


	public GitCommitLog getGitCommitLog(String parentDirctoryName,
			String repositoryName,String branchName) {
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		GitCommitLog gitCommitLog = gitUtil.getCommitLog(branchName);
		return gitCommitLog;

	}


	public void getProjectZip(String parentDirctoryName,
			String repositoryName,String commitName,HttpServletResponse response){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		gitUtil.getProjectZip(commitName, response);
	}


	public GitParentStatistics loadStatistics(String parentDirctoryName,
			String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName,repositoryName);
		return gitUtil.getCommitStatistics();
	}

	public int[][] loadDayAndHour(String parentDirctoryName,
			String repositoryName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName, repositoryName);	
		return gitUtil.getDayAndHour();
	}

	public GitInfo getGitInfo(String parentDirctoryName,
			String repositoryName,String branchName){
		GitUtil gitUtil = new GitUtil(parentDirctoryName, repositoryName);	
		return gitUtil.getGitInfo(branchName);
	}

}
