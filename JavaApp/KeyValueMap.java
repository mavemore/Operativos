/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication18;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Administrador
 */
public class KeyValueMap<K, V> {
    private HashMap<K, V> map = new HashMap<K, V>();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock rl = rwl.readLock();
    private Lock wl = rwl.writeLock();

    public V get(Object k) {
        rl.lock();
        try {
            return map.get(k);
        } finally {
            rl.unlock();
        }
    }

    public V put(K k, V v) {
        wl.lock();
        try {
            return map.put(k, v);
        } finally {
            wl.unlock();
        }
    }
    
    public V remove(Object k) {
        wl.lock();
        try {
            return map.remove(k);
        } finally {
            wl.unlock();
        }
    }
    public Collection<V> values() {
        rl.lock();
        try {
            return map.values();
        } finally {
            rl.unlock();
        }
    }
    
}

