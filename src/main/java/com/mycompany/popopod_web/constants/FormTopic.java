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
public enum FormTopic {
    REQSOUND("reqSound"),
    USERNAME("userName"),
    SOUNDPATH("soundPath"),
    REQQUEUE("reqQueue");

    private final String name;

    private FormTopic(String topic) {
        this.name = topic;
    }

    public String getName() {
        return name;
    }
}
