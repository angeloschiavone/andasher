package com.angelo.androidprova.core;

import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;
/**
 * Class representing a String to be drawn plus information
 * needed to draw it
 */
public class CTextString implements Poolable<CTextString> {
 

	private static final int POOL_LIMIT = 100000;
	private static final Pool<CTextString> sPool = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<CTextString>() {
				public CTextString newInstance() {
					return new CTextString();
				}

				public void onAcquired(CTextString element) {
				}

				public void onReleased(CTextString element) {
				}
			}, POOL_LIMIT));

	private CTextString mNext;

	public void setNextPoolable(CTextString element) {
		mNext = element;
	}

	public CTextString getNextPoolable() {
		return mNext;
	}

	public static CTextString acquire() {

		// Log.e("Diff","living "+(acquired-released));
		return sPool.acquire();
	}

	public void release() {

		// Log.e("release","release");
		sPool.release(this);
		/*
		 * if(m_Context!=null) if(m_Context instanceof CPPMContext)
		 * ((CPPMContext)m_Context).release();
		 */
	}

	private CTextString() {
	}

	/**
	 * Default constructor
	 * 
	 * @param str
	 *            String to draw
	 * @param x
	 *            x co-ordinate of top-left corner of the string's bounding box
	 * @param y
	 *            y co-ordinate of top-left corner of the string's bounding box
	 * @param iSize
	 *            font size
	 */
	private CTextString(String str, int x, int y, long iSize) {

		m_String = (str);
		m_x = (x);
		m_y = (y);
		m_iSize = (iSize);
		
		// Log.e("CTextString","constructor");
		
	}

	/**
	 * String to be drawn
	 */
	String m_String;
	/**
	 * x co-ordinate of top-left corner of the string's bounding box
	 */
	int m_x;
	/**
	 * y co-ordinate of top-left corner of the string's bounding box
	 */
	int m_y;
	/**
	 * Font size
	 */
	long m_iSize;

}
