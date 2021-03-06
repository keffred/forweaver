<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<jsp:useBean id="dateValue" class="java.util.Date" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>
<script>

$(document).ready(function() {
	
	$("#selectCommit").change(function(){
		if($("#selectCommit option:selected").val() != "체크아웃한 브랜치 없음")
		setTimeout(function(){
			window.location = ($("#selectCommit option:selected").val()
					+"/"+"${fileName}");
		});
	});
	
	$("#source-code").addClass("brush: "+extensionSeach(document.location.href)+";");
	SyntaxHighlighter.all();
		
	 if ($(window).width() > 500) {
		 	$(".td-button").css("width","80px");
	     	$( ".full-content" ).show();
	    }else{
	    	$(".td-button").css("width","36px");
	    	$( ".full-content" ).hide();
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
	     	$( ".full-content" ).hide();
	     	$(".td-button").css("width","36px");
	     } else{
	 	    $( ".full-content" ).show();
	 		$(".td-button").css("width","80px");
	      }
	 });
	 
});

</script>

<body>
	<div data-role="page">

<%@ include file="/WEB-INF/panel/projectPanel.jsp"%>

		<div data-theme="a" data-role="header">

			<h1>파일 뷰어</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
        <select data-overlay-theme="f" data-theme="f"
			 data-native-menu="false" id="selectCommit">
					<c:forEach items="${gitLogList}" varStatus="status" var="gitLog">
						<option 
						<c:if test='${status.count == selectCommitIndex + 1}'>
						selected="selected"
						</c:if >
							value="/m/project/${project.name}/browser/commit:${fn:substring(gitLog.getName(),0,8)}/filepath:">
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
							<td colspan="2"  class="none-top-border post-top-title">
							<a rel="external" class="none-color" href="/project/${project.name}/commitlog-viewer/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
								${gitCommitLog.shortMassage}</a></td>
							<td class="none-top-border td-button" rowspan="2">
							<a rel="external"	href="/project/${project.name}/browser/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
									<span class="span-button"> <i class="icon-eye-open icon-white"></i>
										<p class="p-button">소스</p>
									</span>
							</a>
							<a class = "full-content" rel="external"	
							href="/project/${project.name}/data/commit:${fn:substring(gitCommitLog.commitLogID,0,20)}/filepath:/${fn:replace(fileName,'.jsp', ',jsp')}">
										<span class="span-button"> 
										<i	class="icon-circle-arrow-down icon-white"></i>
											<p class="p-button">다운</p></span>
									</a>							
							</td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${gitCommitLog.commiterName}</b></td>
							<td class="post-bottom-tag"><span class="full-content tag-commit tag-name">${gitCommitLog.commitLogID}</span></td>
						</tr>
					</tbody>
				</table>
				<pre id="source-code">${fileContent}</pre>
	</div>
	
	</div>
</body>
</html>