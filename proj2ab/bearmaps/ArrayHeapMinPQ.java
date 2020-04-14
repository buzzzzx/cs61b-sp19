package bearmaps;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> keys;
    private HashMap<T, Integer> itemIndexMap;
    private int size;

    private class PriorityNode {
        private T item;
        private double priority;

        PriorityNode(T t, double p) {
            item = t;
            priority = p;
        }

        public T getItem() {
            return item;
        }

        public double getPriority() {
            return priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((ArrayHeapMinPQ.PriorityNode) o).getItem().equals(getItem());
            }
        }

    }

    /**
     * Constructor.
     */
    public ArrayHeapMinPQ() {
        keys = new ArrayList<>();
        itemIndexMap = new HashMap<>();
        keys.add(new PriorityNode(null, 0.0));  // element in index 0 is always a sentinel.
        size = 0;
    }

    private int leftChild(int k) {
        return k * 2;
    }

    private int rightChild(int k) {
        return k * 2 + 1;
    }

    private int parent(int k) {
        return k / 2;
    }

    private boolean hasLeftChild(int k) {
        return (leftChild(k) <= size);
    }

    private boolean hasRightChild(int k) {
        return (rightChild(k) <= size);
    }

    private boolean hasParent(int k) {
        return k > 1;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("This item already existed.");
        }

        keys.add(new PriorityNode(item, priority));
        itemIndexMap.put(item, size + 1);
        size += 1;
        swim(size);
    }

    /**
     * Helper method that swim up the hierarchy to the right place.
     */
    private void swim(int k) {
        if (!hasParent(k)) {
            return;
        }

        if (smaller(k, parent(k))) {
            swap(parent(k), k);
            swim(parent(k));
        }
    }

    /**
     * Helper method that sink down the hierarchy, yielding to most qualified folk.
     * If two children have same priority, just choose left to replace.
     */
    private void sink(int k) {
        int qualified = k;

        if (hasLeftChild(k) && smaller(leftChild(k), qualified)) {
            qualified = leftChild(k);
        }

        if (hasRightChild(k) && smaller(rightChild(k), qualified)) {
            qualified = rightChild(k);
        }

        if (qualified != k) {
            swap(qualified, k);
            sink(qualified);
        }
    }

    /**
     * Helper method that swap two items.
     */
    private void swap(int m, int n) {

        PriorityNode tmp = keys.get(m);
        keys.set(m, keys.get(n));
        keys.set(n, tmp);

        itemIndexMap.put(keys.get(m).getItem(), m);
        itemIndexMap.put(keys.get(n).getItem(), n);

    }

    @Override
    public boolean contains(T item) {
        return itemIndexMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return keys.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T item = keys.get(1).getItem();
        keys.set(1, keys.get(size));
        keys.remove(size);
        size -= 1;
        itemIndexMap.remove(item);

        sink(1);
        return item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = itemIndexMap.get(item);
        double oldPriority = keys.get(index).getPriority();
        keys.get(index).setPriority(priority);

        if (priority > oldPriority) {
            sink(index);
        } else {
            swim(index);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return true if ith node has smaller priority than jth node.
     */
    private boolean smaller(int i, int j) {
        return keys.get(i).getPriority() < keys.get(j).getPriority();
    }
}
