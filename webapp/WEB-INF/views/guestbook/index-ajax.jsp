<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset{
	float: none;
	text-align:center
}
.ui-dialog .ui-dialog-buttonpane button {
	margin-left:auto;
	margin-right:auto;
}
#dialog-message p {
	padding:20px 0;
	font-weight:bold;
	font-size:1.0em;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var isEnd = false;

var messageBox = function( title, message, callback ){
	$( "#dialog-message" ).attr( "title", title );
	$( "#dialog-message p" ).text( message );
	$( "#dialog-message" ).dialog({
		modal: true,
		buttons: {
			"확인": function() {
  				$( this ).dialog( "close" );
			}
		},
		close: callback || function() {}
	});	
}

var render = function( vo ) {
	// 상용 app에서는 template library 를 사용한다. ex) ejs, leaf
	var html = 
		"<li data-no='" + vo.no + "'>" +
		"	<strong>" + vo.name + "</strong>" +
		"	<p>" + vo.message.replace( /\n/gi, "<br>" )  + "</P>" + 
		"   <a href='' data-no='" + vo.no + "'>삭제</a>" + 
		"</li>";
		
	$( "#list-guestbook" ).append( html );	
}

var fetchList = function(){
	
	if( isEnd === true ) {
		return;
	}
	
	var startNo = $( "#list-guestbook li" ).last().data( "no" ) || 0;
	$.ajax( {
		url : "${pageContext.request.contextPath }/guestbook/api/list?sno=" + startNo,
		type: "get",
		dataType: "json",
		data: "",
		//contentType: 'application/json', //JSON Type으로 데이터를 보낼 때,
		success: function( response ){
			if( response.result === "fail" ){
				console.error( response.message );
				return;
			}
			
			// detect end
			if( response.data.length < 5 ){
				isEnd = true;
				$( "#btn-next" ).prop( "disabled", true );
			}
				
			//rendering
			$.each( response.data, function( index, vo ){
				render( vo );
			} );
		},
		error: function( jqXHR, status, e ){
			console.error( status + " : " + e );
		}
	} );	
}

$(function(){
	
	$( "#add-form" ).submit( function( event ){
		// submit event 기본 동작을 막음
		// posting을 막음
		event.preventDefault();
		
		//validate form data
		var name = $( "#input-name" ).val();
		if( name === "" ) {
			messageBox( "방명록에 글 남기기", "이름은 필수 입력 항목입니다.", function(){
				$( "#input-name" ).focus();
			});
			return;
		}
		
		var password = $( "#input-password" ).val();
		if( password === "" ){
			messageBox( "방명록에 글 남기기", "비밀번호는 필수 입력 항목입니다.", function(){
				$( "#input-password" ).focus();
			});	
			return;
		}
		
	});
	
	$( window ).scroll( function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $( document ).height();
			
		// console.log( documentHeight + ">=" + scrollTop + "+" + windowHeight );	
		// scrollbar thumb가 바닥 전 10px까지 왔을 때...
		if( scrollTop + windowHeight + 10 > documentHeight ) {
			fetchList();
		}
	});
	
	$( "#btn-next" ).click( function(){
		fetchList();
	});
	
	//최초 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
				<div style="margin:20px 0; text-align:center">
					<button id="btn-next" style="padding:10px 20px">다음</button>
				</div>
			</div>
			<div id="dialog-message" title="" style="display:none">
			  <p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>