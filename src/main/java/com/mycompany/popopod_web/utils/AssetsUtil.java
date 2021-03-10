/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web.utils;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Utility class around asset files(css, javascript...etc)
 * @author Emicr
 */
public class AssetsUtil {

    /**
     * get the newest update date of argument's file
     * <p>This method is usually called to refer to the newest version of assets. It's possible to escape from continue using old version cash of assets </p>
     * <p>Where this method is called is JSP file.When you refer to Javascript, CSS, write the JSP area and Call this method. </p>
     * @param path the abusolute path of file
     * @return newest update data(formatted <strong>yyyyMMddhhmmss</strong>)
     */
    public static String getLastModifiedDate(String path) {
        File file = new File(path);
        long lastModified = file.lastModified();
        return new SimpleDateFormat("yyyyMMddhhmmss").format(lastModified);
    }
}
