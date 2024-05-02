function onConfirmService(serviceId) {
    return fetch("/api/service/" + serviceId, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            console.log(response)
            if (!response.ok) {
                response.text().then(text => createAlert(text))
            } else if (response.url.endsWith("login")) {
                window.location.href = response.url
            } else {
                createAlert("Послуга успішно арендовано")
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при купівлі послуги")
        })
}

function deleteService(serviceId) {
    return fetch("/api/admin/services/" + serviceId, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                createAlert("Послуга успішно видалено", () => removeElements("service", serviceId))
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при видаленні послуги")
        })
}