<%--
    Document   : index
    Created on : 15-Mar-2010, 14:47:22
    Author     : bastinl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

    <title>Login / signup page</title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>

</head>
<body>
<h2>Please log in!</h2>
<form method="POST" action="http://localhost:8080/login">
    Username:<input id="username" type="text" name="username" value=""/>----
    Password:<input type="password" name="password"  value="" />
    <input id="login" type="submit" value="Login" />
</form>


<div id="loginError"></div>


<form id="register">
    <h2> Don't yet have an account? </h2>
    Username:<input type="text" id="newUsername" name="newUsername" onkeypress="checkLength()" value="" />----
    Password:<input type="password" name="newPassword"  onkeypress="checkLength()"  value="" />
    <input id="registerSubmit" type="submit" value="Register" disabled="disabled"/>
</form>

<div id="registrationError"></div>


<p id="error"></p>

<script type="text/javascript">

    $(document).ready(function() {
        $('#registrationError').hide();

        $('#register').submit(function (e) {
            e.preventDefault();
            $.ajax('http://localhost:8080/register',{
                type: "POST",
                data : {
                    newUsername : document.getElementsByName('newUsername')[0].value,
                    newPassword : document.getElementsByName('newPassword')[0].value
                },
                success: function (responseText) {

                    $('#newUsername').val('');
                    $('#newPassword').val('');

                    $('#registrationError').html('<p>You have successfully registered!</p>');
                    $('#registrationError').css("color", "green");
                    $('#registrationError').show();
                },
                error: function (jqXHR, textStatus, errorMessage) {
                    $('#registrationError').html('<p>The username you have entered is already in use</p>');
                    $('#registrationError').css("color", "red");
                    $('#registrationError').show();

                }})
        })
    });


    function checkLength(){
        let username = document.getElementsByName('newUsername')[0].value;
        let password = document.getElementsByName('newPassword')[0].value;

        if((username.length > 0 && username.length <= 7) && (password.length >0)){
            $('#registerSubmit').removeAttr('disabled')
            return true;
        } else {
            $('#registerSubmit').attr('disabled', 'disabled')

            return false;
        }
    }
</script>

</body>
</html>
