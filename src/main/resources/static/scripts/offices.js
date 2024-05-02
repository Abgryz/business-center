

function onConfirmOffice(officeId, months) {
    console.log( officeId, months)
    return fetch("/api/rent/" + officeId + "?months=" + months, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            console.log(response)
            if (!response.ok) {
                response.text().then(text => createAlert(text))
            } else if (response.url.endsWith("login")){
                window.location.href = response.url
            } else {
                createAlert("Офіс успішно арендовано", () => removeElements("office", officeId))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при арендуванні офісу")
        })
}


function officeModal(officeId) {
    let modal = document.createElement("div");
    modal.id = "myModal";
    modal.classList.add("modal");

    let modalContent = document.createElement("div");
    modalContent.classList.add("modal-content");

    let closeSpan = document.createElement("span");
    closeSpan.classList.add("close");
    closeSpan.innerHTML = "&times;";

    let heading = document.createElement("h2");
    heading.textContent = "Підтвердження аренди офіса";

    let paragraph = document.createElement("p");
    paragraph.textContent = "Введіть кількість місяців, на які ви хочете здійснити оренду офіса:";

    // Додали контейнер для інпуту
    let inputContainer = document.createElement("div");
    inputContainer.classList.add("input-container");

    let inputMonths = document.createElement("input");
    inputMonths.type = "number";
    inputMonths.id = "months";
    inputMonths.placeholder = "Кількість місяців";
    inputMonths.min = "1";
    inputMonths.required = true;
    inputMonths.classList.add("input-field"); // Додаємо клас для поля введення

    // Додали інпут до контейнера
    inputContainer.appendChild(inputMonths);

    let confirmBtn = document.createElement("button");
    confirmBtn.id = "confirmBtn";
    confirmBtn.textContent = "Підтвердити аренду";
    confirmBtn.classList.add("confirm-button"); // Додаємо клас для кнопки
    inputContainer.appendChild(confirmBtn);

    modalContent.appendChild(closeSpan);
    modalContent.appendChild(heading);
    modalContent.appendChild(paragraph);
    modalContent.appendChild(inputContainer); // Додаємо контейнер до модального вікна замість прямого додавання інпуту
    modal.appendChild(modalContent);

    document.body.appendChild(modal);
    modal.style.display = "block";

    confirmBtn.onclick = function() {
        modal.style.display = "none";
        return onConfirmOffice(officeId, inputMonths.value);
    }

    closeSpan.onclick = function() {
        modal.remove();
    };
}

function deleteOffice(officeId) {
    return fetch("/api/admin/offices/" + officeId, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                createAlert("Офіс успішно видалено", () => removeElements("office", officeId))
            } else {
                response.text().then(text => createAlert(text))
            }
        })
        .catch(err => {
            console.error(err)
            createAlert("Виникла помилка при видаленні офіса")
        })
}
