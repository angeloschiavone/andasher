/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angelo.androidprova.core;

import java.io.File;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import android.util.Log;

/**
 * Manages the getting, setting, and persistant storage of dasher prefs.
 *
 * @author joshua
 */
public class Settings {

    /**
     * The Logger for this class.
     */
    //private static Logger log = Logger.getLogger(Settings.class.getName());

    /**
     * 
     */
    protected static Preferences prefs;
    protected static String prefsPrefix = "JDasher/";

    static {
        InitSettings();
    }

    protected static void InitSettings() {
        // TODO: Create a dummy Preferences class to handle AccessControlException
        try {
            prefs = Preferences.userRoot();
            LoadPersistentSettings();
        } catch (AccessControlException e) {
            Log.e("", "Can't access the registry.  Settings will not be saved.");
        }
    }

    protected static void LoadPersistentSettings() {
        for (Setting s : getAllSettings())
            s.load();
    }

    public static List<Setting> getAllSettings() {
        B[] bVals = B.values();
        L[] iVals = L.values();
        S[] sVals = S.values();
        int nTotal = bVals.length + iVals.length + sVals.length;
        ArrayList<Setting> settings = new ArrayList<Setting>(nTotal);
        for (B b : bVals)
            settings.add(b);
        for (L i : iVals)
            settings.add(i);
        for (S s : sVals)
            settings.add(s);
        return settings;
    }

    /**
     * Value indicating a persistent setting
     */
    public final static boolean PERS = true;

    public static interface Setting<T> {

        public boolean isPersistent();
        public String getDescription();
        public String getSName();

        public T getDefault();
        public T get();
        public void set(T value);
        public void reset();
        public void load();
        public String prefix();

        public void parseAndSet(String s);

        public void addSettingListener(SettingListener<T> lis);
        public void removeSettingListener(SettingListener<T> lis);

    }

    public static class SimpleSetting<T> implements Setting<T> {

        protected Preferences prefs;
        protected String name, description, prefix;
        protected boolean persistent;
        protected T defaultValue, value;

        protected Set<SettingListener<T>> listeners
                = new HashSet<SettingListener<T>>();

        public SimpleSetting(String name, boolean persistent, T defaultValue, String description) {
            this("", name, persistent, defaultValue, description, null);
        }
        public SimpleSetting(String name, boolean persistent, T defaultValue, String description, Preferences prefs) {
            this("", name, persistent, defaultValue, description, prefs);
        }
        public SimpleSetting(String prefix, String name, boolean persistent, T defaultValue, String description, Preferences prefs) {
            this.prefix = prefix;
            this.name = name;
            this.description = description;
            this.persistent = persistent;
            this.defaultValue = this.value = defaultValue;
            this.prefs = prefs;
        }

        public boolean isPersistent() {
            return persistent;
        }
        public String getDescription() {
            return description;
        }
        public String getSName() {
            return name;
        }

        public T getDefault() {
            return defaultValue;
        }
        public T get() {
            return value;
        }
        public void set(T value) {
            T old = get();
            this.value = value;
            save();
            sendSettingChangedEvent(old, value);
        }
        protected void sendSettingChangedEvent(T old, T value) {
            SettingChangedEvent<T> e = new SettingChangedEvent<T>(this, old);
            for (SettingListener<T> l : listeners)
                l.settingChanged(e);
        }

        public void reset() {
            set(getDefault());
        }
        public void load() {

        }
        public void save() {

        }
        public String prefix() {
            return prefix;
        }

        public void parseAndSet(String s) {
            throw new UnsupportedOperationException("parseAndSet not implimented for setting " + toString());
        }

        public void addSettingListener(SettingListener<T> lis) {
            listeners.add(lis);
        }
        public void removeSettingListener(SettingListener<T> lis) {
            listeners.remove(lis);
        }

        @Override
        public String toString() {
            String pers = (persistent ? "[P] " : "");
            if (value == defaultValue)
                return String.format("(%s%s%s = %s)", pers, prefix, name, value);
            else
                return String.format("(%s%s%s = %s [%s])", pers, prefix, name, value, defaultValue);
        }
    }

    public static interface SettingListener<T> {
        public void settingChanged(SettingChangedEvent<T> e);
    }

    public static class SettingEvent<T> {
        protected Setting<T> setting;
        public SettingEvent(Setting<T> setting) {
            this.setting = setting;
        }
        public Setting<T> getSetting() {
            return setting;
        }
        @Override
        public String toString() {
            return String.format("(%s: %s)", this.getClass(), setting);
        }
    }

