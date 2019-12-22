<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My selection</title>

    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />

    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
</head>
</head>

<body>

<script type="text/javascript">


    $(document).ready(function () {

        $('#finaliseBooking').submit(function (e) {
            e.preventDefault();
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/getClientID",
                data: {
                    username: "${username}"
                },
                success: function (data) {
                    let clientID = data.responseXML
                        .getElementsByTagName("clientID")[0].textContent;

                    $.ajax({
                        type: "POST",
                        url: 'http://localhost:8080/finaliseBooking',
                        data: {
                            clientId: clientID,
                        },
                        success: function (responseText) {
                            window.location.href = "viewTimetable";
                            console.log('success')
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log("error");
                        }
                    });
                }
            })
        });
    });

</script>

<jsp:include page="navbar.jspx"/>

<table>

    <h1>Here are all of the lessons that you are booked on to.</h1>

    <tr>
        <th>Description</th>
        <th>Level</th>
        <th>Lesson Date</th>
        <th>Start Time</th>
        <th>End Time</th>
    </tr>

    <c:forEach items="${myLessons}" var="myLesson">
        <tr>
            <td><c:out value="${myLesson.getDescription()}"/></td>
            <td><c:out value="${myLesson.getLevel()}"/></td>
            <td><c:out value="${myLesson.getLessonDate()}"/></td>
            <td><c:out value="${myLesson.getStartTime()}"/></td>
            <td><c:out value="${myLesson.getEndTime()}"/></td>
        </tr>
    </c:forEach>
</table>


<jsp:include page="footer.jspx"/>


<!-- /.container -->

<script type="text/javascript"
        src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
