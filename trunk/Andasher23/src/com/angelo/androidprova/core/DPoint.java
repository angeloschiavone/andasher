package com.angelo.androidprova.core;

import android.util.Log;



import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;
/*
 * Class representing a point in Dasher space
 */
public class DPoint  implements Poolable<DPoint>{
	
	private static final int POOL_LIMIT = 100000;
	private static final Pool<DPoint> sPool2 = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<DPoint>() {
				public DPoint newInstance() {
					return new DPoint();
				}

				public void onAcquired(DPoint element) {
				}

				public void onReleased(DPoint element) {
				}
			}, POOL_LIMIT));

	private DPoint mNext;

	public void setNextPoolable(DPoint element) {
		mNext = element;
	}

	public DPoint getNextPoolable() {
		return mNext;
	}

	public static DPoint acquire() {
		return sPool2.acquire();
	}


	public void release() {
		sPool2.release(this);
	}
	
	
	private DPoint()
	{
		
		// Log.e("DPoint","constructor ");
	     //new Exception().printStackTrace();
	}
	
	/**
	 * X co-ord
	 */
	public long x;
	/**
	 * Y co-ord
	 */
	public long y;
}