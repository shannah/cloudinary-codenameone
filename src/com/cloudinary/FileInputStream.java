/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary;

import com.codename1.io.FileSystemStorage;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author shannah
 */
class FileInputStream extends InputStream {

    InputStream is;
    
    public FileInputStream(File file) throws IOException {
        is = FileSystemStorage.getInstance().openInputStream(file.path);
    }

    @Override
    public int read() throws IOException {
        return is.read();
    }

    @Override
    public int available() throws IOException {
        return is.available();
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

    @Override
    public boolean markSupported() {
        return super.markSupported(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int read(byte[] b) throws IOException {
        return is.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return is.read(b, off, len); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void reset() throws IOException {
        is.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void mark(int readlimit) {
        is.mark(readlimit); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long skip(long n) throws IOException {
        return is.skip(n); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
