<%-- 
    Document   : HelloJSP
    Created on : 2021/02/15, 7:51:26
    Author     : Emicr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./assets/css/musicPlayer.css">
        <title>PoPoPod Music Player</title>
    </head>
    <body>
        <% String userName = (String) request.getAttribute("userName");%>
        <h1>Hello <%= userName%>! Welcome to PoPoPod!</h1>
        <% String soundPath = (String) request.getAttribute("soundPath");%>
        <div id="audioPlayer">
            <audio controls>
                <source id="audioSource" src="<%= soundPath%>/beyond_rainbow.mp3">
            </audio>
        </div>
        <div id="file-drag-area">
            <div><label for="reqSound">select or drag&drop music</label></div>
            <input type="file" class="reqSound" name="reqSound">
        </div>
        <p><a href="index.html">トップページへ戻る</a></p>
        <script src="./assets/js/musicPlayer.js"></script>
    </body>
</html>
