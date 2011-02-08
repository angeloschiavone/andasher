package com.angelo.androidprova.core;

import android.util.Log;



import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;
/**
 * Rectangle in Dasher space
 */
public class DRect  implements Poolable<DRect>{
	
	private static final int POOL_LIMIT = 100000;
	private static final Pool<DRect> sPool2 = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<DRect>() {
				public DRect newInstance() {
					return new DRect();
				}

				public void onAcquired(DRect element) {
				}

				public void onReleased(DRect element) {
				}
			}, POOL_LIMIT));

	private DRect mNext;

	public void setNextPoolable(DRect element) {
		mNext = element;
	}

	public DRect getNextPoolable() {
		return mNext;
	}

	public static DRect acquire() {
	
		// Log.e("Diff","living "+(acquired-released));
		return sPool2.acquire();
	}


	public void release() {
	
		 // Log.e("DRect","release");
		sPool2.release(this);
	}
	
	
	private DRect()
	{
		// Log.e("DRect","constructor ");
	     //new Exception().printStackTrace();
	}
	
	/**
	 * Larger y co-ord
	 */
	public long maxY;
	/**
	 * Smaller y co-ord
	 */
	public long minY;
	/**
	 * Smaller x co-ord
	 */
	public long minX;
	/**
	 * Larger x co-ord
	 */
	public long maxX;
}