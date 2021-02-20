/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web.constants;

/**
 *
 * @author Emicr
 */
public enum ArrowFileExtension {

    MP3("mp3"),
    MP4("mp4"),
    MPEG("mpeg"),
    WAV("wav"),
    OGG("ogg");
    
    private String name;

    private ArrowFileExtension(String extension) {
        this.name = extension;
    }

    public String getName() {
        return name;
    }
}
