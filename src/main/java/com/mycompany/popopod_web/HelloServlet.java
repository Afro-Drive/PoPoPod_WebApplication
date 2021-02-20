/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Initial processing servlet
 * @author Emicr
 */
public class HelloServlet extends HttpServlet {
    
    private static final String UTF8 = "UTF-8";
    private static final String RESOURCES_PATH = "";

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //文字コードの設定
        req.setCharacterEncoding(UTF8);
        
        String userName = req.getParameter("userName");
        req.setAttribute("userName", userName);

        String path = "/WEB-INF/views/HelloJSP.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }
}
