import java.util.HashMap;

public class MyCache<K,V> implements ICache<K,V>{
    private final HashMap<K,V> data = new HashMap<>();

    @Override
    public synchronized  V get(K key) {
        return data.get(key);
    }

    @Override
    public synchronized void put(K key, V value) {
        data.put(key,value);
    }

}
