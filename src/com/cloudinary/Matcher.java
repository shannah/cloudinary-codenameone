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
public class Matcher {

    private RE r;
    private String str;
    Matcher(RE r, String str) {
        this.r = r;
    }
    
    public boolean find() {
        return r.match(str);
    }

    public String replaceFirst(String replacement) {
        return r.subst(str, replacement, RE.REPLACE_FIRSTONLY);
    }

    public boolean matches() {
        return r.match(str);
    }

    public String group(int i) {
        return str.substring(r.getParenStart(i), r.getParenEnd(i));
    }

    public int groupCount() {
        return r.getParenCount();
    }
    
}
