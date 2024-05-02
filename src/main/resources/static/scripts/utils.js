function modalOnClose(modal, onClose) {
    modal.remove();
    if (onClose) {
        return onClose();
    }
}

function createAlert(text, onClose) {
    let {modal, modalContent, closeBtn} = createModal(text);

    modal.appendChild(modalContent);
    document.body.appendChild(modal);

    closeBtn.onclick = () => modalOnClose(modal, onClose);

    window.onclick = function(event) {
        if (event.target === modal) {
            return modalOnClose(modal, onClose);
        }
    }

    modal.style.display = "block";
}

function createConfirm(text, onConfirm, onCancel) {
    let {modal, modalContent, closeBtn} = createModal(text);

    let buttonsContainer = document.createElement("div");
    buttonsContainer.classList.add("buttons-container");
    modalContent.appendChild(buttonsContainer);

    // Додаємо кнопку підтвердження
    let confirmBtn = document.createElement("button");
    confirmBtn.textContent = "Підтвердити";
    confirmBtn.classList.add("confirm-button");
    buttonsContainer.appendChild(confirmBtn);

    // Додаємо кнопку скасування
    let cancelBtn = document.createElement("button");
    cancelBtn.textContent = "Скасувати";
    cancelBtn.classList.add("cancel-button");
    buttonsContainer.appendChild(cancelBtn);

    modal.appendChild(modalContent);
    document.body.appendChild(modal);

    closeBtn.onclick = () => modalOnClose(modal, onCancel);

    confirmBtn.onclick = () => modalOnClose(modal, onConfirm);

    cancelBtn.onclick = () => modalOnClose(modal, onCancel);

    window.onclick = function(event) {
        if (event.target === modal) {
            return modalOnClose(modal, onCancel);
        }
    }

    modal.style.display = "block";
}

function createModal(text) {
    let modal = document.createElement("div");
    modal.id = "modal";
    modal.classList.add("modal");

    let modalContent = document.createElement("div");
    modalContent.classList.add("modal-content");

    let closeBtn = document.createElement("span");
    closeBtn.classList.add("close");
    closeBtn.innerHTML = "&times;";
    modalContent.appendChild(closeBtn);

    let modalText = document.createElement("p");
    modalText.textContent = text;
    modalContent.appendChild(modalText);
    return {modal, modalContent, closeBtn};
}


function submitHandlerParam(formId, method, endpoint){
    const formData = new FormData(document.getElementById(formId));
    const params = new URLSearchParams();
    formData.forEach((value, key) => {
        params.append(key, value);
    });
    const endpointWithParams = endpoint + "?" + params.toString();

    return fetch(endpointWithParams, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
    })
}

function submitHandlerBody(formId, method, endpoint){
    const formData = Object.fromEntries(new FormData(document.getElementById(formId)));
    for (let key in formData) {
        if (formData[key] === "on") {
            formData[key] = true;
        }
    }
    const body = JSON.stringify(formData);
    console.log(body)
    return fetch(endpoint, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: body
    })
}

function removeElements(attribute, value) {
    let elements = document.querySelectorAll(`[${attribute}="${value}"]`);
    elements.forEach(element => element.remove());
}