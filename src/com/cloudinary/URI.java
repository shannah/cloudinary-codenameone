/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary;

/**
 *
 * @author shannah
 */
class URI {

    String str;
    
    static URI create(String cloudinaryUrl) {
        URI out = new URI();
        out.str = cloudinaryUrl;
        return out;
    }

    String getHost() {
        String fullHost = com.codename1.io.Util.getURLHost(str);
        fullHost = com.codename1.io.Util.decode(fullHost, null, false);
        if (fullHost.indexOf("@") != -1) {
            return fullHost.substring(fullHost.indexOf("@")+1);
        }
        return fullHost;
    }

    String getUserInfo() {
        String fullHost = com.codename1.io.Util.getURLHost(str);
        fullHost = com.codename1.io.Util.decode(fullHost, null, false);
        if (fullHost.indexOf("@") != -1) {
            return fullHost.substring(0, fullHost.indexOf("@"));
        }
        return null;
    }

    String getPath() {
        return com.codename1.io.Util.decode(com.codename1.io.Util.getURLPath(str), null, false);
    }

    String getQuery() {
        if (str.indexOf("?") != -1) {
            String query = str.substring(str.indexOf("?"));
            if (query.indexOf("#") != -1) {
                query = query.substring(0, query.indexOf("#"));
            }
            return com.codename1.io.Util.decode(query, null, true);
        
        }
        return null;
        
    }
    
}
