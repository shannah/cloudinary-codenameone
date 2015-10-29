/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary;

import com.codename1.util.regex.RE;

/**
 *
 * @author shannah
 */
public class Pattern {

    RE r;
    String pattern;
    
    public static Pattern compile(String string) {
        
        Pattern p = new Pattern();
        p.r = new RE(string);
        p.pattern = string;
        return p;
    }

    public Matcher matcher(String str) {
        return new Matcher(r, str);
    }

    public String pattern() {
        return pattern;
    }
    
}
