/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angelo.androidprova.resources;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>A ResourceStream wraps an InputStream to give it a name and size.
 * For example:
 * </p>
 * <pre>
 *   InputStream in = ...;
 *   ResourceStream rs = new ResourceStream(in, "my ResourceStream");
 *   System.out.println(rs);
 * </pre>
 * <p>Will print <code>my ResourceStream</code>.
 */
public class ResourceStream extends FilterInputStream {

    String name;
    long size = 0;

    public ResourceStream(InputStream in, String name) {
        super(in);
        this.name = name;

        // Make a best guess about the size
        try {
            size = in.available();
        } catch (IOException ex) {
            size = 0;
        }
    }
    public ResourceStream(InputStream in, String name, long size) {
        super(in);
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return name;
    }

    public long getSize() {
        return size;
    }
}
