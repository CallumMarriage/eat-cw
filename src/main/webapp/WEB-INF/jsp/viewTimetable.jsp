<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

    <title>Lessons</title>

    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>

    <spring:url value="/css/main.css" var="springCss"/>
    <link href="${springCss}" rel="stylesheet"/>

    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
</head>

<body>

<script type="text/javascript">
    $(document).ready(function () {

        $('.chooseLesson').submit(function (e) {

            console.log($("#lessonId").val())

            e.preventDefault();
            $('#msg').hide();
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/getClientID",
                data: {
                    username: "${username}"
                },
                dataType: 'xml',
                success: function (data) {
                    let clientID = data
                        .getElementsByTagName("clientID")[0].textContent;
                    $.ajax({
                        type: "POST",
                        url: 'http://localhost:8080/chooseLesson',
                        data: {
                            clientId: clientID,
                            lessonId: $("#lessonId").val()
                        },
                        success: function (responseText) {
                            window.location.href = "viewSelection";
                        },
                        error: function (jqXHR, textStatus, errorThrown) {

                            $('#msg').show();
                            $('#msg').html('<p> Sorry, you have already signed up to that lesson</p>');
                            $('#msg').css("color", "red");
                        }
                    })
                }
            })
        })
    });

    function addHiddenFields(id){
        console.log(id)
        $("#lessonId").val(id);
        console.log($('#lessonId').val())
    }
</script>

<p id="error"></p>


<jsp:include page="navbar.jspx"/>

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

        <form class="chooseLesson" id="lesson-${lesson.getLessonId()}">
            <p id="desc-${lesson.getLessonId()}">"${lesson.getDescription()}"</p>
            <p id="level-${lesson.getLevel()}">"${lesson.getLevel()}"</p>
            <p id="lessonDate-${lesson.getLessonDate()}">"${lesson.getLessonDate()}"</p>
            <p id="startTime-${lesson.getStartTime()}">"${lesson.getStartTime()}"</p>
            <p id="endTime-${lesson.getEndTime()}">"${lesson.getEndTime()}"</p>
            <p class="lessonid" id="lessonId-${lesson.getLessonId()}" name="lessonId">"${lesson.getLessonId()}"</p>
            <input type="submit" value="Select Lesson" onclick="addHiddenFields('${lesson.getLessonId()}')">
        </form>
    </c:forEach>

    <div id="lessonId"></div>
</table>

<div id="msg"></div>


<!-- /.container -->

<script type="text/javascript"
        src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
