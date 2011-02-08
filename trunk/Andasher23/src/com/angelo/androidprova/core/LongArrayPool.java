package com.angelo.androidprova.core;

import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;

public class LongArrayPool implements Poolable<LongArrayPool> {

	public long[] array;// = new long[30];
	 
	private LongArrayPool()
	{
		array = new long[64];
		for (int i = 0; i < 64; i++) {
			array[i]=0;
		}
	} 
	

	
	private static final int POOL_LIMIT = 100000;
	private static final Pool<LongArrayPool> sPool = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<LongArrayPool>() {
				public LongArrayPool newInstance() {
					return new LongArrayPool();
				}

				public void onAcquired(LongArrayPool element) {
				}

				public void onReleased(LongArrayPool element) {
				}
			}, POOL_LIMIT));

	private LongArrayPool mNext;

	public void setNextPoolable(LongArrayPool element) {
		mNext = element;
	}

	public LongArrayPool getNextPoolable() {
		return mNext;
	}

	public static LongArrayPool acquire() {
		return sPool.acquire();
	}


	public void release() {
		for (int i = 0; i < 64; i++) {
			array[i]=0;
		}
		sPool.release(this);
	}

}
