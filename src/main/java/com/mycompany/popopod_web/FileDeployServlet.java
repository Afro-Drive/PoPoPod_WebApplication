/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web;

import com.mycompany.popopod_web.constants.ArrowFileExtension;
import com.mycompany.popopod_web.constants.DOMElement;
import com.mycompany.popopod_web.constants.FormTopic;
import com.mycompany.popopod_web.constants.ServletConstants;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
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
        maxFileSize = 1024 * 1024 * 100,
        maxRequestSize = 1024 * 1024 * 50,
        fileSizeThreshold = 1024 * 1024 * 3)
public class FileDeployServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding(ServletConstants.UTF8);

        String userName = req.getParameter(FormTopic.USERNAME.getName());
        StringBuilder audioQueueDOM = new StringBuilder(DOMElement.OL.getOpen());
        String servletPath = "";
        boolean isFirst = true;

        List<Part> filteredParts = req.getParts()
                .stream()
                .filter(part -> part.getName().equals(FormTopic.REQSOUND.getName()))
                .map(part -> part)
                .collect(Collectors.toList());

        for(Part part : filteredParts) {
            String filename = part.getSubmittedFileName();

            // create DOM element of requested playing queue
            audioQueueDOM.append(DOMElement.LI.getOpen())
                    .append(DOMElement.A.getOpen())
                    .append(filename)
                    .append(DOMElement.A.getClose())
                    .append(DOMElement.LI.getClose());

            // check uploaded music file extension
            if (checkFileExtension(filename)) {
                this.saveToDisk(filename, part);

                if (isFirst) {
                    servletPath = this.generateServletPath(filename);
                    isFirst = false;
                }
            }
        }
        
        audioQueueDOM.append(DOMElement.OL.getClose());

        req.setAttribute(FormTopic.SOUNDPATH.getName(), servletPath);
        req.setAttribute(FormTopic.REQQUEUE.getName(), audioQueueDOM.toString());
        req.setAttribute(FormTopic.USERNAME.getName(), userName);

        String path = "/WEB-INF/views/HelloJSP.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }

    /**
     * generate the virtual file path on Servlet
     * @param filename to success deployment in disk
     * @return the virtula path on servlet
     */
    private String generateServletPath(String filename) {
        return new StringBuilder().append(ServletConstants.APP_ROOT_PATH)
                .append(ServletConstants.ASSETS_PATH)
                .append(ServletConstants.SOUND_PATH)
                .append(filename)
                .toString();
    }

    /**
     * save the file in disk with using part
     * @param filename to save to disk
     * @param part part object
     * @throws IOException 
     */
    private void saveToDisk(String filename, Part part) throws IOException {
        String diskRealPath = getServletContext().getRealPath(
                new StringBuffer()
                        .append(ServletConstants.SLASH)
                        .append(ServletConstants.ASSETS_PATH)
                        .append(ServletConstants.SOUND_PATH)
                        .append(filename)
                        .toString());
        part.write(diskRealPath);
    }

    /**
     * check if file extension is allowed
     *
     * @param filename to check the extension
     * @return true: allowed extension<br>false: not allowed extension
     */
    private boolean checkFileExtension(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return false;
        }

        String ext = getFileExtension(filename);
        for (ArrowFileExtension extension : ArrowFileExtension.values()) {
            if (ext.startsWith(extension.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * get file extension
     *
     * @param filename to get the extension
     * @return file extension or empty value if not exist extension
     */
    private String getFileExtension(String filename) {
        int startPos = StringUtils.lastIndexOf(filename, ".");
        if (startPos == -1) {
            return "";
        }
        return filename.substring(startPos + 1);
    }
}
