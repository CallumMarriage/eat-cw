<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Registration</title>


    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>

    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

</head>
<body>


<form id="register">
    <h2> Register Here </h2>
    Username:<input type="text" id="newUsername" name="newUsername" onkeypress="checkLength()" value=""/>----
    Password:<input type="password" name="newPassword" onkeypress="checkLength()" value=""/>
    <input id="registerSubmit" type="submit" value="Register" disabled="disabled"/>
</form>

<div id="registrationError"></div>

<script type="text/javascript">
    $(document).ready(function () {

        $('#register').submit(function (e) {
            let registrationError = $('#registrationError');
            registrationError.hide();

            e.preventDefault();
            $.ajax('http://localhost:8080/register', {
                type: "POST",
                data: {
                    newUsername: document.getElementsByName('newUsername')[0].value,
                    newPassword: document.getElementsByName('newPassword')[0].value
                },
                success: function (responseText) {

                    clearRegDetails();
                    window.location.href = "/";
                },
                error: function (jqXHR, textStatus, errorMessage) {
                    clearRegDetails();
                    registrationError.html('<p>'+jqXHR.responseXML.getElementsByTagName("message")[0].textContent+'</p>');
                    registrationError.css("color", "red");
                    registrationError.show();

                }
            })
        })
    });

    function checkLength() {
        let username = document.getElementsByName('newUsername')[0].value;
        let password = document.getElementsByName('newPassword')[0].value;

        if ((username.length > 0 && username.length <= 7) && (password.length > 0)) {
            $('#registerSubmit').removeAttr('disabled')
            return true;
        } else {
            $('#registerSubmit').attr('disabled', 'disabled')

            return false;
        }
    }

    function clearRegDetails() {
        $('#newUsername').val('');
        $('#newPassword').val('');
    }
</script>

</body>
</html>
