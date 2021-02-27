/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web;

import com.mycompany.popopod_web.constants.ArrowFileExtension;
import com.mycompany.popopod_web.constants.FormTopic;
import com.mycompany.popopod_web.constants.ServletConstants;
import java.io.File;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Emicr
 */
@WebServlet(name = "filedeploy", urlPatterns = "/filedeploy")
@MultipartConfig(
        location = "C:/Java_work/PoPoPod_tmp",
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 5)
public class FileDeployServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setCharacterEncoding(ServletConstants.UTF8);
        
        String userName = req.getParameter(FormTopic.USERNAME.getName());
        Part audioPart = req.getPart(FormTopic.REQSOUND.getName());
        
        // check uploaded music file extension
        String filename = audioPart.getSubmittedFileName();
        String diskRealPath = "";
        String servletPath = "";

        if(checkFileExtension(filename)) {
            diskRealPath = getServletContext().getRealPath(
                    ServletConstants.SLASH + ServletConstants.ASSETS_PATH + ServletConstants.SOUND_PATH);
            audioPart.write(diskRealPath + filename);

            servletPath =
                    ServletConstants.APP_ROOT_PATH
                    + ServletConstants.ASSETS_PATH
                    + ServletConstants.SOUND_PATH
                    + filename;
        }

        req.setAttribute("soundPath", servletPath);
        req.setAttribute("userName", userName);

        String path = "/WEB-INF/views/HelloJSP.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
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
        for(ArrowFileExtension extension : ArrowFileExtension.values()) {
            if(ext.startsWith(extension.getName())) {
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
        return filename.substring(startPos + 1);
    }
}
