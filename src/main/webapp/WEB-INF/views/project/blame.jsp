<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<jsp:useBean id="dateValue" class="java.util.Date" />
<!DOCTYPE html>
<head>
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>

<body>
	<script>
	var blameArray = [];
	blameArray.push([
	         	    '1111',
	         		'@@@@',
	         		'3333',
	         		'4444'
	         		]);//쓰레기 값
	<c:forEach items="${gitBlameList}" var="gitBlame">
	blameArray.push([
	    '${gitBlame.getUserName()}',
		'${gitBlame.getCommitID()}',
		'${gitBlame.getCommitTime()}',
		'${gitBlame.getUserEmail()}'
		]);
	</c:forEach>
	$(function() {
	
	
	
	$('#tags-input').textext()[0].tags().addTags(
			getTagList("/tags:<c:forEach items='${project.tags}' var='tag'>	${tag},</c:forEach>"));

	
	$("#selectCommit").selectpicker({style: 'btn-primary', menuStyle: 'dropdown-inverse'});
	$('#selectCommit').selectpicker('refresh');
	
	$("#selectCommit").change(function(){
		if($("#selectCommit option:selected").val() != "체크아웃한 브랜치 없음")
			window.location = $("#selectCommit option:selected").val()+filePathTransform("${fileName}")+"/blame";
	});
	
	$("#source-code").addClass("brush: "+extensionSeach(document.location.href)+";");
	SyntaxHighlighter.all();
	
	 setTimeout(function(){ // 0.5초 뒤에 실행
		 for(var i=1;i<=blameArray.length;i++)
			 if(blameArray[i-1][1] != blameArray[i][1])
		 	$('td.gutter > div.line.number'+i).html(
		 			"<span><a href='/"+blameArray[i][3].replace('.',',')+"'>"+blameArray[i][0]+
		 			"</a>  <a href='/project/${project.name}/commitlog-viewer/commit:"+blameArray[i][1]+"'>"+
		 			blameArray[i][1]+"</a>  "+blameArray[i][2]+"</span>  "+i
		 	);
	}, 500);
});

</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<h5>
				<big><big><i class="fa fa-bookmark"></i> ${project.name}</big></big> 
				<small>${project.description}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li class="active"><a href="/project/${project.name}/">프로젝트
							브라우져</a></li>
					<li><a href="/project/${project.name}/commitlog">커밋 내역</a></li>
					<li><a href="/project/${project.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);" onclick="openWindow('/project/${project.name}/chat', 400, 500);">채팅</a></li>
					<li><a href="/project/${project.name}/weaver">참가자</a></li>
					<li><a href="/project/${project.name}/chart">통계</a>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://forweaver.com/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>

			<div class="span12">
				<div class="span9">
					<h4 class="file-name-title">${fileName}</h4>
				</div>
				<select id="selectCommit" class="span3">
					<c:forEach items="${gitLogList}" varStatus="status" var="gitLog">
						<option 
						<c:if test='${status.count == selectCommitIndex + 1}'>
						selected="selected"
						</c:if >
							value="/project/${project.name}/browser/commit:${fn:substring(gitLog.getName(),0,8)}/filepath:">
							<jsp:setProperty name="dateValue" property="time"
								value="${gitLog.getCommitTime()*1000}" />
							<fmt:formatDate value="${dateValue}" pattern="yy년MM월dd일 HH시mm분" />
						</option>
					</c:forEach>
				</select>
				
				<table class="table table-hover">
					<tbody>
						<tr>
							<td class="none-top-border td-post-writer-img" rowspan="2"><img
								src="${gitCommitLog.getImgSrc()}">
							</td>
							<td style="width: 800px;"
								class="none-top-border post-top-title-short"><a class="none-color" href="/project/${project.name}/commitlog-viewer/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
								${fn:substring(gitCommitLog.shortMassage,0,45)}</a></td>
							<td class="none-top-border td-button" rowspan="2">
							<a	href="/project/${project.name}/browser/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
									<span class="span-button"> <i class="fa fa-eye"></i>
										<p class="p-button">전체</p>
									</span>
									
							</a></td>
							<td class="none-top-border td-button" rowspan="2">
							<a	href="${fn:substring(requestScope['javax.servlet.forward.servlet_path'],0,(requestScope['javax.servlet.forward.servlet_path']).length()-6)}">
									<span class="span-button"> <i class="fa fa-file-code-o"></i>
										<p class="p-button">소스</p>
									</span>
									
							</a></td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${gitCommitLog.commiterName}</b>
								${gitCommitLog.getCommitDate()} &nbsp;&nbsp; <span
								style="cursor: text;" class="tag-commit tag-name">${gitCommitLog.commitLogID}</span>
							</td>
						</tr>
					</tbody>
				</table>
				<div style="padding-top:30px;" class="well-white">
					<pre id="source-code" >${fileContent}</pre>
				</div>
			</div>

			<!-- .span9 -->
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>
