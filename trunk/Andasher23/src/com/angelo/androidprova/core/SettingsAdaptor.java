/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angelo.androidprova.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is an adaptor class for the new Settings class to fool old code
 * into thinking it's just using an ordinary CSettingsStore.
 *
 * @author joshua
 */
public class SettingsAdaptor extends CSettingsStore {

    /**
     * The Logger for this class.
     */
   // private static Logger log = Logger.getLogger(SettingsAdaptor.class.getName());

    /**
     * Same as new SettingsAdaptor..., true)
     *
     * @param pEventHandler Event handler which we should notify
     * of parameter changes
     */
    public SettingsAdaptor(CEventHandler pEventHandler) {

        super(pEventHandler);

    }

    public Settings.Setting getSettingFromParam(EParameters param) {
        if (param instanceof Ebp_parameters)
            return Settings.B.valueOf(((Ebp_parameters) param).name);
        if (param instanceof Elp_parameters)
            return Settings.L.valueOf(((Elp_parameters) param).name);
        if (param instanceof Esp_parameters)
            return Settings.S.valueOf(((Esp_parameters) param).name);
        return null;
    }

    /**
     * Creates a new SettingsStore and instructs it to retrieve persistent
     * settings if the backing store is ready to respond.
     *
     * @param pEventHandler Event handler to notify of parameter changes
     * @param readyYet Is the backing store ready; can we retrieve
     * persistent settings?
     */
//    public SettingsAdaptor(CEventHandler pEventHandler, boolean readyYet) {
//
//        m_pEventHandler = pEventHandler;
//        s_oParamTables = new CParamTables();
//
//        if (readyYet) { // If the backing store is ready (prepared by a subclass)
//            LoadPersistent();
//        }
//    }

    /* All C++-style integer based enums have now been replaced by three Enum types
     * which implement EParameters, meaning one can pass both a generic parameter AND
     * a specialised parameter. For references into the tables, the .ordinal() of
     * a specialised parameter is used. All switch() statements should now check
     * the parameter's type, cast it to the appropriate one, and then switch on
     * the relevant enum. Alternatively it may be possible to have cases of a
     * child-type, L've yet to check this.
     *
     * 14/07: The whole codebase is now converted to use the new parameter scheme.
     * It's broadly very solid; everything is passed around as enum types until the actual
     * load/store instructions in CSettingsStore, whereupon ordinals are taken.
     *
     * The only weakness is that one CANNOT in fact switch on an EParameters, since
     * there is no way for the compiler to know that all its children are Enums.
     * There may be some way around this -- some sort of enum-interface -- but
     * L haven't found it yet. This can be solved by splitting any switch
     * into three, type-checking, casting, and then switching in a type-specific
     * manner.
     */
    /**
     * NOOP: Already done by the Settings class
     */
    @Override
    public void LoadPersistent() {

    }

    /**
     * Sets the value of a given boolean parameter.
     * <p>
     * This will raise a ParameterNotificationEvent with our
     * event handler.
     *
     * @param iParameter Parameter to set
     * @param bValue New value for this parameter
     */
    @Override
    public void SetBoolParameter(Ebp_parameters iParameter, boolean bValue) {
        getSettingFromParam(iParameter).set(bValue);

        // Initiate events for changed parameter
        CParameterNotificationEvent oEvent = new CParameterNotificationEvent(iParameter);
        m_pEventHandler.InsertEvent(oEvent);
    }

    /**
     * Sets the value of a given long parameter.
     * <p>
     * This will raise a ParameterNotificationEvent with our
     * event handler.
     *
     * @param iParameter Parameter to set
     * @param lValue New value for this parameter
     */
    @Override
    public void SetLongParameter(Elp_parameters iParameter, long lValue) {
        getSettingFromParam(iParameter).set(lValue);

        // Initiate events for changed parameter
        CParameterNotificationEvent oEvent = new CParameterNotificationEvent(iParameter);
        m_pEventHandler.InsertEvent(oEvent);
    }

    /**
     * Sets the value of a given string parameter.
     * <p>
     * This will raise a ParameterNotificationEvent with our
     * event handler.
     *
     * @param iParameter Parameter to set
     * @param sValue New value for this parameter
     */
    @Override
    public void SetStringParameter(Esp_parameters iParameter, String sValue) {
        getSettingFromParam(iParameter).set(sValue);

        // Initiate events for changed parameter
        CParameterNotificationEvent oEvent = new CParameterNotificationEvent(iParameter);
        m_pEventHandler.InsertEvent(oEvent);

    }

    /**
     * Gets the value of a boolean parameter
     *
     * @param iParameter Parameter to query
     * @return Value of this parameter
     */
    @Override
    public boolean GetBoolParameter(Ebp_parameters iParameter) {
        return (Boolean) getSettingFromParam(iParameter).get();
    }

    /**
     * Gets the value of an integer parameter
     *
     * @param iParameter Parameter to query
     * @return Value of this parameter
     */
    @Override
    public long GetLongParameter(Elp_parameters iParameter) {
        return (Long) getSettingFromParam(iParameter).get();
    }

    /**
     * Gets the value of a String parameter
     *
     * @param iParameter Parameter to query
     * @return Value of this parameter
     */
    @Override
    public String GetStringParameter(Esp_parameters iParameter) {
        return (String) getSettingFromParam(iParameter).get();
    }

    /**
     * Resets a given parameter to its default value, as given
     * by its entry in CParamTables.
     *
     * @param iParameter Parameter to reset
     */
    @Override
    public void ResetParameter(EParameters iParameter) {
        getSettingFromParam(iParameter).reset();
    }

}
