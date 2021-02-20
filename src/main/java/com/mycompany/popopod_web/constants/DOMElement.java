/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.popopod_web.constants;

/**
 * DOM Element enum class
 * <p>usually treating the pair of Open Tag, Close Tag</p>
 * @author Emicr
 */
public enum DOMElement {

    UL("<ul>", "</ul>"),
    OL("<ol>", "</ol>"),
    LI("<li>", "</li>"),
    A("<a>", "</a>");


    private DOMElement(String open) {
        this.open = open;
        this.close = "";
    }

    private DOMElement(String open, String close) {
        this.open = open;
        this.close = close;
    }
    
    private final String open;
    private final String close;

    /**
     * get the open DOM Tage enum value
     * @return open DOM Tag
     */
    public String getOpen() {
        return open;
    }

    private void setOpen(String open) {
        // do nothing
    }

    /**
     * get the close DOM Tag enum value
     * @return 
     */
    public String getClose() {
        return close;
    }

    private void setClose(String close) {
        // do nothing
    }
}
