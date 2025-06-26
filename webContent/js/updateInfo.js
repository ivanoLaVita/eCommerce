document.addEventListener("DOMContentLoaded", function () {
    const usernameValidator = /^[a-zA-Z0-9_.]{4,15}$/;
    const nameValidator = /^[a-zA-Z]{3,}$/;

    let validUsername = true;
    let validPwdCheck = true;
    let validName = true;
    let validSurname = true;

    function showError(id, message) {
        const el = document.getElementById(id);
        if (el) {
            el.textContent = message;
            el.style.display = "block";
        }
    }

    function hideError(id) {
        const el = document.getElementById(id);
        if (el) {
            el.textContent = "";
            el.style.display = "none";
        }
    }

    // Username AJAX check
    const usernameField = document.getElementById("usernameNuovo");
    if (usernameField) {
        usernameField.addEventListener("keyup", function () {
            const username = this.value;
            if (username.match(usernameValidator)) {
                fetch("RegistrationServlet", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: `mode=checkUsername&username=${encodeURIComponent(username)}`
                })
                .then(response => response.text())
                .then(data => {
                    if (data.includes("not available")) {
                        validUsername = false;
                        showError("error-username", "Username già in uso");
                    } else {
                        validUsername = true;
                        hideError("error-username");
                    }
                });
            } else {
                validUsername = false;
                showError("error-username", "Username non valido (4-15 caratteri, lettere/numeri/_.).");
            }
        });
    }

    // Password
    const password = document.getElementById("password");
    const passwordCheck = document.getElementById("passwordCheck");
    if (password && passwordCheck) {
        passwordCheck.addEventListener("keyup", function () {
            if (password.value.length >= 5 && password.value === passwordCheck.value) {
                validPwdCheck = true;
                hideError("error-pwd");
            } else {
                validPwdCheck = false;
                showError("error-pwd", "Le password non coincidono o sono troppo corte");
            }
        });
    }

    // Nome
    const nome = document.getElementById("nomeNuovo");
    if (nome) {
        nome.addEventListener("keyup", function () {
            if (this.value.match(nameValidator)) {
                validName = true;
                hideError("error-name");
            } else {
                validName = false;
                showError("error-name", "Inserisci un nome valido (almeno 3 lettere)");
            }
        });
    }

    // Cognome
    const cognome = document.getElementById("cognomeNuovo");
    if (cognome) {
        cognome.addEventListener("keyup", function () {
            if (this.value.match(nameValidator)) {
                validSurname = true;
                hideError("error-surname");
            } else {
                validSurname = false;
                showError("error-surname", "Inserisci un cognome valido (almeno 3 lettere)");
            }
        });
    }

    
    const form = document.getElementById("modificaInfo");
    const submitBtn = document.getElementById("modificaInfoBtn");
    if (submitBtn && form) {
        submitBtn.addEventListener("click", function (e) {
            if (!(validUsername && validPwdCheck && validName && validSurname)) {
                e.preventDefault();
            }
        });
    }
});
