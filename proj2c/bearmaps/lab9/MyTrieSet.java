package bearmaps.lab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {

    private static class Node {
        private boolean isKey;
        private HashMap<Character, Node> next;

        Node(boolean isKey) {
            this.isKey = isKey;
            this.next = new HashMap<>();
        }

    }

    private Node root;
    private int size;

    /**
     * Contructor.
     */
    public MyTrieSet() {
        root = new Node(false);
        size = 0;
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root.next = new HashMap<>();
        size = 0;
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        Node node = containHelp(root, key, 0);
        if (node == null || !node.isKey) {
            return false;
        }
        return true;
    }

    /**
     * Helper method that return the node which map to the last character of given string.
     * Return null if don't find it.
     */
    private Node containHelp(Node node, String s, int index) {
        if (node.next.size() == 0) {
            return null;
        }

        Node next = node.next.get(s.charAt(index));
        if (next != null) {
            if (index == s.length() - 1) {
                return next;
            } else {
                return containHelp(next, s, index + 1);
            }
        } else {
            return null;
        }
    }

    /**
     * Helper method that collect all the keys in a trie to the list.
     */
    private void colHelp(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }

        for (char c : n.next.keySet()) {
            colHelp(s + c, x, n.next.get(c));
        }
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        addHelp(root, key, 0);
    }

    /**
     * Helper method that add a string to the trie recursively.
     * If the string already exists, make isKey of last node of the string true.
     */
    private void addHelp(Node node, String key, int index) {
        if (index == key.length()) {
            node.isKey = true;
            return;
        }
        if (!node.next.containsKey(key.charAt(index))) {
            boolean isKey = false;
            if (index == key.length() - 1) {
                isKey = true;
            }
            node.next.put(key.charAt(index), new Node(isKey));
        }
        addHelp(node.next.get(key.charAt(index)), key, index + 1);
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> res = new ArrayList<>();
        Node startNode = containHelp(root, prefix, 0);
        if (startNode != null) {
            colHelp(prefix, res, startNode);
        }
        return res;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        StringBuilder res = new StringBuilder();

        int i;
        Node curr = root;
        for (i = 0; i < key.length(); i += 1) {
            char c = key.charAt(i);
            curr = curr.next.get(c);
            if (curr != null) {
                res.append(c);
            } else {
                break;
            }
        }

        return res.toString();
    }

}
