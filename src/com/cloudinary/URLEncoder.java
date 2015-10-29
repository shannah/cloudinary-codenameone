/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author shannah
 */
class URLEncoder {

    static String encode(String toString, String utF8) throws UnsupportedEncodingException{
        return com.codename1.io.Util.encodeUrl(toString);
    }
    
}
