$(document).ready(function () {
    let emailValidator = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\\.,;:\s@\"]+\.)+[^<>()[\]\\.,;:\s@\"]{2,})$/i;
    let usernameValidator = /^[a-zA-Z0-9_.]{4,15}$/;
    let nameValidator = /^[a-zA-Z]{3,}$/;

    //let validEmail = true;
    let validUsername = true;
    let validPwdCheck = true;
    let validName = true;
    let validSurname = true;

    //let slideEmail = 0;
    let slideUsername = 0;
    let slidePwd = 0;
    let slideNome = 0;
    let slideSurname = 0;

   /*
	 // Validate email
    $("#emailNuovo").keyup(function () {
        let email = $(this).val();

        if (email.match(emailValidator)) {
            $.ajax({
                type: "POST",
                url: "RegistrationServlet",
                data: {
                    mode: "checkEmail",
                    email: email,
                },
                dataType: "html",
                success: function (data) {
                    if (data.match("not available")) {
                        validEmail = false;
                        if (slideEmail === 0) {
                            $("#error-email").slideDown();
                            slideEmail = 1;
                        }
                        $("#error-email").text("Email already in use");
                    } else if (data.match("available")) {
                        validEmail = true;
                        slideEmail = 0;
                        $("#error-email").slideUp().text("");
                    }
                }
            });
        } else {
            validEmail = false;
            if (slideEmail === 0) {
                $("#error-email").slideDown();
                slideEmail = 1;
            }
            $("#error-email").text("Please enter a valid email address");
        }
    });*/

    // Validate username
    $("#usernameNuovo").keyup(function () {
        let username = $(this).val();

        if (username.match(usernameValidator)) {
            $.ajax({
                type: "POST",
                url: "RegistrationServlet",
                data: {
                    mode: "checkUsername",
                    username: username,
                },
                dataType: "html",
                success: function (data) {
                    if (data.match("not available")) {
                        validUsername = false;
                        if (slideUsername === 0) {
                            $("#error-username").slideDown();
                            slideUsername = 1;
                        }
                        $("#error-username").text("Username already in use");
                    } else if (data.match("available")) {
                        validUsername = true;
                        slideUsername = 0;
                        $("#error-username").slideUp().text("");
                    }
                }
            });
        } else {
            validUsername = false;
            if (slideUsername === 0) {
                $("#error-username").slideDown();
                slideUsername = 1;
            }
            $("#error-username").text("Invalid username (4-15 characters, letters/numbers/_. only)");
        }
    });

    // Validate password match
    $("#passwordCheck").keyup(function () {
        let pwd = $("#password").val();
        let pwdCheck = $("#passwordCheck").val();

        if (pwdCheck === pwd && pwd.length >= 5) {
            validPwdCheck = true;
            slidePwd = 0;
            $("#error-pwd").slideUp().text("");
        } else {
            validPwdCheck = false;
            if (slidePwd === 0) {
                $("#error-pwd").slideDown();
                slidePwd = 1;
            }
            $("#error-pwd").text("Passwords do not match");
        }
    });

    // Validate first name
    $("#nomeNuovo").keyup(function () {
        let nome = $(this).val();

        if (nome.match(nameValidator)) {
            validName = true;
            if (slideNome === 1) {
                slideNome = 0;
                $("#error-name").slideUp();
            }
            $("#error-name").text("");
        } else {
            validName = false;
            if (slideNome === 0) {
                $("#error-name").slideDown();
                slideNome = 1;
            }
            $("#error-name").text("Please enter a valid first name");
        }

        if (nome === "") {
            validName = false;
            if (slideNome === 1) {
                slideNome = 0;
                $("#error-name").slideUp();
            }
            $("#error-name").text("");
        }
    });

    // Validate last name
    $("#cognomeNuovo").keyup(function () {
        let cognome = $(this).val();

        if (cognome.match(nameValidator)) {
            validSurname = true;
            if (slideSurname === 1) {
                slideSurname = 0;
                $("#error-surname").slideUp();
            }
            $("#error-surname").text("");
        } else {
            validSurname = false;
            if (slideSurname === 0) {
                $("#error-surname").slideDown();
                slideSurname = 1;
            }
            $("#error-surname").text("Please enter a valid last name");
        }

        if (cognome === "") {
            validSurname = false;
            if (slideSurname === 1) {
                slideSurname = 0;
                $("#error-surname").slideUp();
            }
            $("#error-surname").text("");
        }
    });

    // Submit only if all fields are valid
    $("#modificaInfoBtn").click(function (event) {
        if (validPwdCheck && validUsername && validName && validSurname) {
            $("#modificaInfo").submit();
        } else {
            event.preventDefault();
        }
    });
});
