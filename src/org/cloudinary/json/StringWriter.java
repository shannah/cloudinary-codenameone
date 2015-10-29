/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudinary.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author shannah
 */
class StringWriter extends Writer {

    
    StringBuffer buf = new StringBuffer();
    
    
    StringBuffer getBuffer() {
        return buf;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        buf.append(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        
    }

    @Override
    public void close() throws IOException {
        
    }
    
}
