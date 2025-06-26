$(document).ready(function(){

    let emailValidator = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    let usernameValidator = /^[a-zA-Z0-9_.]{4,15}$/;
    let nameValidator = /^[a-zA-Z]{3,}$/;
    let validEmail = false;
    let validUsername = false;
    let validPwd = false;
    let validPwdCheck = false;
    let validName = false;
    let validSurname = false;
    
    let slideEmail = 0;
    let slideUsername = 0;

    $("#email").keyup(function() {
        validateEmail();
    });
    
    $("#username").keyup(function (){
        validateUsername();
    });
    
    $("#password").keyup(function (){
        validatePassword();
    });
    
    $("#passwordConfirm").keyup(function () {
        validatePasswordMatch();
    });
    
    $("#firstName").keyup(function () {
        validateName();
    });
    
    $("#lastName").keyup(function () {
        validateSurname();
    });
    
    $(".register-form").submit(function(event) {
        validateAllFields(event);
    });

    function validateEmail() {
        let email = $("#email").val();
        
        if (email.match(emailValidator)) {
            $.ajax({
                type: "POST",
                url: "RegistrationServlet", 
                data: {
                    mode: "checkEmail",
                    email: email,
                },
                dataType: "html",
                success: function(data) {
                    if (data.match("not available")) { 
                        validEmail = false;
                        if (slideEmail === 0) {
                            $("#error-email").slideDown();
                            slideEmail = 1;
                        }
                        $("#error-email").text("Email already used");
                    } else if (data.match("available")) {
                        validEmail = true;
                        slideEmail = 0;
                        $("#error-email").slideUp();
                        $("#error-email").text("");
                    }
                }
            });

        } else {
            validEmail = false;
            if (slideEmail === 0) {
                $("#error-email").slideDown();
                slideEmail = 1;
            }
            $("#error-email").text("Choose a valid email");
        }

        if (email === "") {
            validEmail = false;
            if (slideEmail === 1) {
                slideEmail = 0;
                $("#error-email").slideUp();
            }
            $("#error-email").text("");
        }
    }
    
    function validateUsername() {
        let username = $("#username").val();

        if(username.match(usernameValidator)){
            $.ajax({
                type: "POST",
                url: "RegistrationServlet",
                data: {
                    mode: "checkUsername",
                    username: username,
                },
                dataType: "html",
                success: function(data) {
                    if(data.match("not available")) {
                        validUsername = false;
                        if(slideUsername === 0) {
                            $("#error-username").slideDown();
                            slideUsername = 1;
                        }
                        $("#error-username").text("Username not available and/or already used");
                    } else if (data.match("available")){
                        validUsername = true;
                        slideUsername = 0;
                        $("#error-username").slideUp();
                        $("#error-username").text("");
                    }
                }
            });

        } else {
            validUsername = false;
            if(slideUsername === 0) {
                $("#error-username").slideDown();
                slideUsername = 1;
            }
            $("#error-username").text("Choose a valid username");
        }

        if(username === ""){
            validUsername = false;
            if(slideUsername === 1) {
                slideUsername = 0;
                $("#error-username").slideUp();
            }
            $("#error-username").text("");
        }
    }
    
    function validatePassword() {
        let pwd = $("#password").val();
        let slidePwd = 0;
        
        if(pwd.length < 5){
            validPwd = false;
            if(slidePwd === 0){
                $("#error-pwd").text("Password must be at least 5 characters long");
                $("#error-pwd").slideDown();
                slidePwd = 1;
            } else {
                $("#error-pwd").text("Password must be at least 5 characters long");
            }
        } else {
            validPwd = true;
            
            if(slidePwd === 1){
                $("#error-pwd").slideUp();
                slidePwd = 0;
            }
            
            $("#error-pwd").text("");
        }
    }
    
    function validatePasswordMatch() {
        let pwd = $("#password").val();
        let pwdCheck = $("#passwordConfirm").val();
        let slidePwd = 0;
        
        if(pwdCheck === pwd){
            validPwdCheck = true;
            $("#error-pwdchk").slideUp();
            $("#error-pwdchk").text("");
        } else {
            validPwdCheck = false;
            $("#error-pwdchk").slideDown();
            $("#error-pwdchk").text("Password do not match");
        }
    }
    
    function validateName() {
        let nome = $("#firstName").val();
        let slideNome = 0;
        
        if(nome.match(nameValidator)){
            validName = true;
            
            if(slideNome === 1){
                slideNome = 0;
                $("#error-name").slideUp();
            }
            $("#error-name").html("");
        } else {
            validName = false;

            if(slideNome === 0){
                $("#error-name").slideDown();
                slideNome = 1;
            }
            
            $("#error-name").html("Please enter a valid first name");
        }
        
        if(nome === ""){
            validName = false;
            
            if(slideNome === 1){
                slideNome = 0;
                $("#error-name").slideUp();
            }
            
            $("#error-name").html("");
        }
    }
    
    function validateSurname() {
        let cognome = $("#lastName").val();
        let slideSurname = 0;
        
        if(cognome.match(nameValidator)){
            validSurname = true;
            
            if(slideSurname === 1){
                slideSurname = 0;
                $("#error-surname").slideUp();
            }
            $("#error-surname").html("");
        } else {
            validSurname = false;

            if(slideSurname === 0){
                $("#error-surname").slideDown();
                slideSurname = 1;
            }
            
            $("#error-surname").html("Please enter a valid last name");
        }
        
        if(cognome === ""){
            validSurname = false;
            
            if(slideSurname === 1){
                slideSurname = 0;
                $("#error-surname").slideUp();
            }
            
            $("#error-surname").html("");
        }
    }
    
    function validateAllFields(event) {
        validateEmail();
        validateUsername();
        validatePassword();
        validatePasswordMatch();
        validateName();
        validateSurname();
        
        if(!(validEmail && validPwdCheck && validUsername && validName && validSurname && validPwd)){
            event.preventDefault();
        }
    }

});
