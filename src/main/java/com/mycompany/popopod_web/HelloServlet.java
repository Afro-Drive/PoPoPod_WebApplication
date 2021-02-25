/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Initial processing servlet
 * @author Emicr
 */
public class HelloServlet extends HttpServlet {
    
    private static final String UTF8 = "UTF-8";
    private static final String APP_ROOT_PATH = "http://localhost:8084/PoPoPod_Web/";
    private static final String ASSETS_PATH = "assets/";
    private static final String SOUND_PATH = "sound/";
    private static final String GUEST = "Guest";
    private static final String CONTENT_DISPOSITION = "Constent-Disposition";

    // to be enum object
    private static final List<String> ARROW_EXTENSIONS =
            Collections.unmodifiableList(Arrays.asList("mp3", "mp4", "mpeg", "wav", "ogg"));
    // ↓name of input element from top page(to be enum object)
    private static final String USERNAME = "userName";
    private static final String REQSOUND = "reqSound";

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //文字コードの設定
        req.setCharacterEncoding(UTF8);
        
        String userName = StringUtils.isEmpty(req.getParameter(USERNAME)) ?
                GUEST : req.getParameter(USERNAME);
        System.out.println(userName);
        req.setAttribute(USERNAME, userName);
        
        // check uploaded music file extension
        String soundPath = APP_ROOT_PATH + ASSETS_PATH + SOUND_PATH;
        req.setAttribute("soundPath", soundPath);

        String path = "/WEB-INF/views/HelloJSP.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }

    /**
     * get file name of requested music from user
     * @param part
     * @return music file name or null(if user sent no file)
     */
    private String getReqSoundFileName(Part part) {
        for(String disposition : part.getHeader(CONTENT_DISPOSITION).split(";")) {
            System.out.println(disposition);
            if(disposition.trim().startsWith(REQSOUND)) {
                String name = disposition.substring(
                        disposition.indexOf("=") + 1)
                        .replace("\"", "")
                        .trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                return name;
            }
        }

        return null;
    }

    /**
     * check if file extension is allowed
     * @param filename to check the extension
     * @return true: allowed extension<br>false: not allowed extension
     */
    private boolean checkFileExtension(String filename) {
        if(StringUtils.isEmpty(filename)) {
            return false;
        }

        String ext = getFileExtension(filename);
        for(String extension : ARROW_EXTENSIONS) {
            if(ext.startsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get file extension
     * @param filename to get the extension
     * @return file extension or empty value if not exist extension
     */
    private String getFileExtension(String filename) {
        int startPos = StringUtils.lastIndexOf(filename, ".");
        if(startPos == -1) {
            return "";
        }
        return filename.substring(startPos);
    }
}
