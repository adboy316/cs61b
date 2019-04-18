

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<Key, Value> implements Map61B<Key, Value> {

    private static final int DEFAULT_INITIAL_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private int n;  // number of key value pairs
    private int m;
    private double loadFactor;

    private ULLMap<Key, Value>[] st;


    public MyHashMap() {
        this(DEFAULT_INITIAL_SIZE);

    }

    public MyHashMap(int initialSize) {
        this.m = initialSize;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        st = new ULLMap[m];
        for (int i = 0; i < m; i++) {
            st[i] = new ULLMap<Key, Value>();
        }

        this.n = 0;
    }
;
    public MyHashMap(int initialSize, double loadFactor){
        this. m = initialSize;
        this.loadFactor = loadFactor;
        st = new ULLMap[m];
        for (int i = 0; i < m; i++) {
            st[i] = new ULLMap<Key, Value>();
        }

        this.n = 0;
    }

    // hash value between 0 and m-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        for (ULLMap kv: st){
            while (kv.iterator().hasNext()){
                kv.clear();
            }
        }

        n = 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to containsKey() is null");
        int i = hash(key);
        return st[i].containsKey(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() is null");
        int i = hash(key);
        return st[i].get(key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return this.n;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("First argument to put() is null.");
        int i = hash(key);
        if (value == null) {
            st[i].remove(key);
            return;
        }
        if (n >= loadFactor*m) resize(2*m);
        if (!st[i].containsKey(key)) n += 1;
        st[i].put(key, value);
    }

    public void resize(int n) {
//        MyHashMap<Key, Value> temp = new MyHashMap(n);
//
//        for (int i = 0; i < m; i++) {
//            for (Key key : st[i].keySet()) {
//                temp.put(key, st[i].get(key));
//            }
//        }
//
//        this.m  = temp.m;
//        this.n  = temp.n;
//        this.st = temp.st;
    }



    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set keySet() {
        Set<Key> set = new HashSet<>();
        for (ULLMap kv: st){
            for (Object key: kv){
                set.add((Key)key);
            }
        }
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     *
     * @param key
     * @param value
     */
    @Override
    public Object remove(Object key, Object value) {
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return null;
    }




}
