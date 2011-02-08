package com.angelo.androidprova.core;

import dasher.utils.pool.Pool;
import dasher.utils.pool.Poolable;
import dasher.utils.pool.PoolableManager;
import dasher.utils.pool.Pools;

/**
 * Exception to be raised to signal that a given Node was not drawable.
 */
 public class NodeCannotBeDrawnException extends Exception implements Poolable<NodeCannotBeDrawnException>{
	
	private static final int POOL_LIMIT = 100000;
	private static final Pool<NodeCannotBeDrawnException> sPool2 = Pools.synchronizedPool(Pools
			.finitePool(new PoolableManager<NodeCannotBeDrawnException>() {
				public NodeCannotBeDrawnException newInstance() {
					return new NodeCannotBeDrawnException();
				}

				public void onAcquired(NodeCannotBeDrawnException element) {
				}

				public void onReleased(NodeCannotBeDrawnException element) {
				}
			}, POOL_LIMIT));

	private NodeCannotBeDrawnException mNext;

	public void setNextPoolable(NodeCannotBeDrawnException element) {
		mNext = element;
	}

	public NodeCannotBeDrawnException getNextPoolable() {
		return mNext;
	}

	public static NodeCannotBeDrawnException acquire() {
		return sPool2.acquire();
	}


	public void release() {
		sPool2.release(this);
	}

	/**
	 * Default constructor; creates the exception but does not store a Node.
	 * 
	 */
	private NodeCannotBeDrawnException() {
		 
	}
}