    public static class SettingChangedEvent<T> extends SettingEvent<T>{
        protected T newVal, oldVal;
        public SettingChangedEvent(Setting<T> setting, T old) {
            super(setting);
            newVal = setting.get();
            oldVal = old;
        }
        public T getNewValue() {
            return newVal;
        }
        public T getOldValue() {
            return oldVal;
        }
        @Override
        public String toString() {
            return String.format("(%s: %s new: %s old: %s)",
                    this.getClass(), setting, newVal, oldVal);
        }
    }

    /**
     * Boolean settings
     */
    public enum B implements Setting<Boolean> {
        DRAW_MOUSE_LINE("DrawMouseLine", PERS, true, "Draw Mouse Line"),
        DRAW_MOUSE("DrawMouse", PERS, true, "Draw Mouse Position"),
        SHOW_SLIDER("ShowSpeedSlider", PERS, true, "ShowSpeedSlider"),
        START_MOUSE("StartOnLeft", PERS, true, "StartOnLeft"),
        START_SPACE("StartOnSpace", PERS, false, "StartOnSpace"),
        START_STYLUS("StartOnStylus", PERS, false, "StartOnStylus"),
        STOP_IDLE("StopOnIdle", PERS, false, "StopOnIdle"),
        KEY_CONTROL("KeyControl", PERS, false, "KeyControl"),
        CONTROL_MODE("ControlMode", PERS, false, "ControlMode"),
        COLOUR_MODE("ColourMode", PERS, true, "ColourMode"),
        MOUSEPOS_MODE("StartOnMousePosition", PERS, false, "StartOnMousePosition"),
        OUTLINE_MODE("OutlineBoxes", PERS, true, "OutlineBoxes"),
        PALETTE_CHANGE("PaletteChange", PERS, true, "PaletteChange"),
        NUMBER_DIMENSIONS("NumberDimensions", PERS, false, "NumberDimensions"),
        EYETRACKER_MODE("EyetrackerMode", PERS, false, "EyetrackerMode"),
        AUTOCALIBRATE("Autocalibrate", PERS, true, "Autocalibrate"),
        DASHER_PAUSED("DasherPaused", !PERS, true, "Dasher Paused"),
        GAME_MODE("GameMode", PERS, false, "Dasher Game Mode"),
        TRAINING("Training", !PERS, false, "Provides locking during training"),
        REDRAW("Redraw", !PERS, false, "Force a full redraw at the next timer event"),
        LM_DICTIONARY("Dictionary", PERS, true, "Whether the word-based language model uses a dictionary"),
        LM_LETTER_EXCLUSION("LetterExclusion", PERS, true, "Whether to do letter exclusion in the word-based model"),
        AUTO_SPEEDCONTROL("AutoSpeedControl", PERS, true, "AutoSpeedControl"),
        CLICK_MODE("ClickMode", PERS, false, "Dasher Click Mode"),
        LM_ADAPTIVE("LMAdaptive", PERS, true, "Whether language model should learn as you enter text"),
        BUTTONONESTATIC("ButtonOneStaticMode", PERS, false, "One-button static mode"),
        BUTTONONEDYNAMIC("ButtonOneDynamicMode", PERS, false, "One-button dynamic mode"),
        BUTTONMENU("ButtonMenuMode", PERS, false, "Button menu mode"),
        BUTTONPULSING("ButtonPulsingMode", PERS, false, "One-button dynamic pulsing mode"),
        BUTTONSTEADY("ButtonSteadyMode", PERS, true, "One-button dynamic steady mode"),
        BUTTONDIRECT("ButtonDirectMode", PERS, false, "Three-button direct mode"),
        BUTTONFOURDIRECT("ButtonFourDirectMode", PERS, false, "Four-button direct mode"),
        BUTTONALTERNATINGDIRECT("ButtonAlternatingDirectMode", PERS, true, "Alternating direct mode"),
        COMPASSMODE("ButtonCompassMode", PERS, false, "Compass mode"),
        SOCKET_INPUT_ENABLE("SocketInputEnable", PERS, false, "Read pointer coordinates from network socket instead of mouse"),
        SOCKET_DEBUG("SocketInputDebug", PERS, false, "Print information about socket input processing to console"),
        OLD_STYLE_PUSH("OldStylePush", PERS, false, "Old style node pushing algorithm"),
        CIRCLE_START("CircleStart", PERS, false, "Start on circle mode"),
        GLOBAL_KEYBOARD("GlobalKeyboard", PERS, false, "Whether to assume global control of the keyboard"),
        DELAY_VIEW("DelayView", !PERS, false, "Delayed dynamics (for two button mode)"),
        LM_REMOTE("RemoteLM", PERS, false, "Language model is remote and responds asynchronously."),
        CONNECT_LOCK("Connecting", !PERS, false, "Currently waiting for a connection to a remote LM; lock Dasher."),
        TEST("Test", !PERS, false, "A setting for testing SettingListeners.")
        ;

