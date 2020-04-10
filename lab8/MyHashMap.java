import javax.xml.ws.EndpointReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    /* instance variables. */
    private Set<K> keys;
    private Entry<K, V>[] buckets;
    private int bucketsSize;
    private double loadFactor;
    private int size;

    /* default variables. */
    static final double DEFAULT_LOAD_FACTOR = 0.75;
    static final int DEFAULT_INITIAL_SIZE = 16;
    static final int MULTIPLY_FACTOR = 2;

    private static class Entry<K, V> {
        private final K key;
        private V val;
        public Entry<K, V> next;

        Entry(K k, V v, Entry<K, V> r) {
            key = k;
            val = v;
            next = r;
        }

        public K getKey() {
            return key;
        }

        public Entry<K, V> next() {
            return next;
        }

        public V getVal() {
            return val;
        }

        public void setVal(V val) {
            this.val = val;
        }
    }

    /**
     * Constructor that initial loadFactor and initialSize with default value.
     */
    public MyHashMap() {
        this(DEFAULT_INITIAL_SIZE, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructor that initial initialSize with value of given initialSize.
     * And loadFactor with default value.
     */
    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructor that initial loadFactor and initialSize with given parameters.
     */
    public MyHashMap(int initialSize, double loadFactor) {
        this.bucketsSize = initialSize;
        this.loadFactor = loadFactor;

        keys = new HashSet<>();
        buckets = new Entry[initialSize];
        size = 0;
    }

    /**
     * Return insert index to buckets.
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % bucketsSize;
    }

    /**
     * Ensure input key is not null.
     */
    private void validate(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null.");
        }
    }

    /**
     * Resize buckets size and redistribute all items.
     * Complexity is O(N). But seldom do resizing.
     * Actually most operation is just put without resizing, complexity is O(1).
     * So averagely is O(1).
     */
    private void resize(int capacity) {

        Entry<K, V>[] newBuckets = new Entry[capacity];

        // redistribute all items.
        for (K key : keys) {
            int index = (key.hashCode() & 0x7fffffff) % capacity;
            newBuckets[index] = putHelper(newBuckets[index], key, get(key));
        }

        buckets = newBuckets;
        bucketsSize = capacity;
    }

    @Override
    public void clear() {
        int i;
        for (i = 0; i < bucketsSize; i += 1) {
            buckets[i] = null;
        }

        // clear keys;
        keys.clear();

        // size
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        validate(key);
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        validate(key);
        int index = hash(key);

        Entry<K, V> bucket = buckets[index];

        return get(bucket, key);
    }

    /**
     * Helper method that return value from give key.
     */
    private V get(Entry<K, V> entry, K key) {
        if (entry == null) {
            return null;
        }
        if (entry.getKey().equals(key)) {
            return entry.getVal();
        }
        return get(entry.next(), key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        validate(key);

        double ratio = (double) size / bucketsSize;
        if (ratio > loadFactor) {
            resize(bucketsSize * MULTIPLY_FACTOR);
        }

        if (!containsKey(key)) {
            keys.add(key);
            size += 1;
        }
        int index = hash(key);
        buckets[index] = putHelper(buckets[index], key, value);
    }

    /**
     * Helper method that add a new mapping.
     * If key is existed, then update value.
     */
    private Entry<K, V> putHelper(Entry<K, V> entry, K key, V val) {
        if (entry == null) {
            return new Entry<>(key, val, null);
        }
        if (key.equals(entry.getKey())) {
            entry.setVal(val);
        } else {
            entry.next = putHelper(entry.next(), key, val);
        }

        return entry;
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        validate(key);

        if (containsKey(key)) {
            V res = get(key);
            int index = hash(key);
            buckets[index] = removeHelper(buckets[index], key);
            keys.remove(key);
            size -= 1;

            return res;
        } else {
            return null;
        }
    }

    /**
     * Helper method that remove the mapping of given key.
     */
    private Entry<K, V> removeHelper(Entry<K, V> entry, K key) {
        if (entry == null) {
            return null;
        }
        if (key.equals(entry.getKey())) {
            return entry.next();
        } else {
            entry.next = removeHelper(entry.next(), key);
        }

        return entry;
    }

    @Override
    public V remove(K key, V value) {
        validate(key);

        if (containsKey(key) && value.equals(get(key))) {
            int index = hash(key);
            buckets[index] = removeHelper(buckets[index], key);
            keys.remove(key);
            size -= 1;

            return value;
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
