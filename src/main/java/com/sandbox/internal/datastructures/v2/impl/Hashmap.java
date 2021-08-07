package com.sandbox.internal.datastructures.v2.impl;

import com.sandbox.internal.datastructures.v2.Map;

import java.util.LinkedList;

public class Hashmap<K, V> implements Map<K, V> {

    private LinkedList<Node<K, V>>[] data = new LinkedList[10];
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;
        LinkedList<Node<K, V>> list = data[getIndex(key)];
        if (list == null) return null;
        if (!list.isEmpty()) {
            for (Node<K, V> o : list) {
                if (o.k.equals(key)) {
                    return o.v;
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        LinkedList<Node<K, V>> list = data[getIndex(key)];
        if (list == null) {
            list = new LinkedList<>();
            list.add(new Node<>(key, value));
            data[getIndex(key)] = list;
        } else {
            list.add(new Node<>(key, value));
        }
        size++;
        return get(key);
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    /**
     * Compress function
     * @param key
     * @return
     */
    private int getIndex(Object key) {
        if (data.length == 0) return 0;
        return key.hashCode() % data.length;
    }

    static class Node<K, V> {
        final K k;
        final V v;

        Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }
}
