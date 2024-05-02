const url = window.location.pathname;
let form = document.getElementById('office-form');
let saveBtn = document.getElementById('saveBtn');
if (url.endsWith('offices')){
    saveBtn.onclick = () => createConfirm('Ви дійсно хочете створити офіс?',
        () => onSubmitClickListener(form, "POST", "/api/admin/offices"))
} else {
    const id = url.substring(url.lastIndexOf('/') + 1);
    saveBtn.onclick = () => createConfirm('Ви дійсно хочете змінити офіс?',
        () => onSubmitClickListener(form, "PUT", "/api/admin/offices/" + id))
}

function onSubmitClickListener(form, method, action) {
    submitHandlerBody(form.id, method, action)
        .then(response => {
            if (response.ok) {
                createAlert("Офіс оновлено")
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при оновленні офісу. Перевірте введені дані")
        })
}