import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    private Node root; // Root of BST

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BSTMap () {

    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        root = clear(root);
    }

    private Node clear(Node x) {
        if (x == null) return x;
        x.val = null;
        x.right = clear(x.right);
        x.left = clear(x.left);
        x.size = 0;
        return x;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("calls containsKey() with null key");
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get (Node x, K key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;

    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root =  put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) return new Node (key, value, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left =  put(x.left, key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.val = value;
        x.size = x.size +1;
        return x;

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }






}
