function registerUser() {
    var regUsername = document.getElementById("regUsername").value;
    var regPassword = document.getElementById("regPassword").value;

    $.ajax({
        type: "POST",
        url: "/messenger/register",
        data: {username: regUsername, password: regPassword},
        success: function (response) {
            alert(response);
        },
        error: function (xhr, status, error) {
            alert("Error: " + xhr.responseText);
        }
    });
}

function sendMessage() {
    var sendUsername = document.getElementById("sendUsername").value;
    var message = document.getElementById("message").value;

    $.ajax({
        type: "POST",
        url: "/messenger/message",
        data: {username: sendUsername, message: message},
        success: function (response) {
            alert(response);
        },
        error: function (xhr, status, error) {
            alert("Error: " + xhr.responseText);
        }
    });
}

function readMessages() {
    var readUsername = document.getElementById("readUsername").value;

    $.ajax({
        type: "GET",
        url: "/messenger/message",
        data: {username: readUsername},
        success: function (response) {
            displayMessages(response);
        },
        error: function (xhr, status, error) {
            alert("Error: " + xhr.responseText);
        }
    });
}

function displayMessages(messages) {
    var messagesContainer = document.getElementById("messagesContainer");
    messagesContainer.innerHTML = "<h3>Messages:</h3><p>" + messages + "</p>";
}