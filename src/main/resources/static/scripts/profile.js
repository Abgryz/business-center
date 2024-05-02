document.getElementById("saveBtn").addEventListener("click", (event) => {
    fetch("api/user?name=" + document.getElementById("name").value, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => {
            if (response.ok) {
                createAlert("Ім'я оновлено")
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при оновленні імені")
        })
})

function deleteRent(rentId) {
    fetch("api/rent/" + rentId, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => {
            if (response.ok) {
                createAlert("Арендування офісу видалено", () => removeElements("rent", rentId))
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при видаленні аренди")
        })
}

function deleteServiceOrder(serviceOrderId) {
    fetch("api/service/" + serviceOrderId, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => {
            if (response.ok) {
                createAlert("Замовлення послуги видалено", () => removeElements("serviceOrder", serviceOrderId))
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при видаленні послуги")
        })
}