        boolean persistent;
        String description;

        public boolean isPersistent() {
            return persistent;
        }

        public String getDescription() {
            return description;
        }

        String sName;
        public String getSName() {
            return sName;
        }

        B(String sName, boolean pers, boolean def, String desc) {
            this(sName, pers, Boolean.valueOf(def), desc);
        }
        B(boolean pers, boolean def, String desc) {
            this("", pers, def, desc);
            sName = this.name();
        }

        /**
         * Creates a new boolean parameter
         *
         * @param sName
         * @param pers Persistent
         * @param def Default value
         * @param desc Human-readable description
         */
        B(String sName, boolean pers, Boolean def, String desc) {
            this.sName = sName;
            persistent = pers;
            description = desc;
            defVal = def;
            value = def;
        }
        Boolean defVal;
        Boolean value;

        public Boolean get() {
            return value;
        }

        public Boolean getDefault() {
            return defVal;
        }

        public void set(Boolean val) {
            Boolean old = value;
            value = val;
            if (prefs != null && persistent) {
                prefs.putBoolean(prefix() + sName, value);
            }
            SettingChangedEvent<Boolean> evt
                    = new SettingChangedEvent<Boolean>(this, old);
            for (SettingListener lis : listeners)
                lis.settingChanged(evt);
        }

        public B getThis() {
            return this;
        }

        public void reset() {
            set(defVal);
        }

        public void load() {
            if (prefs != null && persistent) {
                value = prefs.getBoolean(prefix() + sName, defVal);
            }
        }

        public void parseAndSet(String s) {
            set(Boolean.parseBoolean(s));
        }

        public String prefix() {
            return prefsPrefix + "B_";
        }

        protected Set<SettingListener<Boolean>> listeners
                = new HashSet<SettingListener<Boolean>>();
        public void addSettingListener(SettingListener<Boolean> lis) {
            listeners.add(lis);
        }
        public void removeSettingListener(SettingListener<Boolean> lis) {
            listeners.remove(lis);
        }
    }

