package es.datastructur.synthesizer;

public interface BoundedQueue<T> extends Iterable<T> {
    /**
     * returns size of the buffer.
     */
    int capacity();

    /**
     * returns number of items currently in the buffer.
     */
    int fillCount();

    /**
     * adds item to the end.
     */
    void enqueue(T x);

    /**
     * deletes and return item from the front.
     */
    T dequeue();

    /**
     * returns (but do not delete) item from the front.
     */
    T peek();

    /**
     * returns true if the buffer empty(fillCount equals zero).
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * returns true if the buffer full(fillCount equals capacity).
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
