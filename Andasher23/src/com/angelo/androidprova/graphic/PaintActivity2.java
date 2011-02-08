package com.angelo.androidprova.graphic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.angelo.androidprova.applet.JDasher;
import com.angelo.androidprova.applet.JDasherHost;
import com.angelo.androidprova.applet.JDasherPanel2;
import com.angelo.androidprova.applet.JDasherScreen;
import com.angelo.androidprova.applet.JMouseInput;
import com.angelo.androidprova.applet.MyTextView;
import com.angelo.androidprova.core.CDasherNode;
import com.angelo.androidprova.core.CEditEvent;
import com.angelo.androidprova.core.CEvent;
import com.angelo.androidprova.core.CPPMContext;
import com.angelo.androidprova.core.CSettingsStore;
import com.angelo.androidprova.core.CTextString;
import com.angelo.androidprova.core.DPoint;
import com.angelo.androidprova.core.DRect;
import com.angelo.androidprova.core.Elp_parameters;
import com.angelo.androidprova.core.GNRCReturn;
import com.angelo.androidprova.core.LongArrayPool;
import com.angelo.androidprova.core.NodeCannotBeDrawnException;
import com.angelo.androidprova.core.Point;
import com.angelo.androidprova.preferences.DasherPreferenceActivity;
import com.angelo.androidprova.preferences.OptionSettings;

public class PaintActivity2 extends Activity implements JDasherHost {

	private static final boolean TRACE_PERFORMANCE = false;
	static public PaintActivity2 mActivity;

	private static final int REQUEST_CODE_PREFERENCES = 1;

	private SensorManager mSensorManager;

	/**
	 * Instance of Dasher which does the work
	 */
	public JDasher dasher;
	/**
	 * Screen object to be used for drawing
	 */
	private JDasherScreen Screen;
	/**
	 * Panel object which will reflect those drawings on the GUI
	 */
	private JDasherPanel2 dasherPanel;
	private DisplayMetrics displayMetrics;
	public MyTextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		threadLoadObjectPools.start();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mActivity = this;

		// get screen width and height
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;

		setContentView(R.layout.main);

		textView = (MyTextView) findViewById(R.id.TextView01);

		// get handles to the JetView from XML and the JET thread.
		dasherPanel = (JDasherPanel2) findViewById(R.id.JDasherPanel2);

		/*
		 * Screen = new JDasherScreen(Dasher, width - 20, (int) (height * 0.9) -
		 * 40); dasherPanel = new JDasherPanel(Screen, Dasher, width - 20, (int)
		 * (height * 0.9) - 40, this); Dasher.setDasherPanel( dasherPanel,
		 * this); Dasher.Realize(); Dasher.ChangeScreen(Screen);
		 */
		// dasherPanel = new JDasherPanel2(Screen, Dasher, width - 20, (int)
		// (height * 0.9) - 40, this);

		// body to uncomment
		dasherPanel.setConstructorPrams(Screen, dasher, width, height - 20,
				this);
		dasher = new JDasher(this, dasherPanel);
		Screen = new JDasherScreen(dasher, width, height - 20);
		dasherPanel.setScreen(Screen, dasher, width, height - 20);
		dasher.setDasherPanel(dasherPanel, this);
		dasher.Realize();
		dasher.ChangeScreen(Screen);
		// setContentView(dasherPanel);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// loadObjectPools();

		dasherPanel.startWorker();

		SharedPreferences myPrefsSpeed = getSharedPreferences(
				"speed_shared_preference", MODE_WORLD_READABLE);
		int spd = myPrefsSpeed.getInt("speed_shared_preference_speed", 15);
		CSettingsStore.instance.SetLongParameter(Elp_parameters.LP_MAX_BITRATE,
				spd * 3);

		SharedPreferences myPrefs = getSharedPreferences(
				"input_shared_preference", MODE_WORLD_READABLE);
		boolean useSensor = myPrefs.getInt("input_shared_preference_input", 0) == 0;
		OptionSettings.useSensor = useSensor;

		SharedPreferences myPrefsInputX = getSharedPreferences(
				"input_x_shared_preference", MODE_WORLD_READABLE);
		int inputX = myPrefsInputX.getInt("input_x_shared_preference_input_x",
				0);
		OptionSettings.inputX = inputX;