    /**
     * Long settings
     */
    public enum L implements Setting<Long> {
        ORIENTATION("ScreenOrientation", PERS, -2, "Screen Orientation"),
        REAL_ORIENTATION("RealOrientation", !PERS, 0, "Actual screen orientation (allowing for alphabet default)"),
        MAX_BITRATE("MaxBitRateTimes100", PERS, 80, "Max Bit Rate Times 100"),
        VIEW_ID("ViewID", PERS, 1, "ViewID"),
        LANGUAGE_MODEL_ID("LanguageModelID", PERS, 0, "LanguageModelID"),
        DASHER_FONTSIZE("DasherFontSize", PERS, 1, "DasherFontSize"),
        UNIFORM("UniformTimes1000", PERS, 50, "UniformTimes1000"),
        YSCALE("YScaling", PERS, 0, "YScaling"),
        MOUSEPOSDIST("MousePositionBoxDistance", PERS, 50, "MousePositionBoxDistance"),
        STOP_IDLETIME("StopIdleTime", PERS, 1000, "StopIdleTime"),
        TRUNCATION("Truncation", PERS, 0, "Truncation"),
        TRUNCATIONTYPE("TruncationType", PERS, 0, "TruncationType"),
        LM_MAX_ORDER("LMMaxOrder", PERS, 5, "LMMaxOrder"),
        LM_EXCLUSION("LMExclusion", PERS, 0, "LMExclusion"),
        LM_UPDATE_EXCLUSION("LMUpdateExclusion", PERS, 1, "LMUpdateExclusion"),
        LM_ALPHA("LMAlpha", PERS, 49, "LMAlpha"),
        LM_BETA("LMBeta", PERS, 77, "LMBeta"),
        LM_MIXTURE("LMMixture", PERS, 50, "LMMixture"),
        MOUSE_POS_BOX("MousePosBox", !PERS, -1, "Mouse Position Box Indicator"),
        NORMALIZATION("Normalization", !PERS, 1 << 16, "Interval for child nodes"),
        LINE_WIDTH("LineWidth", PERS, 1, "Width to draw crosshair and mouse line"),
        LM_WORD_ALPHA("WordAlpha", PERS, 50, "Alpha value for word-based model"),
        USER_LOG_LEVEL_MASK("UserLogLevelMask", PERS, 0, "Controls level of user logging, 0 = none, 1 = short, 2 = detailed, 3 = both"),
        SPEED_DIVISOR("SpeedDivisor", !PERS, 100, "Factor by which to slow down (multiplied by 100)"),
        ZOOMSTEPS("Zoomsteps", PERS, 32, "Integerised ratio of zoom size for click/button mode, denom 64."),
        B("ButtonMenuBoxes", PERS, 4, "Number of boxes for button menu mode"),
        S("ButtonMenuSafety", PERS, 25, "Safety parameter for button mode, in percent."),
        Z("ButtonMenuBackwardsBox", PERS, 1, "Number of back-up boxes for button menu mode"),
        R("ButtonModeNonuniformity", PERS, 0, "Button mode box non-uniformity"),
        RIGHTZOOM("ButtonCompassModeRightZoom", PERS, 5120, "Zoomfactor (*1024) for compass mode"),
        BOOSTFACTOR("BoostFactor", !PERS, 100, "Boost/brake factor (multiplied by 100)"),
        AUTOSPEED_SENSITIVITY("AutospeedSensitivity", PERS, 100, "Sensitivity of automatic speed control (percent)"),
        SOCKET_PORT("SocketPort", PERS, 20320, "UDP/TCP socket to use for network socket input"),
        SOCKET_INPUT_X_MIN("SocketInputXMinTimes1000", PERS, 0, "Bottom of range of X values expected from network input"),
        SOCKET_INPUT_X_MAX("SocketInputXMaxTimes1000", PERS, 1000, "Top of range of X values expected from network input"),
        SOCKET_INPUT_Y_MIN("SocketInputYMinTimes1000", PERS, 0, "Bottom of range of Y values expected from network input"),
        SOCKET_INPUT_Y_MAX("SocketInputYMaxTimes1000", PERS, 1000, "Top of range of Y values expected from network input"),
        OX("OX", PERS, 2048, "X coordinate of crosshair"),
        OY("OY", PERS, 2048, "Y coordinate of crosshair"),
        MAX_Y("MaxY", PERS, 4096, "Maximum Y coordinate"),
        INPUT_FILTER("InputFilterID", PERS, 3, "Module ID of input filter"),
        CIRCLE_PERCENT("CirclePercent", PERS, 10, "Percentage of nominal vertical range to use for radius of start circle"),
        TWO_BUTTON_OFFSET("TwoButtonOffset", PERS, 1024, "Offset for two button dynamic mode")
        ;

        boolean persistent;
        public boolean isPersistent() {
            return persistent;
        }

        String description;

        public String getDescription() {
            return description;
        }

        String sName;
        public String getSName() {
            return sName;
        }

        L(String sName, boolean pers, long def, String desc) {
            this(sName, pers, Long.valueOf(def), desc);
        }
        L(boolean pers, long def, String desc) {
            this("", pers, def, desc);
        }

        /**
         * Creates a new long parameter
         *
         * @param sName
         * @param pers Persistent
         * @param def Default value
         * @param desc Human-readable description
         */
        L(String sName, boolean pers, Long def, String desc) {
            this.sName = sName;
            persistent = pers;
            description = desc;
            defVal = def;
            value = def;
        }
        
        Long defVal;
        Long value;
                
        public Long get() {
            return value;
        }
        public Long getDefault() {
            return defVal;
        }
        public void set(Long val) {
            Long old = value;
            value = val;
            if (prefs != null && persistent) {
                prefs.putLong(prefix() + sName, value);
            }
            SettingChangedEvent<Long> evt
                    = new SettingChangedEvent<Long>(this, old);
            for (SettingListener lis : listeners)
                lis.settingChanged(evt);
        }

        public void reset() {
            set(defVal);
        }

        public void load() {
            if (prefs != null && persistent) {
                value = prefs.getLong(prefix() + sName, defVal);
            }
        }

        public void parseAndSet(String s) {
            set(Long.parseLong(s));
        }

        public String prefix() {
            return prefsPrefix + "L_";
        }

