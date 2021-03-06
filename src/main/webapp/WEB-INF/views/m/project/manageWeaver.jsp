<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
</head>

<body>
	<div data-role="page">
		<%@ include file="/WEB-INF/panel/projectPanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a"
			data-role="header">
			<h1>참가자 목록</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content">

			<table id="weaverTable" class="table table-hover">

				<c:forEach items="${project.adminWeavers}" var="adminWeaver">
					<tr>
						<td class='td-post-writer-img'><img
							src='${adminWeaver.getImgSrc()}'></td>
						<td class='vertical-middle'>${adminWeaver.id}</td>
						<td class='vertical-middle'>${adminWeaver.email}</td>
						<td style='width: 42px;' class='td-button'><span
							class='span-button'><i class='icon-user icon-white'></i>
								<p class='p-button-small'>괸리자</p></span></td>
					</tr>
				</c:forEach>

				<c:forEach items="${project.joinWeavers}" var="joinWeaver">
					<tr>
						<td class='td-post-writer-img'><img
							src='${joinWeaver.getImgSrc()}'></td>
						<td class='vertical-middle'>${joinWeaver.id}</td>
						<td class='vertical-middle'>${joinWeaver.email}</td>
						<td style='width: 42px;' class='td-button'></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
</body>
</html>