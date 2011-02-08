package com.angelo.androidprova.core;

import android.util.Log;
import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;

public class DasherNodeArrayPool implements Poolable<DasherNodeArrayPool> {

	public CDasherNode[] array;// = new long[30];
	public int size = 30;

	private DasherNodeArrayPool() {
		array = new CDasherNode[30];
		/*
		 * for (int i = 0; i < 30; i++) { array[i]= new CDasherNode(); }
		 */
	}

	private static final int POOL_LIMIT = 100000;
	private static final Pool<DasherNodeArrayPool> sPool = Pools
			.synchronizedPool(Pools.finitePool(
					new PoolableManager<DasherNodeArrayPool>() {
						public DasherNodeArrayPool newInstance() {
							return new DasherNodeArrayPool();
						}

						public void onAcquired(DasherNodeArrayPool element) {
						}

						public void onReleased(DasherNodeArrayPool element) {
						}
					}, POOL_LIMIT));

	private DasherNodeArrayPool mNext;

	public void setNextPoolable(DasherNodeArrayPool element) {
		mNext = element;
	}

	public DasherNodeArrayPool getNextPoolable() {
		return mNext;
	}

	static int count = 0;

	public static DasherNodeArrayPool acquire() {
		count++;

		
		DasherNodeArrayPool ret = sPool.acquire();
		
		ret.size = 30;
		return ret;
	}

	public void release() {
		size = 0;
		/*
		 * for (int i = 0; i < 30; i++) { array[i]=null; }
		 */
		sPool.release(this);
	}

}