        protected Set<SettingListener<Long>> listeners
                = new HashSet<SettingListener<Long>>();
        public void addSettingListener(SettingListener<Long> lis) {
            listeners.add(lis);
        }
        public void removeSettingListener(SettingListener<Long> lis) {
            listeners.remove(lis);
        }
    }
    
    /**
     * String settings
     */
    public enum S implements Setting<String> {
        ALPHABET_ID("AlphabetID", PERS, "Default", "AlphabetID"),
        ALPHABET_1("Alphabet1", PERS, "", "Alphabet History 1"),
        ALPHABET_2("Alphabet2", PERS, "", "Alphabet History 2"),
        ALPHABET_3("Alphabet3", PERS, "", "Alphabet History 3"),
        ALPHABET_4("Alphabet4", PERS, "", "Alphabet History 4"),
        COLOUR_ID("ColourID", PERS, "", "ColourID"),
        DEFAULT_COLOUR_ID("DefaultColourID", !PERS, "", "Default Colour ID (Used for auto-colour mode)"),
        DASHER_FONT("DasherFont", PERS, "", "DasherFont"),
        SYSTEM_LOC("SystemLocation", !PERS, "system.rc/", "System Directory"),
        // TODO: make the user directory actually do something
        USER_LOC("UserLocation", PERS, (new File(System.getProperty("user.home"), ".dasher")).toString(),
                "User Directory"),
        GAME_TEXT_FILE("GameTextFile", !PERS, "gamemode_english_GB.txt", "File with strings to practice writing"),
        TRAIN_FILE("TrainingFile", !PERS, "", "Training text for alphabet"),
        SOCKET_INPUT_X_LABEL("SocketInputXLabel", PERS, "x", "Label preceding X values for network input"),
        SOCKET_INPUT_Y_LABEL("SocketInputYLabel", PERS, "y", "Label preceding Y values for network input"),
        INPUT_FILTER("InputFilter", PERS, "Normal Control", "Input filter used to provide the current control mode"),
        INPUT_DEVICE("InputDevice", PERS, "Mouse Input", "Driver for the input device"),
        LM_HOST("LMHost", PERS, "", "Language Model Host"),
        Filename(!PERS, "", "Filename of the file JDasher is editing"),
        Dictionary(!PERS, "dict/english.dict", "The dictionary to use for spell checking"),
        Phonetic(!PERS, "dict/en_phonet.dat", "The phonetic dictionary to use for spell checking"),
        UserDictionary(PERS, Dictionary.get(), "The user dictionary to use for spell checking")
        ;
        
        boolean persistent;
        public boolean isPersistent() {
            return persistent;
        }
        
        String description;
        public String getDescription() {
            return description;
        }
        
        String sName;
        public String getSName() {
            return sName;
        }

        S(boolean pers, String def, String desc) {
            this(null, pers, def, desc);
        }

        /**
         * Creates a new string parameter
         *
         * @param sName
         * @param pers Persistent
         * @param def Default value
         * @param desc Human-readable description
         */
        S(String sName, boolean pers, String def, String desc) {
            if (sName == null)
                this.sName = toString();
            else
                this.sName = sName;
            persistent = pers;
            description = desc;
            defVal = def;
            value = def;
        }
        
        String defVal;
        String value;
                
        public String get() {
            return value;
        }
        public String getDefault() {
            return defVal;
        }
        public void set(String val) {
            String old = value;
            value = val;
            if (prefs != null && persistent) {
                prefs.put(prefix() + sName, value);
            }
            SettingChangedEvent<String> evt
                    = new SettingChangedEvent<String>(this, old);
            for (SettingListener lis : listeners)
                lis.settingChanged(evt);
        }

        public void reset() {
            set(defVal);
        }

        public void load() {
            if (prefs != null && persistent) {
                value = prefs.get(prefix() + sName, defVal);
            }
        }

        public void parseAndSet(String s) {
            set(s);
        }

        public String prefix() {
            return prefsPrefix + "S_";
        }

        protected Set<SettingListener<String>> listeners
                = new HashSet<SettingListener<String>>();
        public void addSettingListener(SettingListener<String> lis) {
            listeners.add(lis);
        }
        public void removeSettingListener(SettingListener<String> lis) {
            listeners.remove(lis);
        }
    }

