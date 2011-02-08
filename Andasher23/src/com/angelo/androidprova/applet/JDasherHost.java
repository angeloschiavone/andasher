package com.angelo.androidprova.applet;

import android.view.MotionEvent;

import com.angelo.androidprova.core.CEvent;


/** Capabilities required of anything which contains a Dasher control.
 * This provides decoupling between the Applet and the core, so that Dasher
 * can be integrated into any Java app.
 */
public interface JDasherHost {

	/**
	 * Should trigger a redraw of Dasher by any means necessary.
	 * <p>
	 * This may mean simply calling Dasher.Draw if no user interface
	 * setup is necessary.
	 * <p>
	 * Under Swing this typically means starting a top-level repaint.
	 *
	 */
	public void Redraw();
	
	/**
	 * Register ourselves to receive mouse motion events from the host.
	 * <p>
	 * The host should either register the submitted object as a MouseMotionListener
	 * or supply its own events by some other means.
	 * 
	 * @param e Listener to add
	 */
	public void regMouseMotionListener(JMouseInput e);
	
	/**
	 * Passes an event to the host; the host need not do anything
	 * if it doesn't want to.
	 * <p>
	 * At present, all events are passed up.
	 * 
	 * @param event Event being passed
	 */
	public void handleEvent(CEvent event);
	
}
