

document.getElementById("register-button").addEventListener("click", (event) => {
    submitHandlerBody("register-form", "POST", "api/register")
        .then(response => {
            if (response.ok) {
                createAlert("Реєстрація пройшла успішно. Тепер ви можете увійти в систему.", () => redirectToLogin())
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при реєстрації. Такий користувач вже існує.")
        })

})

function redirectToLogin(){
    window.location.assign("/login");
}
