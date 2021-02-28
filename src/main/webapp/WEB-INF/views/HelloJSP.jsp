<%-- 
    Document   : HelloJSP
    Created on : 2021/02/15, 7:51:26
    Author     : Emicr
--%>

<%@page import="com.mycompany.popopod_web.constants.FormTopic"%>
<%@page import="com.mycompany.popopod_web.utils.AssetsUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./assets/css/musicPlayer.css?date='<%= AssetsUtil.getLastModifiedDate(config.getServletContext().getRealPath("/assets/css/musicPlayer.css")) %>'">
        <title>PoPoPod Music Player</title>
    </head>
    <body>
        <% String userName = (String) request.getAttribute(FormTopic.USERNAME.getName());%>
        <h1>Hello <%= userName%>! Welcome to PoPoPod!</h1>
        <% String soundPath = (String) request.getAttribute(FormTopic.SOUNDPATH.getName());%>
        <div id="audioPlayer">
            <audio controls>
                <source id="audioSource" src="<%= soundPath %>">
            </audio>
        </div>
        <div id="file-drop-area" ondrop="dropHandler(event);" ondragover="dragOverHandler(event);" ondragleave="dragLeaveHandler(event)">
            <form action="filedeploy" method="post" enctype="multipart/form-data">
                <div><label for="reqSound">select or drag&drop music</label></div>
                <input type="file" class="reqSound" name="reqSound">
                <input type="hidden" name="userName" value="<%= userName %>">
            </form>
        </div>
        <p><a href="index.html">トップページへ戻る</a></p>
        <script src="./assets/js/musicPlayer.js?date='<%= AssetsUtil.getLastModifiedDate(config.getServletContext().getRealPath("/assets/js/musicPlayer.js")) %>'"></script>
    </body>
</html>