		SharedPreferences myPrefsInputY = getSharedPreferences(
				"input_y_shared_preference", MODE_WORLD_READABLE);
		int inputY = myPrefsInputY.getInt("input_y_shared_preference_input_y",
				0);
		OptionSettings.inputY = inputY;
		// Log.e("spd", "spd " + spd);

	}

	Thread threadLoadObjectPools = new Thread(new Runnable() {

		public void run() {
			loadObjectPools();

		}
	});

	private void loadObjectPools() {
		Log.e("Memory", "start");
		int POOL_INIT_SIZE = 2000;
		CDasherNode[] nodes = new CDasherNode[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			nodes[i] = CDasherNode.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			nodes[i].release();
		}
		/*
		 * CPPMContext[] contexts = new CPPMContext[2]; for (int i = 0; i < 2;
		 * i++) { contexts[i] = CPPMContext.acquire(); } for (int i = 0; i < 2;
		 * i++) { contexts[i].release(); }
		 */
		LongArrayPool[] longArrayPools = new LongArrayPool[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			longArrayPools[i] = LongArrayPool.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			longArrayPools[i].release();
		}
		/*
		 * DasherNodeArrayPool[] dasherNodeArrayPool = new
		 * DasherNodeArrayPool[POOL_INIT_SIZE]; for (int i = 0; i <
		 * POOL_INIT_SIZE; i++) { dasherNodeArrayPool[i] =
		 * DasherNodeArrayPool.acquire(); } for (int i = 0; i < POOL_INIT_SIZE;
		 * i++) { dasherNodeArrayPool[i].release(); }
		 */
		DPoint[] element = new DPoint[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			element[i] = DPoint.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			element[i].release();
		}

		CTextString[] cTextString = new CTextString[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			cTextString[i] = CTextString.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			cTextString[i].release();
		}

		DRect[] rect = new DRect[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			rect[i] = DRect.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			rect[i].release();
		}
		GNRCReturn[] gNRCReturn = new GNRCReturn[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			gNRCReturn[i] = GNRCReturn.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			gNRCReturn[i].release();
		}
		NodeCannotBeDrawnException[] nodeCannotBeDrawnException = new NodeCannotBeDrawnException[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			nodeCannotBeDrawnException[i] = NodeCannotBeDrawnException
					.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			nodeCannotBeDrawnException[i].release();
		}
		Point[] point = new Point[POOL_INIT_SIZE];
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			point[i] = Point.acquire();
		}
		for (int i = 0; i < POOL_INIT_SIZE; i++) {
			point[i].release();
		}
		System.gc();
		Log.e("Memory", "stop");

	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.e("PaintActivity2", "onResume");
		if ((OptionSettings.inputX == OptionSettings.INPUT_SENSOR)
				| (OptionSettings.inputY == OptionSettings.INPUT_SENSOR)) {
			/*mSensorManager.registerListener(dasherPanel,
					mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_UI);*/
			mSensorManager.registerListener(dasherPanel,
					mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					SensorManager.SENSOR_DELAY_UI);
		/*	mSensorManager.registerListener(dasherPanel,
					mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
					SensorManager.SENSOR_DELAY_UI);*/
		}
	}

	public void Redraw() {
		dasherPanel.invalidate();
	}

	public void handleEvent(CEvent event) {
		if (event.m_iEventType == 2) { // EV_EDIT
			CEditEvent evt = (CEditEvent) event;
			StringBuffer sb = new StringBuffer(evt.m_sText);
			if (evt.m_iEditType == 1) { // New text!
				strText.append(sb);
			} else if (evt.m_iEditType == 2) // delete text
			{
				int indexDeleteFrom = strText.lastIndexOf(sb.toString());
				strText.delete(indexDeleteFrom, indexDeleteFrom + sb.length());
			}
			mHandler.post(mUpdateResults);
		}
	}

	// Need handler for callbacks to the UI thread
	final Handler mHandler = new Handler();
	static volatile StringBuffer strText = new StringBuffer(100);

	// Create runnable for posting
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			synchronized (strText) {
				textView.setText(strText);
			}
		}
	};

	/*
	 * if (event.m_iEventType == 2) { // EV_EDIT
	 * 
	 * CEditEvent evt = (CEditEvent) event; if (evt.m_iEditType == 1) { // New
	 * text!
	 * 
	 * if (!"".equals(this.getSelectedText())) { String text =
	 * getSelectedText(); supressNextEvent = true; this.replaceSelection(""); }
	 * supressNextEvent = true; int oldCaret = this.getCaretPosition();
	 * this.insert(evt.m_sText, oldCaret); this.setCaretPosition(oldCaret + 1);
	 * int newCaret = oldCaret + 1; } else if (evt.m_iEditType == 2) { // Delete
	 * text if (!"".equals(this.getText())) {
	 * 
	 * supressNextEvent = true;
	 * 
	 * if (!"".equals(this.getSelectedText())) { this.replaceSelection(""); }
	 * 
	 * supressNextEvent = true; int newCaret = this.getCaretPosition() -
	 * (evt.m_sText.length()); // this.replaceRange("", this.getCaretPosition()
	 * - // (evt.m_sText.length()), this.getCaretPosition());
	 * this.replaceRange("", newCaret, this.getCaretPosition());
	 * setCaretPosition(newCaret); } }
	 * 
	 * 
	 * }
	 * 
	 * if (event.m_iEventType == 3) { // Request for context
	 * 
	 * dasher.core.CEditContextEvent evt = (dasher.core.CEditContextEvent)
	 * event;
	 * 
	 * String NewContext;
	 * 
	 * int StartPosition = java.lang.Math.max(0, this.getSelectionStart() -
	 * evt.m_iMaxLength);
	 * 
	 * try { if (StartPosition == 0) { NewContext = this.getText(0,
	 * this.getSelectionStart()); } else { NewContext =
	 * this.getText(StartPosition, evt.m_iMaxLength); } } catch (Exception e) {
	 * return; }
	 * 
	 * evt.newContext = NewContext;
	 * 
	 * }
	 */

	public void regMouseMotionListener(JMouseInput e) {

		// this.addMouseMotionListener(e);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (TRACE_PERFORMANCE)
				Debug.stopMethodTracing();
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			Log.e("dasherPanel.getStartX() ", "" + dasherPanel.getStartX());
			if (dasherPanel.getStartX() == -1000) {

				dasher.PauseAt(0, 0);
				// dasherPanel.startWorker();
				dasherPanel.setStartX();

			} else {
				// dasherPanel.stopWorker();

				dasher.Unpause(0);
				dasherPanel.setStartX(-1000);

			}
		}
		Log.e("keyCode ", " keyCode " + keyCode);
		return super.onKeyDown(keyCode, event);
	}

	public void onStart() {

		super.onStart();

		Log.e("PaintActivity2", "onStart");

		// other shutdown code here
		if (TRACE_PERFORMANCE)
			Debug.startMethodTracing("andasher");
	}

	public void onStop() {
		super.onStop();

		Log.e("PaintActivity2", "onStop");
		// other shutdown code here
		// Debug.stopMethodTracing();
	}

	private static final int SETTINGS_MENU_ID = Menu.FIRST;
	private static final int EXIT_MENU_ID = Menu.FIRST + 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, SETTINGS_MENU_ID, 0, "Settings").setShortcut('3', 's');
		menu.add(0, EXIT_MENU_ID, 0, "Exit").setShortcut('4', 'e');

		/****
		 * Is this the mechanism to extend with filter effects? Intent intent =
		 * new Intent(null, getIntent().getData());
		 * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 * menu.addIntentOptions( Menu.ALTERNATIVE, 0, new ComponentName(this,
		 * NotesList.class), null, intent, 0, null);
		 *****/
		return true;
	}

	// Listen for results.
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// See which child activity is calling us back.
		switch (resultCode) {
		case REQUEST_CODE_PREFERENCES:
			// This is the standard resultCode that is sent back if the
			// activity crashed or didn't doesn't supply an explicit result.
			if (resultCode == RESULT_CANCELED) {

			} else {

			}
		default:
			break;
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		dasherPanel.stopWorker();
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// mPaint.setXfermode(null);
		// mPaint.setAlpha(0xFF);

		switch (item.getItemId()) {
		case SETTINGS_MENU_ID:

			Intent launchPreferencesIntent = new Intent().setClass(this,
					DasherPreferenceActivity.class);

			// Make it a subactivity so we know when it returns
			startActivityForResult(launchPreferencesIntent,
					REQUEST_CODE_PREFERENCES);

			// new ColorPickerDialog(this, this, mPaint.getColor()).show();
			return true;
		case EXIT_MENU_ID:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		Log.e("Andasher", "onPause");
		dasherPanel.stopWorker();
		dasher.m_DasherModel.m_LanguageModel.m_RootContext.head = null;
		mSensorManager.unregisterListener(dasherPanel);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("Andasher", "onDestroy");
	}
}
