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
        <title>PoPoPod Music Player</title>
    </head>
    <body>
        <% String userName = (String) request.getAttribute("userName"); %>
        <h1>Hello <%= userName %>! Welcome to PoPoPod!</h1>
        <% String soundPath = (String) request.getAttribute("soundPath"); %>
        <audio controls>
            <source src="<%= soundPath %>/beyond_rainbow.mp3">
        </audio>
        
        <p><a href="index.html">トップページへ戻る</a></p>
    </body>
</html>
