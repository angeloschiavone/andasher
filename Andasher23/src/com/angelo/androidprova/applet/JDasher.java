package com.angelo.androidprova.applet;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.util.Log;

import com.angelo.androidprova.core.CDasherInterfaceBase;
import com.angelo.androidprova.core.CEvent;
import com.angelo.androidprova.core.CWrapperFactory;
import com.angelo.androidprova.core.SettingsAdaptor;
import com.angelo.androidprova.core.Esp_parameters;
import com.angelo.androidprova.resources.StaticResourceManager;

import dasher.utils.FileListReader;

/**
 * Simple implementation of CDasherInterfaceBase providing minimal
 * facilities to support an Applet version of Dasher.
 */
public class JDasher extends CDasherInterfaceBase {

    /**
     * The Logger for this class.
     */
    //private static Logger log = Logger.getLogger(JDasher.class.getName());

    /**
     * SAX Parser Factory
     */
//    protected SAXParserFactory saxfac;

    /**
     * SAX Parser
     */
//    protected SAXParser parser;

    // <editor-fold defaultstate="collapsed" desc="protected File file;">
    protected File file;
    public static final String PROP_FILE = "file";

    /**
     * Get the value of file
     *
     * @return the value of file
     */
    public File getFile() {
        return file;
    }

    /**
     * Set the value of file
     *
     * @param file new value of file
     */
    public void setFile(File file) {
        File oldFile = this.file;
        this.file = file;
        propertyChangeSupport.firePropertyChange(PROP_FILE, oldFile, file);
    }

