/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.angelo.androidprova.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.angelo.androidprova.core.CSettingsStore;
import com.angelo.androidprova.core.Elp_parameters;
import com.angelo.androidprova.graphic.PaintActivity2;
import com.angelo.androidprova.graphic.R;

/**
 * This is an example of a custom preference type. The preference counts the
 * number of clicks it has received and stores/retrieves it from the storage.
 */
public class MyPreference extends Preference implements
		AdapterView.OnItemSelectedListener {
 
	private int mSpeed = -1;

	
	
	// This is the constructor called by the inflater
	public MyPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		setWidgetLayoutResource(R.layout.preference_widget);
	
		
		Log.e("getKey()",getKey());
		
		//this.setKey("angelo");
	}

	/**
	 * Names corresponding to the different example menu resources.
	 */
	private static final String sMenuExampleNames[] = { "Speed 0",
		"Speed 1",
		"Speed 2",
		"Speed 3",
		"Speed 4",
		"Speed 5",
		"Speed 6",
		"Speed 7",
		"Speed 8",
		"Speed 9"
		};

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);

		// Set our custom views inside the layout
		final Spinner mySpinner = (Spinner) view.findViewById(R.id.Spinner01);
		if (mySpinner != null) {
			// mySpinner.setSelection(0);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					PaintActivity2.mActivity,
					android.R.layout.simple_spinner_item, sMenuExampleNames);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// When programmatically creating views, make sure to set an ID
			// so it will automatically save its instance state
			if (mySpinner.getAdapter() == null)
				mySpinner.setAdapter(adapter);
			mySpinner.setOnItemSelectedListener(this);
			mySpinner.setSelection(mSpeed);

		}
	}

	/*
	 * @Override protected void onClick() { int newValue = mClickCounter + 1; //
	 * Give the client a chance to ignore this change if they deem it // invalid
	 * if (!callChangeListener(newValue)) { // They don't want the value to be
	 * set return; }
	 * 
	 * // Increment counter mClickCounter = newValue;
	 * 
	 * // Save to persistent storage (this method will make sure this //
	 * preference should be persistent, along with other useful checks)
	 * persistInt(mClickCounter);
	 * 
	 * // Data has changed, notify so UI can be refreshed! notifyChanged(); }
	 */

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		// This preference type's value type is Integer, so we read the default
		// value from the attributes as an Integer.
		return a.getInteger(index, 0);
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		if (restoreValue) {
			// Restore state
			// mClickCounter = getPersistedInt(mClickCounter);
			mSpeed = getPersistedInt(mSpeed);
		} else {
			// Set state
			int value = (Integer) defaultValue;
			mSpeed = value;
			persistInt(value);
			
		}
	}

	
	public int getSpeed()
	{
		return getPersistedInt(mSpeed);
		
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		/*
		 * Suppose a client uses this preference type without persisting. We
		 * must save the instance state so it is able to, for example, survive
		 * orientation changes.
		 */

		final Parcelable superState = super.onSaveInstanceState();
		if (isPersistent()) {
			// No need to save instance state since it's persistent
			return superState;
		}

		// Save the instance state
		final SavedState myState = new SavedState(superState);
		myState.mSpeed = mSpeed;
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (!state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}

		// Restore the instance state
		SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		mSpeed = myState.mSpeed;
		notifyChanged();
	}

	/**
	 * SavedState, a subclass of {@link BaseSavedState}, will store the state of
	 * MyPreference, a subclass of Preference.
	 * <p>
	 * It is important to always call through to super methods.
	 */
	private static class SavedState extends BaseSavedState {
		int mSpeed;

		public SavedState(Parcel source) {
			super(source);

			// Restore the click counter
			mSpeed = source.readInt();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);

			// Save the click counter
			dest.writeInt(mSpeed);
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		Log.e("mSpeed ", "position " + position);

		mSpeed = position;
		persistInt(mSpeed);

		CSettingsStore.instance.SetLongParameter(Elp_parameters.LP_MAX_BITRATE, mSpeed*30);
		
		/*
		 * mSpeed = position; Log.e("mSpeed ", "arg3 " + arg3); Log.e("mSpeed ",
		 * "mSpeed " + mSpeed);
		 * 
		 * 
		 * persistInt(mSpeed);
		 * 
		 * // Data has changed, notify so UI can be refreshed! notifyChanged();
		 */
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
