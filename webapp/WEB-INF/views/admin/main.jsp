<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite03/assets/css/admin/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/admin/include/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div id="site-form">
					<form method="post" action="">
						<label class="block-label" for="title">사이트 타이틀</label>
						<input id="title" name="title" type="text" value="">
						
						<label class="block-label" for="welcomeMessage">환영 메세지</label>
						<input id="welcomeMessage" name="welcomeMessage" type="text" value="">

						<label class="block-label">프로필 이미지</label>
						<img id="profile" src="https://scontent-icn1-1.xx.fbcdn.net/v/t1.0-1/p160x160/936089_1019758748039064_7187347097932848216_n.jpg?oh=9f25131398604088fc57945de6872b2e&oe=59A697B2">
						<input type="file" name="file1">

						<label class="block-label">사이트 설명</label>
						<textarea name="description"></textarea>
						
						<input type="submit" value="변경" />
					</form>
									
				

				</div>
			</div>
			<c:import url="/WEB-INF/views/admin/include/navigation.jsp" />
		</div>
	</div>
</body>
</html>