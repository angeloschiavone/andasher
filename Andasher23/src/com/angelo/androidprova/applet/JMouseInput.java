package com.angelo.androidprova.applet;



import android.util.Log;

import com.angelo.androidprova.core.CDasherInput;
import com.angelo.androidprova.core.CEventHandler;
import com.angelo.androidprova.core.CSettingsStore;

/**
 * Simple mouse input device which uses a mouse motion listener to
 * track the mouse position and reports the latest reading when
 * GetCoordinates is called.
 * <p>
 * Only methods which differ significantly from their abstract meanings
 * documented in CDasherInput are documented here; for further details
 * see CDasherInput.
 */
public class JMouseInput extends CDasherInput  {

	JDasherPanel2 dasherPanel;
	
	public JMouseInput(CEventHandler EventHandler, CSettingsStore SettingsStore, JDasherPanel2 dasherPanel) {
		super(EventHandler, SettingsStore, 0, 0, "Mouse Input");
		this.dasherPanel = dasherPanel;
	}
	
	
	public int GetCoordinateCount() {
		return 2;
	}
 
	//return 0 if those reported are screen co-ordinates, or 1 if they are Dasher world co-ordinates.
	public int GetCoordinates(int iN, long[] Coordinates) {
		
		Coordinates[0] =  dasherPanel.getX();// mouseX;
		Coordinates[1] = dasherPanel.getY();// mouseY;
		//Log.e("JMouseInput", "dasherPanel.getX() "+dasherPanel.getX()+" , dasherPanel.getY() "+dasherPanel.getY());
		return 0;
	}
}
