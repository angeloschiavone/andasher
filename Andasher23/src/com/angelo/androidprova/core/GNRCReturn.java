package com.angelo.androidprova.core;


import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;
/**
 * Simple struct for returning min/max pairs.
 */
public class GNRCReturn implements Poolable<GNRCReturn> {
	private static final int POOL_LIMIT = 100000;
	private static final Pool<GNRCReturn> sPool2 = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<GNRCReturn>() {
				public GNRCReturn newInstance() {
					return new GNRCReturn();
				}

				public void onAcquired(GNRCReturn element) {
				}

				public void onReleased(GNRCReturn element) {
				}
			}, POOL_LIMIT));

	private GNRCReturn mNext;

	public void setNextPoolable(GNRCReturn element) {
		mNext = element;
	}

	public GNRCReturn getNextPoolable() {
		return mNext;
	}

	public static GNRCReturn acquire() {
		return sPool2.acquire();
	}


	public void release() {
		sPool2.release(this);
	}
	
	private GNRCReturn()
	{
		
	}
	
    /**
     * New max
     */
    long iNewMin;
    /**
     * New min
     */
    long iNewMax;
}