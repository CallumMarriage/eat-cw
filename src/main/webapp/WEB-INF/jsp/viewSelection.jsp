<%--
  Created by IntelliJ IDEA.
  User: callummarriage
  Date: 21/12/2019
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My selection</title>


    <link href="${jstlCss}" rel="stylesheet"/>

    <c:url value="/css/main.css" var="jstlCss"/>
    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
</head>
</head>

<body>

<script type="text/javascript">

    function FinaliseBooking() {
        $('#msg').hide();

        $.ajax({
            type: "GET",
            url: "http://localhost:8080/getClientID",
            data: {
                username: "${username}"
            },
            success: function (data) {
                let clientID = data.split("<clientID>")[1];
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
    }

</script>

<div id="mySidenav" class="sidenav">
    <a href="${pageContext.request.contextPath}/viewSelection">View Selection</a>
    <a href="${pageContext.request.contextPath}/viewTimetable">View Timetable</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>

</div>

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

<button onclick='FinaliseBooking()'>Finalise Booking</button>

<!-- /.container -->

<script type="text/javascript"
        src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
