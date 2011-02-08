package com.angelo.androidprova.core;

import android.util.Log;



import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;
/*
 * Class representing a point in Dasher space
 */
public class Point  implements Poolable<Point>{
	
	private static final int POOL_LIMIT = 100000;
	private static final Pool<Point> sPool2 = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<Point>() {
				public Point newInstance() {
					return new Point();
				}

				public void onAcquired(Point element) {
				}

				public void onReleased(Point element) {
				}
			}, POOL_LIMIT));

	private Point mNext;

	public void setNextPoolable(Point element) {
		mNext = element;
	}

	public Point getNextPoolable() {
		return mNext;
	}

	public static Point acquire() {
		return sPool2.acquire();
	}


	public void release() {
		sPool2.release(this);
	}
	
	
	private Point()
	{
		
	//	Log.e("Point","constructor ");
	     //new Exception().printStackTrace();
	}
	
	/**
	 * X co-ord
	 */
	public int x=0;
	/**
	 * Y co-ord
	 */
	public int y=0;
}