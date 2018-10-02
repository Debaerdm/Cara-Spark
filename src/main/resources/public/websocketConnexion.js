var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat");
webSocket.onmessage = function (user) {
    updateChat(user);
};
webSocket.onclose = function () { alert("WebSocket connection closed") };