    /**
     * String settings
     */
    public enum Files implements Setting<File> {
        UserLoc(PERS, new File(System.getProperty("user.home"), ".dasher"), "User directory"),
        TrainingFile(!PERS, "", "Training text for alphabet"),
        Filename(!PERS, "", "Filename of the file JDasher is editing"),
        Dictionary(!PERS, new File("dict", "english.dict"), "The dictionary to use for spell checking"),
        Phonetic(!PERS, new File("dict", "en_phonet.dat"), "The phonetic dictionary to use for spell checking"),
        UserDictionary(PERS, Dictionary.get(), "The user dictionary to use for spell checking")
        ;

        boolean persistent;
        public boolean isPersistent() {
            return persistent;
        }

        String description;
        public String getDescription() {
            return description;
        }

        String sName;
        public String getSName() {
            return sName;
        }

        Files(boolean pers, File def, String desc) {
            this(null, pers, def, desc);
        }

        Files(boolean pers, String filename, String desc) {
            this(null, pers, new File(filename), desc);
        }

        /**
         * Creates a new File parameter
         *
         * @param sName
         * @param pers Persistent
         * @param def Default value
         * @param desc Human-readable description
         */
        Files(String sName, boolean pers, File def, String desc) {
            if (sName == null)
                this.sName = toString();
            else
                this.sName = sName;
            persistent = pers;
            description = desc;
            defVal = def;
            value = def;
        }

        File defVal;
        File value;

        public File get() {
            return value;
        }
        public File getDefault() {
            return defVal;
        }
        public void set(File val) {
            File old = value;
            value = val;
            if (prefs != null && persistent) {
                prefs.put(prefix() + sName, value.toString());
            }
            SettingChangedEvent<File> evt
                    = new SettingChangedEvent<File>(this, old);
            for (SettingListener lis : listeners)
                lis.settingChanged(evt);
        }

        public void reset() {
            set(defVal);
        }

        public void load() {
            if (prefs != null && persistent) {
                value = new File(prefs.get(prefix() + sName, defVal.toString()));
            }
        }

        public void parseAndSet(String s) {
            set(new File(s));
        }

        public String prefix() {
            return prefsPrefix + "Files_";
        }

        protected Set<SettingListener<File>> listeners
                = new HashSet<SettingListener<File>>();
        public void addSettingListener(SettingListener<File> lis) {
            listeners.add(lis);
        }
        public void removeSettingListener(SettingListener<File> lis) {
            listeners.remove(lis);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args.length == 0) {
            cmdHelp(args);
        } else {
            cmd(args);
        }
    }

    protected static void cmd(String[] args) {
        if (args[0].equalsIgnoreCase("interactive")) {
            cmdInteractive(args);
        } else if (args[0].equalsIgnoreCase("resetall")) {
            cmdResetAll(args);
        } else if (args[0].equalsIgnoreCase("list")) {
            cmdList(args);
        } else if (args[0].equalsIgnoreCase("reset")) {
            cmdReset(args);
        } else if (args[0].equalsIgnoreCase("get")) {
            cmdGet(args);
        } else if (args[0].equalsIgnoreCase("set")) {
            cmdSet(args);
        } else if (args[0].equalsIgnoreCase("help")) {
            cmdHelp(args);
        } else {
            System.out.println("Invalid command");
            cmdHelp(args);
        }
    }

    protected static void cmdHelp(String[] args) {
        System.out.println("Usage: java dasher.core.Settings <command>");
        System.out.println("Accepted commands:");
        System.out.println("  interactive");
        System.out.println("  resetall");
        System.out.println("  list");
        System.out.println("  get <setting>");
        System.out.println("  set <setting> <value>");
    }

    protected static void cmdInteractive(String args[]) {
        System.out.println("Not implemened.");
    }
    protected static void cmdResetAll(String args[]) {
        for (Setting s : getAllSettings()) {
            s.reset();
        }
        System.out.println("Done reseting all settings.");
    }
    protected static void cmdList(String args[]) {
        List<Setting> settings = getAllSettings();
        for (Setting s : settings) {
            System.out.format("%s%s = %s (def %s)\n", s.prefix(), s.getSName(), s.get(), s.getDefault());
        }
    }
    protected static void cmdReset(String args[]) {
        System.out.println("Not implemened.");
    }
    protected static void cmdGet(String args[]) {
        System.out.println("Not implemened.");
    }
    protected static void cmdSet(String args[]) {
        System.out.println("Not implemened.");
    }

public enum Blah {
    alph, blah, cat;
    
    public Blah getThis() {
        return this;
    }
}

}
