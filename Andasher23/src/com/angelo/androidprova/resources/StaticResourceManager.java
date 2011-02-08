/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angelo.androidprova.resources;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.angelo.androidprova.core.CSettingsStore;


/**
 * <p>This class is what all dasher resources should come from.  All resources
 * should be in <code>dasher.resources</code> or a subpackage, and should be
 * accessed through <code>StaticResourceManager.getReasourceStream</code>.</p>
 *
 * @author joshua
 */
public class StaticResourceManager {

    /**
     * The Logger for this class.
     */
    private static Logger log = Logger.getLogger(StaticResourceManager.class.getName());

    private static CSettingsStore settings;
    private static File userdir
            = (new File(System.getProperty("user.home"), ".dasher"));

    static {
        try {
            userdir = userdir.getCanonicalFile();
        } catch (Exception e) {
        }
    }

    /**
     * Attempts to get the named resource first by looking for it in the
     * user directory and then by calling
     * <code>StaticResourceManager.class.getResourceAsStream</code>.
     *
     * TODO: StaticResourceManager does not appear to be getting stuff from userdir
     * 
     * @param resource the name of the resource relative to <code>dasher.system.rc</code>
     *                  or the user directory
     * @return the requested resource or <code>null</code> if not found
     */
    public static InputStream getResourceStream(String resource) {
        InputStream in = getUserResourceStream(resource);
        if (in == null) {
            return getSystemResourceStream(resource);
        }
        return in;
    }

    /**
     * Gets a resource directly from the user dir.
     *
     * @param resource the name of the resource relative to user dir.
     * @return the requested resource or <code>null</code> if not found
     */
    public static InputStream getUserResourceStream(String resource) {
        InputStream in = null;
        try {
            File file = new File(userdir, resource);
            long size = -1;
            size = file.length();
            in = name(new FileInputStream(file), file.toString(), size);
        } catch (Exception ex) {
            log.log(Level.FINE, "Resource not found: " + userdir + resource, ex);
        }
        return in;
    }

    /**
     * Gets a resource directly from the system dir.
     *
     * @param resource the name of the resource relative to <code>dasher.system.rc</code>
     * @return the requested resource or <code>null</code> if not found
     */
    public static InputStream getSystemResourceStream(String resource) {
        return name(StaticResourceManager.class.getResourceAsStream(resource), resource);
    }

    public static CSettingsStore getSettingsStore() {
        return settings;
    }

    public static void setSettingsStore(CSettingsStore newSettings) {
        settings = newSettings;
    }

    public static InputStream name(InputStream in, String name) {
        if (in == null)
            return in;
        else
            return new ResourceStream(in, name);
    }
    public static InputStream name(InputStream in, String name, long size) {
        if (in == null)
            return in;
        else
            return new ResourceStream(in, name, size);
    }
    
    public static File getUserDir() {
        return userdir;
    }

    protected static ResourceManager rcManager = null;
    public static ResourceManager getStaticRcManager() {
        if (rcManager == null)
            rcManager = new SRcManager();
        return rcManager;
    }

    static class SRcManager implements ResourceManager {

        public InputStream getSystemResourceStream(String name) {
            return StaticResourceManager.getSystemResourceStream(name);
        }

        public InputStream getUserResourceStream(String name) {
            return StaticResourceManager.getUserResourceStream(name);
        }

        public InputStream getResourceStream(String name) {
            return StaticResourceManager.getResourceStream(name);
        }

    }

}
