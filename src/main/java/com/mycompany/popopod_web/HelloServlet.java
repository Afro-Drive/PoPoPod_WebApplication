/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web;

import com.mycompany.popopod_web.constants.FormTopic;
import com.mycompany.popopod_web.constants.ServletConstants;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Initial processing servlet
 * @author Emicr
 */
public class HelloServlet extends HttpServlet {

    private static final String GUEST = "Guest";
    private static final String CONTENT_DISPOSITION = "Constent-Disposition";

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //文字コードの設定
        req.setCharacterEncoding(ServletConstants.UTF8);
        
        String userName = StringUtils.isEmpty(req.getParameter(FormTopic.USERNAME.getName())) ?
                GUEST : req.getParameter(FormTopic.USERNAME.getName());
        req.setAttribute(FormTopic.USERNAME.getName(),userName);

        req.setAttribute(FormTopic.SOUNDPATH.getName(), StringUtils.EMPTY);
        req.setAttribute(FormTopic.REQQUEUE.getName(), StringUtils.EMPTY);

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
            if(disposition.trim().startsWith(FormTopic.REQSOUND.getName())) {
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
}