    public void newFile() {
        setFile(new File("dasher-" + Double.toHexString(Math.random()) + ".txt"));
        InvalidateContext(true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Property change support">
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }// </editor-fold>

    
    /**
     * Our mouse input
     */
    private JMouseInput m_MouseInput;
    /**
     * Host to notify of events and request redraws.
     */
    private JDasherHost m_Host;

    /**
     * Creates a new JDasher. We call CreateSettingsStore immediately
     * after our super-constructor, create, wrap and register a new
     * JMouseInput, run CreateInput and register our newly created
     * input device wiht our host to hook it up to mouse events.
     *
     * @param host Host to report events and request mouse events
     * and redraws.
     */
    public JDasher(JDasherHost host, JDasherPanel2 dasherPanel) {
        super();
		
//        try {
//                saxfac = SAXParserFactory.newInstance();
//                parser = saxfac.newSAXParser();
//        }
//        catch(Exception e) {
//            log.log(Level.SEVERE, "Error creating SAX parser!", e);
//        }

        CreateSettingsStore();
 /*
        m_MouseInput = new JMouseInput(m_EventHandler, m_SettingsStore, dasherPanel);
        RegisterFactory(new CWrapperFactory(m_EventHandler, m_SettingsStore, m_MouseInput));
        CreateInput();

        m_Host = host;
        m_Host.regMouseMotionListener(m_MouseInput);
*/
    }
    
    
    public void setDasherPanel(JDasherPanel2 dasherPanel, JDasherHost host)
    {
        m_MouseInput = new JMouseInput(m_EventHandler, m_SettingsStore, dasherPanel);
       // Log.e("1 JDasher", "m_MouseInput "+(m_MouseInput==null));
        RegisterFactory(new CWrapperFactory(m_EventHandler, m_SettingsStore, m_MouseInput));
        CreateInput();

        m_Host = host;
        m_Host.regMouseMotionListener(m_MouseInput);
    	
    }
    

    public JDasherHost getHost() {
        return m_Host;
    }

    /**
     * External event handler; simply passes the event to our
     * host, typically a JDasherApplet.
     *
     * @param Event Event to handle
     */
    @Override
    public void ExternalEventHandler(CEvent Event) {

    	//Log.e("JDasher","type "+Event.m_iEventType + " Thread.currentThread().getName() " +Thread.currentThread().getName());
//    	Thread.currentThread().getName()
        m_Host.handleEvent(Event);
        
        
    }
 
    /**
     * Attempts to create a JSettings object; if a StoreUnavailableException
     * is produced in the course of this, we fall back and produce a
     * standard CSettingsStore.
     * <p>
     * If a SettingsStore already exists, we ignore the call.
     */
    public void CreateSettingsStore() {

        if (m_SettingsStore == null) {

//            try {
//                m_SettingsStore = new JSettings(m_EventHandler);
                m_SettingsStore = new SettingsAdaptor(m_EventHandler);
//            } catch (StoreUnavailableException e) {
//                // We can't use the registry/config file due to security problems.
//                m_SettingsStore = new CSettingsStore(m_EventHandler);
//                log.info("Can't access the registry.  Settings will not be saved.");
//            }
            StaticResourceManager.setSettingsStore(m_SettingsStore);

        }
    }



    /**
     * Attempts to retrieve a ResourceStream for a given file by
     * retrieving it from our JAR file, and returns its available
     * property which should indicate the file size.
     * <p>
     * In the event of failure of any sort, we return zero.
     *
     * @param strFileName File whose size we wish to retrieve
     * @return File size, or 0 on error.
     */
    public int GetFileSize(String strFileName) {
        try {
            java.io.InputStream in = getResourceStream(strFileName);
            return in.available();
        } catch (Exception e) { // Including if 'in' was null (throwing NullPointerException)
            return 0;
        }
    }

    /**
     * Populates a given Collection with a list of available
     * alphabet files.
     * <p>
     * Due to the difficulty in enumerating the contents of a JAR
     * file, at present this is hard coded to report a certain set.
     * <p>
     * Ideally this should be upgraded to report the true list of
     * available files, potentially by reading some master XML file.
     *
     * @param vFileList Collection to fill with a list of available alphabet files.
     */
    public void ScanAlphabetFiles(Collection<String> vFileList) {
    	Log.e("ScanAlphabetFiles ","-");
        vFileList.add("alphabet.english.xml");
        vFileList.add("alphabet.englishC.xml");
        vFileList.add("alphabet.Thai.xml");
     

     /* try {
        	
            FileListReader.parse(StaticResourceManager.getSystemResourceStream("alphabets.xml"), vFileList);
            InputStream in = StaticResourceManager.getUserResourceStream("alphabets.xml");
            if (in != null)
                FileListReader.parse(in, vFileList);
        } catch (Exception ex) {
            log.log(Level.WARNING, "There was a problem reading the alphabet list.  Trying to load defaults.", ex);
            Log.e("There was a problem reading the alphabet list.  Trying to load defaults.","");
            vFileList.add("alphabet.english.xml");
            vFileList.add("alphabet.englishC.xml");
            vFileList.add("alphabet.Thai.xml");
        } */
    }

    /**
     * Populates a given Collection with a list of available
     * colour files.
     * <p>
     * Due to the difficulty in enumerating the contents of a JAR
     * file, at present this is hard coded to report a certain set.
     * <p>
     * Ideally this should be upgraded to report the true list of
     * available files, potentially by reading some master XML file.
     *
     * @param vFileList Collection to fill with a list of available colour files.
     */
    public void ScanColourFiles(Collection<String> vFileList) {
        try {
            FileListReader.parse(getResourceStream("colours.xml"), vFileList);
        } catch (Exception ex) {
            //log.log(Level.WARNING, "There was a problem reading the color list.  Trying to load defaults.", ex);
            vFileList.add("colour.euroasian.xml");
            vFileList.add("colour.rainbow.xml");
            vFileList.add("colour.euroasian-new.xml");
            vFileList.add("colour.thai.xml");
        }
    }

    /**
     * Reads a list of XML files and returns an instance
     * of CAlphIO which knows about the alphabets found in these
     * files.
     * <p>
     * This class does it by the simple expedient of creating a new
     * CAlphIO and passing these files, but some extensions may
     * redirect the request in the case that the XML files have
     * already been parsed.
     *
     * @param vFiles List of files to be parsed
     * @return a CAlphIO which knows about the alphabets recorded in these files.
     */
//    @Override
//    public CAlphIO doAlphIO(ArrayList<String> vFiles) {
//        return new CAlphIO(vFiles, this);
//    }

    /**
     * Attempts to retrieve a resource stream representing a given
     * file by first trying to open it on the local filesystem,
     * and if that fails using the getResourceAsStream method.
     *
     * @return InputStream pointing to the relevant file if possible,
     * or null if not.
     */
    @Override
    public InputStream getResourceStream(String filename) {
        try {
            FileInputStream in = new FileInputStream(filename);
            if (in != null) {
                return in;
            }
        } catch (IOException e) {

        }
        InputStream in = getClass().getResourceAsStream(filename);
        //log.finer(String.format("Resource stream for %s is %s", filename, in));
        if (in != null) {
            return in;
        } else {
            return getClass().getResourceAsStream(GetStringParameter(Esp_parameters.SP_SYSTEM_LOC) + filename);
        }
        // TODO: why won't redirecting JDasher.getResourceStream to StaticResourceManager work?
//        return StaticResourceManager.getResourceStream(filename);
    }

    /**
     * Sets our system path to "system.rc/".
     */
    public void SetupPaths() {

        m_SettingsStore.SetStringParameter(Esp_parameters.SP_SYSTEM_LOC, "/com/angelo/androidprova/resources/"/*"system.rc/"*/);

    }

    /**
     * Stub
     */
    public void SetupUI() {
        // Auto-generated method stub
    }

    /**
     * Orders our host to redraw.
     *
     * @param bChanged ignored
     */
    public void Redraw(boolean bChanged) {
        m_Host.Redraw();
    }
}
