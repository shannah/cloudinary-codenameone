/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;

/**
 *
 * @author shannah
 */
class MessageDigest {

    static MessageDigest getInstance(String shA1) throws NoSuchAlgorithmException {
        if (!"SHA-1".equals(shA1)) {
            throw new NoSuchAlgorithmException();
        }
        MessageDigest d = new MessageDigest();
        return d;
    }

    byte[] digest(byte[] bytes) {
        Digest digest = new SHA1Digest();
        byte[] resBuf = new byte[digest.getDigestSize()];

        digest.update(bytes, 0, bytes.length);
        digest.doFinal(resBuf, 0);
        return resBuf;
    }

}
