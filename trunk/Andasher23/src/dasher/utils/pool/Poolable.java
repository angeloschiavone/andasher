package dasher.utils.pool;


/**
 * @hide
 */
public interface Poolable<T> {
    void setNextPoolable(T element);
    T getNextPoolable();
}
