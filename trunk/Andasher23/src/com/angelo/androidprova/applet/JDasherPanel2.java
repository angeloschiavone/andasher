package com.angelo.androidprova.applet;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.angelo.androidprova.preferences.OptionSettings;

public class JDasherPanel2 extends SurfaceView implements
		SurfaceHolder.Callback, SensorEventListener {

	class JDasherThread2 extends Thread {

		/*
		 * State-tracking constants
		 */
		public static final int STATE_LOSE = 1;
		public static final int STATE_PAUSE = 2;
		public static final int STATE_READY = 3;
		public static final int STATE_RUNNING = 4;
		public static final int STATE_WIN = 5;
		/*
		 * Member (state) fields
		 */
		/** The drawable to use as the background of the animation canvas */
		private Bitmap mBackgroundImage;

		/** Message handler used by thread to interact with TextView */
		private Handler mHandler;

		/** Paint to draw the lines on screen. */
		private Paint mLinePaint;

		/** The state of the . One of READY, RUNNING, PAUSE, LOSE, or WIN */
		private int mMode;

		/** Indicate whether the surface has been created & is ready to draw */
		private boolean mRun = false;

		/** Scratch rect object. */
		private RectF mScratchRect;

		/** Handle to the surface manager object we interact with */
		private SurfaceHolder mSurfaceHolder;

		public JDasherThread2(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			// get handles to some important objects
			mSurfaceHolder = surfaceHolder;
			mHandler = handler;
			mContext = context;

			// load background image as a Bitmap instead of a Drawable b/c
			// we don't need to transform it and it's faster to draw this way
			// mBackgroundImage = BitmapFactory.decodeResource(res,
			// R.drawable.earthrise);
			mBackgroundImage = Bitmap.createBitmap(width, height,
					Bitmap.Config.RGB_565);
			mBackgroundImage.eraseColor(Color.WHITE);

			// Initialize paints for speedometer
			mLinePaint = new Paint();
			mLinePaint.setAntiAlias(true);
			mLinePaint.setARGB(255, 0, 255, 0);

			mScratchRect = new RectF(0, 0, 0, 0);
		}

		/**
		 * Starts the game, setting parameters for the current difficulty.
		 */
		public void doStart() {
			synchronized (mSurfaceHolder) {
				setState(STATE_RUNNING);
			}
		}

		/**
		 * Pauses the physics update & animation.
		 */
		public void pause() {
			synchronized (mSurfaceHolder) {
				if (mMode == STATE_RUNNING)
					setState(STATE_PAUSE);
			}
		}

		/**
		 * Restores game state from the indicated Bundle. Typically called when
		 * the Activity is being restored after having been previously
		 * destroyed.
		 * 
		 * @param savedState
		 *            Bundle containing the game state
		 */
		public synchronized void restoreState(Bundle savedState) {
			synchronized (mSurfaceHolder) {
				setState(STATE_PAUSE);
			}
		}

		@Override
		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					c = mSurfaceHolder.lockCanvas(null);

					synchronized (mSurfaceHolder) {
						screen.drawToComponent(c);
						// PaintActivity2.mActivity.paintTextView();
						// Log.e("pippo", "pippo");
						// doDraw(c);
					}

					// Get a touch event:
					synchronized (dasher) {
						dasher.notify();
					}
					Thread.yield();

				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
				// System.gc();
				/*
				 * try { Thread.sleep(50); } catch (InterruptedException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 */

			}
		}

		/**
		 * Dump game state to the provided Bundle. Typically called when the
		 * Activity is being suspended.
		 * 
		 * @return Bundle with this view's state
		 */
		public Bundle saveState(Bundle map) {
			synchronized (mSurfaceHolder) {
				if (map != null) {
				}
			}
			return map;
		}

		/**
		 * Used to signal the thread whether it should be running or not.
		 * Passing true allows the thread to run; passing false will shut it
		 * down if it's already running. Calling start() after this was most
		 * recently called with false will result in an immediate shutdown.
		 * 
		 * @param b
		 *            true to run, false to shut down
		 */
		public void setRunning(boolean b) {
			mRun = b;
		}

		/**
		 * Sets the game mode. That is, whether we are running, paused, in the
		 * failure state, in the victory state, etc.
		 * 
		 * @see #setState(int, CharSequence)
		 * @param mode
		 *            one of the STATE_* constants
		 */
		public void setState(int mode) {
			synchronized (mSurfaceHolder) {
				setState(mode, null);
			}
		}

		/**
		 * Sets the game mode. That is, whether we are running, paused, in the
		 * failure state, in the victory state, etc.
		 * 
		 * @param mode
		 *            one of the STATE_* constants
		 * @param message
		 *            string to add to screen or null
		 */
		public void setState(int mode, CharSequence message) {
			/*
			 * This method optionally can cause a text message to be displayed
			 * to the user when the mode changes. Since the View that actually
			 * renders that text is part of the main View hierarchy and not
			 * owned by this thread, we can't touch the state of that View.
			 * Instead we use a Message + Handler to relay commands to the main
			 * thread, which updates the user-text View.
			 */
			synchronized (mSurfaceHolder) {
				mMode = mode;

				if (mMode == STATE_RUNNING) {
					Message msg = mHandler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("text", "");
					b.putInt("viz", View.INVISIBLE);
					msg.setData(b);
					mHandler.sendMessage(msg);
				} else {
					Resources res = mContext.getResources();
					CharSequence str = "";

					if (message != null) {
						str = message + "\n" + str;
					}

					Message msg = mHandler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("text", str.toString());
					b.putInt("viz", View.VISIBLE);
					msg.setData(b);
					mHandler.sendMessage(msg);
				}
			}
		}

		/* Callback invoked when the surface dimensions change. */
		public void setSurfaceSize(int width, int height) {
			// synchronized to make sure these all change atomically
			synchronized (mSurfaceHolder) {
				// don't forget to resize the background image
				mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage,
						width, height, true);
			}
		}

		/**
		 * Resumes from a pause.
		 */
		public void unpause() {
			// Move the real time clock up to now

			setState(STATE_RUNNING);
		}

		/**
		 * Handles a key-down event.
		 * 
		 * @param keyCode
		 *            the key that was pressed
		 * @param msg
		 *            the original event object
		 * @return true
		 */
		boolean doKeyDown(int keyCode, KeyEvent msg) {
			synchronized (mSurfaceHolder) {
				boolean okStart = false;
				if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
					okStart = true;
				if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
					okStart = true;
				if (keyCode == KeyEvent.KEYCODE_S)
					okStart = true;

				boolean center = (keyCode == KeyEvent.KEYCODE_DPAD_UP);

				if (okStart
						&& (mMode == STATE_READY || mMode == STATE_LOSE || mMode == STATE_WIN)) {
					// ready-to-start -> start
					doStart();
					return true;
				} else if (mMode == STATE_PAUSE && okStart) {
					// paused -> running
					unpause();
					return true;
				} else if (mMode == STATE_RUNNING) {
					// center/space -> fire
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
							|| keyCode == KeyEvent.KEYCODE_SPACE) {
						return true;
						// left/q -> left
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
							|| keyCode == KeyEvent.KEYCODE_Q) {
						return true;
						// right/w -> right
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
							|| keyCode == KeyEvent.KEYCODE_W) {
						return true;
						// up -> pause
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
						pause();
						return true;
					}
				}

				return false;
			}
		}

		/**
		 * Handles a key-up event.
		 * 
		 * @param keyCode
		 *            the key that was pressed
		 * @param msg
		 *            the original event object
		 * @return true if the key was handled and consumed, or else false
		 */
		boolean doKeyUp(int keyCode, KeyEvent msg) {
			boolean handled = false;

			synchronized (mSurfaceHolder) {
				if (mMode == STATE_RUNNING) {
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
							|| keyCode == KeyEvent.KEYCODE_SPACE) {
						handled = true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
							|| keyCode == KeyEvent.KEYCODE_Q
							|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
							|| keyCode == KeyEvent.KEYCODE_W) {
						handled = true;
					}
				}
			}

			return handled;
		}

		/**
		 * Draws the ship, fuel/speed bars, and background to the provided
		 * Canvas.
		 */
		private void doDraw(Canvas canvas) {
			// Draw the background image. Operations on the Canvas accumulate
			// so this is like clearing the screen.
			canvas.drawBitmap(mBackgroundImage, 0, 0, null);

			// canvas.drawBitmap(mBackgroundImage, 0, 0, null);

			for (int i = 0; i < 50; i++) {

				mLinePaint.setColor((int) (4294967295.0 * Math.random()));
				int side = (int) (100.0 * Math.random());
				// Log.e("color",
				// "(Math.random()*0xffffff) "+(((int)(Math.random()*0xffffff))));
				float x1 = (float) Math.random() * width;
				float y1 = (float) Math.random() * width;
				mScratchRect.set(x1, y1, x1 + side, y1 + side);
				canvas.drawRect(mScratchRect, mLinePaint);
			}
		}
	}

	private long mX, mY;
	/** Handle to the application context, used to e.g. fetch Drawables. */
	private Context mContext;
	private Bitmap mBitmap;
	private Paint mBitmapPaint;
	private int width;
	private int height;
	private int widthHalf;
	private int heightHalf;
	// <editor-fold defaultstate="collapsed" desc="screen prop">
	protected JDasherScreen screen;

	/**
	 * Get the value of screen
	 * 
	 * @return the value of screen
	 */
	public JDasherScreen getScreen() {
		return screen;
	}// </editor-fold>

	protected JDasherThread2 worker;
	JDasher dasher;

	public JDasherPanel2(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public JDasherPanel2(JDasherScreen newScreen, JDasher dasher, int width,
			int height, Context c) {
		super(c);
		// this.screen = newScreen;
		// this.worker = new JDasherThread(newScreen, dasher, width, height);
		// frameTimer = new Timer();
		// frameTask = new FrameTask2(this);

		// worker.start();

		this.dasher = dasher;
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mBitmap.eraseColor(0xffffffff);
		this.width = width;
		this.height = height;

	}

	public void setConstructorPrams(JDasherScreen newScreen, JDasher dasher,
			int width, int height, Context c) {

		// this.screen = newScreen;
		// this.worker = new JDasherThread(newScreen, dasher, width, height);
		// frameTimer = new Timer();
		// frameTask = new FrameTask2(this);

		// worker.start();

		this.dasher = dasher;
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mBitmap.eraseColor(0xffffffff);
		this.width = width;
		this.height = height;
	}

	public JDasherPanel2(JDasher dasher, int width, int height, Context c,
			Activity activity) {
		this(new JDasherScreen(dasher, width, height), dasher, width, height, c);
	}

	public void setScreen(JDasherScreen screen, JDasher dasher, int width,
			int height) {
		this.dasher = dasher;
		this.screen = screen;

		SurfaceHolder holder = getHolder();
		this.width = width;
		this.height = height;
		this.widthHalf = (width >> 1);
		this.heightHalf = (height >> 1);

		// holder.setType(android.view.SurfaceHolder.SURFACE_TYPE_HARDWARE);
		holder.addCallback(this);
		// this.worker = new JDasherThread2(screen, dasher, width, height);
		this.worker = new JDasherThread2(holder, getContext(), new Handler() {
			@Override
			public void handleMessage(Message m) {

				// mStatusText.setVisibility(m.getData().getInt("viz"));
				// mStatusText.setText(m.getData().getString("text"));
			}
		});
		// worker.start();
	}

	public void setPaused(boolean paused) {

	}

	// by angelo
	public void doKeyDown() {
		dasher.KeyDown(System.currentTimeMillis(), 100);

	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { // quit
	 * application if user presses the back key.
	 * Log.e("KeyEvent","KeyEvent "+keyCode);
	 * 
	 * if (keyCode == KeyEvent.KEYCODE_BACK) { // activity.finish();
	 * Log.e("KEYCODE_BACK","KEYCODE_BACK"); Debug.stopMethodTracing(); }
	 * 
	 * return true; }
	 */

	// static int touchCount = 0;

	@Override
	public boolean dispatchTouchEvent /* onTouchEvent */(MotionEvent event) {

		// if (touchCount++ % 5 == 0) {

		if (OptionSettings.inputX == OptionSettings.INPUT_TOUCH_MIRROR)
			mX = width - (long) event.getX();
		else if (OptionSettings.inputX == OptionSettings.INPUT_TOUCH_NORMAL)
			mX = (long) event.getX();
		if (OptionSettings.inputY == OptionSettings.INPUT_TOUCH_MIRROR)
			mY = height - (long) event.getY();
		else if (OptionSettings.inputY == OptionSettings.INPUT_TOUCH_NORMAL)
			mY = (long) event.getY();

		// }
		// SystemClock.sleep(16);

		synchronized (dasher) {
			try {
				dasher.wait(1000L);
			} catch (InterruptedException e) {
			}
		}

		/*
		 * try { Thread.sleep(16); } catch (InterruptedException e) {
		 * e.printStackTrace(); } event.recycle();
		 */
		/*
		 * try { Thread.sleep(16); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */

		/*
		 * 
		 * 
		 * switch (event.getAction()) {
		 * 
		 * case MotionEvent.ACTION_UP: doKeyDown(); break;
		 * 
		 * } event.recycle(); // invalidate(); // also works!!@TODO Angelo
		 * //dasher.Redraw(true);
		 * 
		 * // screen.drawToComponent(mCanvas); // dasher.Draw(true); //
		 * Log.e("JDAsherPAnel", "mx "+mX+" - mY "+mY);
		 */
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Log.e("KeyEvent","KeyEvent "+keyCode);

		if (keyCode == KeyEvent.KEYCODE_BACK) {

		}

		Log.e("keydown", "keyCode " + keyCode);

		return true;
	}

	private int startX = -1000;
	private int startZ = -1000;
	public int getStartX() {
		return startX;
	}

	public void setStartX() {

		this.startX = valueX;
		// Log.e("startAzimut", "startAzimut " + startX);
		doKeyDown();
	}

	public void setStartX(int val) {
		doKeyDown();
		this.startX = val;
	}
	public void setStartZ(int val) {
 
		this.startZ = val;
	}
	private int valueX = -1000;

	public long getX() {
		return (long) mX;
	}

	public long getY() {
		return (long) mY;
	}

	public void setX(long x) {
		mX = x;
	}

	public void setY(long y) {
		mY = y;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void startWorker() {
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		if (!worker.isAlive()) {
			worker = new JDasherThread2(getHolder(), getContext(),
					new Handler() {
						@Override
						public void handleMessage(Message m) {

							// mStatusText.setVisibility(m.getData().getInt("viz"));
							// mStatusText.setText(m.getData().getString("text"));
						}
					});

			worker.setRunning(true);
			worker.start();
		}
	}

	public void stopWorker() {
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		worker.setRunning(false);
		// worker.pause();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		startWorker();
		// dasher.Unpause(10);
		Log.e("JdasherPanel2", "surfaceCreated");
		try {
		} catch (Exception e) {
			// worker.resume();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e("JdasherPanel2", "surfaceDestroyed");
		// TODO Auto-generated method stub
		// worker.setRunning(false);
		// worker.pause()use();
		// stopWorker();
	}

	// int[] oldX = new int[] { 0, 0, 0 };
	// int[] oldY = new int[] { 0, 0, 0 };
	// int oldX = 0 ;
	// int oldY = 0 ;

	int startY = -1;

	static final int fattore = 3000000;
	static final int fattore2 = 100000;

	public void onSensorChanged(int sensor, float[] values) {

		// if (OptionSettings.useSensor)
		if ((OptionSettings.inputX == OptionSettings.INPUT_SENSOR)
				| (OptionSettings.inputY == OptionSettings.INPUT_SENSOR)) {
			// if (sensor == SensorManager.SENSOR_MAGNETIC_FIELD) {
			if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
				if (startX != -1000) {

					if (OptionSettings.inputX == OptionSettings.INPUT_SENSOR) {
						// int newX = Math.round((int) (values[0] * fattore)
						// + oldX[0] + oldX[1] + oldX[2]) >> 2;

						// int newX = Math.round((int) (values[0] * fattore) +
						// oldX) >> 1;
						int newX = Math.round((int) (values[0] * fattore));
						int clipX = (newX - startX) / 100000;
						clipX = Math.min(150, clipX);
						clipX = Math.max(clipX, -150);
						int mmX = clipX;
						if (values[2] < 0)
							mX = widthHalf + mmX;
						else
							mX = widthHalf - mmX;
						/*
						 * oldX[2] = oldX[1]; oldX[1] = oldX[0]; oldX[0] = newX;
						 */
						// oldX = newX;
					}
					if (OptionSettings.inputY == OptionSettings.INPUT_SENSOR) {

						
						// +" values[0] "+values[0] +" values[2] "+values[2] +
						// " startY "+startY);

						// SensorManager.getOrientation(R, values)

						/*
						 * int newY = Math.round((int) (values[1] * fattore) +
						 * oldY[0] + oldY[1] + oldY[2]) >> 2;
						 */
						// int newY = Math.round((int) (values[1] * fattore) +
						// oldY) >> 1;
						int newY = Math.round((int) (values[1] * fattore));
						int clipY = (newY - startY) / 100000;
						clipY = Math.min(150, clipY);
						clipY = Math.max(clipY, -150);
						if (values[2] < 0)
							mY = heightHalf - clipY;
						else
							mY = heightHalf + clipY;
						/*
						 * oldY[2] = oldY[1]; oldY[1] = oldY[0]; oldY[0] = newY;
						 */
						// oldY=newY;
					}
				} else {
					valueX = (int) (values[0] * fattore);
					startY = (int) values[1] * fattore;
					startZ = (int) values[2] * fattore;
				}
			}
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged2(SensorEvent event) {
		int sensor = event.sensor.getType();
		float[] values = event.values;
		// if (OptionSettings.useSensor)
		if ((OptionSettings.inputX == OptionSettings.INPUT_SENSOR)
				| (OptionSettings.inputY == OptionSettings.INPUT_SENSOR)) {
			
			if (sensor == Sensor.TYPE_ORIENTATION) {
				Log.e("TYPE_ORIENTATION", "values[2] "+values[2]+ "values[1] "+values[1]+ "values[0] "+values[0]);
			}
			
			
			// if (sensor == SensorManager.SENSOR_MAGNETIC_FIELD) {
			//if (sensor == Sensor.TYPE_ACCELEROMETER) {
			if (sensor == Sensor.TYPE_ORIENTATION) {
				if (startX != -1000) {

					if (OptionSettings.inputX == OptionSettings.INPUT_SENSOR) {
						// int newX = Math.round((int) (values[0] * fattore)
						// + oldX[0] + oldX[1] + oldX[2]) >> 2;

						// int newX = Math.round((int) (values[0] * fattore) +
						// oldX) >> 1;
						int newX = Math.round((int) (values[0] * fattore));
						int clipX = (newX - startX) / 100000;
						clipX = Math.min(150, clipX);
						clipX = Math.max(clipX, -150);
						int mmX = clipX;
					 	if (values[2] < 0)
							mX = widthHalf + mmX;
						else 
							mX = widthHalf - mmX;
						/*
						 * oldX[2] = oldX[1]; oldX[1] = oldX[0]; oldX[0] = newX;
						 */
						// oldX = newX;
					}
					// Log.e("TYPE_ACCELEROMETER", "values[2] "+values[2]+ "values[1] "+values[1]+ "values[0] "+values[0]);
					if (OptionSettings.inputY == OptionSettings.INPUT_SENSOR) {

						// Log.e("xxx", "values[1] "+values[1]
						// +" values[0] "+values[0] +" values[2] "+values[2] +
						// " startY "+startY);

						// SensorManager.getOrientation(R, values)

						/*
						 * int newY = Math.round((int) (values[1] * fattore) +
						 * oldY[0] + oldY[1] + oldY[2]) >> 2;
						 */
						// int newY = Math.round((int) (values[1] * fattore) +
						// oldY) >> 1;
						int newY = Math.round((int) (values[1] * fattore));
						int clipY = (newY - startY) / 100000;
						clipY = Math.min(150, clipY);
						clipY = Math.max(clipY, -150);
						if (values[2] < 0)
							mY = heightHalf - clipY;
						else
							mY = heightHalf + clipY;
						/*
						 * oldY[2] = oldY[1]; oldY[1] = oldY[0]; oldY[0] = newY;
						 */
						// oldY=newY;
					}
				} else {
					valueX = (int) (values[0] * fattore);
					startY = (int) values[1] * fattore;
					startZ = (int) values[2] * fattore;
				}
			}
		}
	}

	
	
	
	int oldMx=0,oldMy=0;
	public void onSensorChanged(SensorEvent event) {
		
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        	return; 
		
		int sensor = event.sensor.getType();
		float[] values = event.values;
		if ((OptionSettings.inputX == OptionSettings.INPUT_SENSOR)
				| (OptionSettings.inputY == OptionSettings.INPUT_SENSOR)) {
			
			/*
			if (sensor == Sensor.TYPE_ORIENTATION) {
				 Log.e("TYPE_ORIENTATION", "values[2] "+values[2]+ "values[1] "+values[1]+ "values[0] "+values[0]);
			}*/
			/*
 if (sensor == Sensor.TYPE_ACCELEROMETER) {
	 Log.e("TYPE_ACCELEROMETER", "values[2] "+values[2]+ "values[1] "+values[1]+ "values[0] "+values[0]);
 }*/
			/*
			 if (sensor == Sensor.TYPE_GYROSCOPE) {
				 Log.e("TYPE_GYROSCOPE", "values[2] "+values[2]+ "values[1] "+values[1]+ "values[0] "+values[0]);
			 }*/
			
			if (sensor == Sensor.TYPE_ORIENTATION) {
				if (startX != -1000) {

					if (OptionSettings.inputX == OptionSettings.INPUT_SENSOR) {
						int clipX = (( ( ((int) values[2]-startX)*(90-(int)Math.abs(values[1]))/90) + (((int)values[0]-startZ)*(int)Math.abs(values[1])/90)));
						clipX = ((clipX+oldMx)>>1);
						clipX = Math.min(20, clipX);
						clipX = Math.max(clipX, -20);
						oldMx = clipX;
						mX = widthHalf - (clipX<<3);
					}
					// Log.e("TYPE_ACCELEROMETER", "values[2] "+values[2]+ "values[1] "+values[1]+ "values[0] "+values[0]);
					if (OptionSettings.inputY == OptionSettings.INPUT_SENSOR) {
						
						int clipY = (int) values[1]-startY;
						clipY = ((clipY+oldMy)>>1);
						clipY = Math.min(20, clipY);
						clipY = Math.max(clipY, -20);
						oldMy = clipY;
						mY = heightHalf - (clipY<<3);
					}
				} else {
					valueX = (int) (values[2] /** fattore*/);
					startY = (int) values[1] /** fattore*/;
					startZ = (int) values[0] /** fattore*/;
				}
			}
		}
	}
	
}
