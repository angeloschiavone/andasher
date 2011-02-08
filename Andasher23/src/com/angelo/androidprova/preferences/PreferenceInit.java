package com.angelo.androidprova.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceInit {

	public PreferenceInit(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(DasherPreferenceActivity.KEY_MY_PREFERENCE,
				Context.MODE_PRIVATE);
		
		sp.getInt(DasherPreferenceActivity.KEY_MY_PREFERENCE, 50);
		/*sp.getInt(key, defValue)
		return new Pacco(sp.getString("trackingCode", ""),
				Pacco.MetodiDiSpedizione.key(sp.getInt("tipoSpedizione", 0)));*/
	}
}
