<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Login / signup page</title>

        <script src="https://code.jquery.com/jquery-1.10.2.js"
                type="text/javascript"></script>

        <link rel="stylesheet" type="text/css"
              href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    </head>
    <body>
        <h2>Please log in!</h2>
        <form id="login">
            Username:<input id="username" type="text" name="username" value=""/>----
            Password:<input type="password" name="password"  value="" />
            <input id="loginSubmit" type="submit" value="Login" />
        </form>


        <div id="loginError">
        </div>
        <div id="registerLink"></div>


        <script type="text/javascript">

            $(document).ready(function() {

                $('#login').submit(function (e) {
                    let loginError = $('#loginError');
                    loginError.hide();

                    e.preventDefault();
                    $.ajax('http://localhost:8080/login',{
                        type: "POST",
                        data : {
                            newUsername : document.getElementsByName('username')[0].value,
                            newPassword : document.getElementsByName('password')[0].value
                        },

                        success: function (responseText) {

                            clearLoginDetails();
                            window.location.href = "viewTimetable";

                        },
                        error: function (jqXHR, textStatus, errorMessage) {
                            clearLoginDetails();
                            let errorCode = jqXHR.responseXML
                                .getElementsByTagName("error")[0]
                                .getElementsByTagName("code").item(0).textContent;

                            let errorMes = jqXHR.responseXML
                                .getElementsByTagName("error")[0]
                                .getElementsByTagName("message").item(0).textContent;

                            loginError.html('<p>'+errorMes+'</p>')

                            if(errorCode === "1"){
                                $('#registerLink').html('<a href="http://localhost:8080/registration">Click here to register</a>')
                            }
                            loginError.css("color", "red");
                            loginError.show();

                        }})
                })

            });

            function clearLoginDetails(){
                $('#username').val('');
                $('#password').val('');
            }

        </script>

    </body>
</html>
