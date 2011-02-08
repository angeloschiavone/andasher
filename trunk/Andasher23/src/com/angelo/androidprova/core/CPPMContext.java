package com.angelo.androidprova.core;



import java.io.Serializable;

import android.util.Log;


import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;

	/**
 * Class representing a context which contains information
 * relevant to the PPM model.
 */
public class CPPMContext extends CContextBase implements Serializable  , Poolable<CPPMContext>  {
 
	private static final int POOL_LIMIT = 10;
	private static final Pool<CPPMContext> sPool2 = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<CPPMContext>() {
				public CPPMContext newInstance() {
					return new CPPMContext();
				}
				
				public void onAcquired(CPPMContext element) {
				}

				public void onReleased(CPPMContext element) {
				}
			}, POOL_LIMIT));

	private CPPMContext mNext;
	
	public void setNextPoolable(CPPMContext element) {
		mNext = element;
	}

	public CPPMContext getNextPoolable() {
		return mNext;
	}

	public static CPPMContext acquire() {
 
		return sPool2.acquire();
	}
	
	public void release() {
		this.head = null;
		this.order = 0;
		sPool2.release(this);
		//Log.e("CPPMContext ","release");
	}
 
	public CPPMnode head;
	public int order;
	
	private CPPMContext() {
		this.head = null;
		this.order = 0;
		//Log.e("CPPMContext ","(acquired-released) "+acquired+" "+released +" diff:"+ (acquired-released));
		//new Exception().printStackTrace();
		//Log.e("CPPMContext ","contructor");
	};

 
}