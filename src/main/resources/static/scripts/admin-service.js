const url = window.location.pathname;
let form = document.getElementById('service-form');
let saveBtn = document.getElementById('saveBtn');
if (url.endsWith('services')){
    saveBtn.onclick = () => createConfirm('Ви дійсно хочете створити послугу?',
        () => onSubmitClickListener(form, "POST", "/api/admin/services"))
} else {
    const id = url.substring(url.lastIndexOf('/') + 1);
    saveBtn.onclick = () => createConfirm('Ви дійсно хочете змінити послугу?',
        () => onSubmitClickListener(form, "PUT", "/api/admin/services/" + id))
}

function onSubmitClickListener(form, method, action) {
    submitHandlerBody(form.id, method, action)
        .then(response => {
            if (response.ok) {
                createAlert("Послугу оновлено")
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при оновленні послуги. Перевірте введені дані")
        })
}