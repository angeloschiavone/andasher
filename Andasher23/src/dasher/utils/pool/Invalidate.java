package dasher.utils.pool;

import android.util.Log;

public class Invalidate {

	public static void main(String args[]) {
		InvalidateInfo iff = InvalidateInfo.acquire();
		iff.getNextPoolable();
	}

	static public class InvalidateInfo implements Poolable<InvalidateInfo> {
		public InvalidateInfo()
		{
			super();
		}
		
		private static final int POOL_LIMIT = 10;
		private static final Pool<InvalidateInfo> sPool = Pools
				.synchronizedPool(Pools.finitePool(
						new PoolableManager<InvalidateInfo>() {
							public InvalidateInfo newInstance() {
								return new InvalidateInfo();
							}

							public void onAcquired(InvalidateInfo element) {
							}
							
							public void onReleased(InvalidateInfo element) {
							}
						}, POOL_LIMIT));

		private InvalidateInfo mNext;

		public void setNextPoolable(InvalidateInfo element) {
			mNext = element;
		}

		public InvalidateInfo getNextPoolable() {
			return mNext;
		}

		public static InvalidateInfo acquire() {
			return sPool.acquire();
		}

		public void release() {
			sPool.release(this);
		}
		
		 public int value=-1;
		
	}
}
