package com.angelo.androidprova.preferences;

import com.angelo.androidprova.core.CDasherModel;
import com.angelo.androidprova.graphic.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.CheckBoxPreference;
import android.util.Log;
import android.widget.Toast;

/**
 * Example that shows finding a preference from the hierarchy and a custom
 * preference type.
 */
public class DasherPreferenceActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	public static final String KEY_MY_PREFERENCE = "my_preference";
	public static final String KEY_ADVANCED_CHECKBOX_PREFERENCE = "advanced_checkbox_preference";

	private CheckBoxPreference mCheckBoxPreference;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the XML preferences file
		addPreferencesFromResource(R.xml.dasher_preferences);

		// Get a reference to the checkbox preference
		mCheckBoxPreference = (CheckBoxPreference) getPreferenceScreen()
				.findPreference(KEY_ADVANCED_CHECKBOX_PREFERENCE);
		Log.e("getPreferenceScreen().getKey() ", ""
				+ getPreferenceScreen().getKey());
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Start the force toggle
		// mForceCheckBoxRunnable.run();

		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	protected void onPause() {
		super.onPause();

		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);

		// mHandler.removeCallbacks(mForceCheckBoxRunnable);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// Let's do something when my counter preference value changes
		// CDasherModel.speedIncrease = sharedPreferences.getInt(key, 0)*10;
		Log.e("sharedPreferences.toString() ", " "
				+ sharedPreferences.toString());
		// Log.e("speedIncrease","speedIncrease "+CDasherModel.speedIncrease);
		/*
		 * if (key.equals(KEY_MY_PREFERENCE)) { Toast.makeText(this,
		 * "Thanks! You increased my count to " + sharedPreferences.getInt(key,
		 * 0), Toast.LENGTH_SHORT).show();
		 * 
		 * SharedPreferences sp = getSharedPreferences("pippo0",
		 * Context.MODE_PRIVATE); Editor editor = sp.edit();
		 * 
		 * editor.putInt("pippo1", sharedPreferences.getInt(key, 0));
		 * editor.commit(); } else
		 */
		if (key.equals("speed_preference")) {
			Toast.makeText(
					this,
					"You increased my count to "
							+ sharedPreferences.getInt(key, 0),
					Toast.LENGTH_SHORT).show();

			SharedPreferences sp = getSharedPreferences("speed_shared_preference",
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();

			editor.putInt("speed_shared_preference_speed", sharedPreferences.getInt(key, 0));
			editor.commit();
		}
		/*else if (key.equals("input_preference")) {
			Toast.makeText(
					this,
					"You set input to "
							+ (sharedPreferences.getInt(key, 0)==0?"sensor":"touch"),
					Toast.LENGTH_SHORT).show();

			SharedPreferences sp = getSharedPreferences("input_shared_preference",
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putInt("input_shared_preference_input", sharedPreferences.getInt(key, 0));
			editor.commit();
		OptionSettings.useSensor = sharedPreferences.getInt(key, 0)==0;
		}*/
		else if (key.equals("input_x_preference")) {
			Toast.makeText(
					this,
					"You set horizontal input to "
							+ PreferenceInputX.getInputName (sharedPreferences.getInt(key, 0)),
					Toast.LENGTH_SHORT).show();

			SharedPreferences sp = getSharedPreferences("input_x_shared_preference",
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putInt("input_x_shared_preference_input_x", sharedPreferences.getInt(key, 0));
			editor.commit();
		OptionSettings.inputX = sharedPreferences.getInt(key, 0);
		}
		else if (key.equals("input_y_preference")) {
			Toast.makeText(
					this,
					"You set vertical input to "
							+ PreferenceInputX.getInputName (sharedPreferences.getInt(key, 0)),
					Toast.LENGTH_SHORT).show();

			SharedPreferences sp = getSharedPreferences("input_y_shared_preference",
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putInt("input_y_shared_preference_input_y", sharedPreferences.getInt(key, 0));
			editor.commit();
		OptionSettings.inputY = sharedPreferences.getInt(key, 0);
		}
		 
	}
}
