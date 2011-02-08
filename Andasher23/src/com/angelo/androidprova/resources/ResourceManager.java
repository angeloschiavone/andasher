/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.angelo.androidprova.resources;

import java.io.InputStream;

/**
 *
 * @author joshua
 */
public interface ResourceManager {

    public InputStream getSystemResourceStream(String name);

    public InputStream getUserResourceStream(String name);

    public InputStream getResourceStream(String name);

}
