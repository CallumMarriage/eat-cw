<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<!-- 
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

	<title>Lessons</title>
	<script src="https://code.jquery.com/jquery-1.10.2.js"
			type="text/javascript"></script>
</head>

<body>

	<script type="text/javascript">

		function ChooseLesson(lessonId) {

			$('#msg').hide();
			console.log("${clientId}")

			$.ajax({
				type: "GET",
				url: "http://localhost:8080/getClientID",
				data: {
					username: "${username}"
				},
				success : function(data){
					let clientID = data.split("<clientID>")[1];
					$.ajax({
					type: "POST",
					url : 'http://localhost:8080/chooseLesson',
					data : {
						clientId : clientID,
						lessonId: lessonId
					},
					success : function(responseText) {
						window.location.href = "viewSelection";
					},
					error : function(jqXHR, textStatus, errorThrown){

						$('#msg').show();
						$('#msg').html('<p> Sorry, you have already signed up to that lesson</p>');
						$('#msg').css("color", "red");
					}
				})
				}
			})
		}
	</script>

	<p id="error"></p>


	<jsp:include page="navBar.jspx"/>

	<table>
		<h1>Here are all of the lessons we have available.</h1>

		<p>Click add to lesson to add your self to the roster</p>
		<tr>
			<th>Description</th>
			<th>Level</th>
			<th>Lesson Date</th>
			<th>Start Time</th>
			<th>End Time</th>
		</tr>

		<c:forEach items="${lessons}" var="lesson">
			<tr>
				<td><c:out value="${lesson.getDescription()}" /></td>
				<td><c:out value="${lesson.getLevel()}" /></td>
				<td><c:out value="${lesson.getLessonDate()}"/></td>
				<td><c:out value="${lesson.getStartTime()}" /></td>
				<td><c:out value="${lesson.getEndTime()}" /></td>
				<td><button id="addToLesson" onclick='ChooseLesson("${lesson.getLessonId()}")'>Select Lesson</button></td>
			</tr>
		</c:forEach>
	</table>

	<div id="msg"></div>


	<!-- /.container -->

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
