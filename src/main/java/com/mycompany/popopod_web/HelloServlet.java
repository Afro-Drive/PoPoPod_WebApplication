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

    @Override
    protected void doPost(
            HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("userName");
        req.setAttribute("userName", userName);

        String path = "/WEB-INF/views/HelloJSP.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }
}
