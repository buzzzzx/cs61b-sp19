import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {

    private Node<K, V> root;
    private int size;

    private class Node<K, V> {
        private Node<K, V> left;
        private Node<K, V> right;
        private K key;
        private V value;

        Node(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * helper method that get value for specified key recursively.
     */
    private V get(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = node.key.compareTo(key);
        if (cmp < 0) {
            return get(node.right, key);
        } else if (cmp > 0) {
            return get(node.left, key);
        } else {
            return node.value;
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Can't get value fo null");
        }
        return get(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * helper method that insert a new mapping, do nothing if key is already in the map.
     */
    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) {
            return new Node<K, V>(key, value);
        }
        int cmp = node.key.compareTo(key);
        if (cmp < 0) {
            node.right = put(node.right, key, value);
        } else if (cmp > 0) {
            node.left = put(node.left, key, value);
        }
        return node;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Can't get value fo null");
        }
        size += 1;
        root = put(root, key, value);
    }

    /**
     * helper method that returns a set of keys contain in this map.
     */
    private void keySet(Set<K> keys, Node<K, V> node) {
        if (node != null) {
            keySet(keys, node.left);
            keys.add(node.key);
            keySet(keys, node.right);
        } else {
            return;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySet(keys, root);
        return keys;
    }

    private Node<K, V> minRight(Node<K, V> node) {
        if (node.left == null) {
            return node;
        } else {
            return minRight(node.left);
        }
    }

    private Node<K, V> deleteMinRight(Node<K, V> node) {
        if (node.left == null) {
            return node.right;
        } else {
            node.left = deleteMinRight(node.left);
        }
        return node;
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = node.key.compareTo(key);
        if (cmp < 0) {
            node.right = remove(node.right, key);
        } else if (cmp > 0) {
            node.left = remove(node.left, key);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left != null && node.right == null) {
                return node.left;
            } else if (node.left == null && node.right != null) {
                return node.right;
            } else {
                Node<K, V> min = minRight(node.right);
                min.right = deleteMinRight(node.right);
                min.left = node.left;
                return min;
            }
        }
        return node;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Can't delete null.");
        }
        V res = get(key);
        if (res != null) {
            root = remove(root, key);
            size -= 1;
        }
        return res;
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Can't delete null.");
        }
        if (!get(key).equals(value)) {
            return null;
        } else {
            root = remove(root, key);
            size -= 1;
        }
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    /**
     * helper method that print out BST map in order of increasing key.
     */
    private void printIncreasingly(Node<K, V> node) {
        if (node != null) {
            printIncreasingly(node.left);
            System.out.println(node.key);
            printIncreasingly(node.right);
        } else {
            return;
        }
    }

    /**
     * prints out BST map in order of increasing key.
     */
    public void printInOrder() {
        printIncreasingly(root);
    }
}
