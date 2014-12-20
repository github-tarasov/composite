/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 02.03.14
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */

import java.util.HashMap;
import java.util.Map;

public class CompositeHashMap<TId, TName, TValue> {

    // Use 2 HashMap for quick get by id or name
    private HashMap<TId, HashMap<TName, TValue>> dictById;
    private HashMap<TName, HashMap<TId, TValue>> dictByName;

    private int size;

    public CompositeHashMap() {
        dictById = new HashMap<TId, HashMap<TName, TValue>>();
        dictByName = new HashMap<TName, HashMap<TId, TValue>>();
    }

    public HashMap getById(TId id) {
        if (dictById.containsKey(id)) {
            return dictById.get(id);
        } else {
            return null;
        }
    }

    public HashMap getByName(TName name) {
        if (dictByName.containsKey(name)) {
            return dictByName.get(name);
        } else {
            return null;
        }
    }

    public TValue get(TId id, TName name) {
        if (dictById.containsKey(id) && dictById.get(id).containsKey(name)) {
            return dictById.get(id).get(name);
        } else {
            return null;
        }
    }

    // TODO: if rewrite return old value or null else
    public synchronized void put(TId id, TName name, TValue value)
    {
        boolean isIncremented = true;

        if (dictById.containsKey(id)) {
            if (isIncremented && dictById.get(id).containsKey(name)) {
                isIncremented = false; // will be overwritten
            }
            dictById.get(id).put(name, value); // add or rewrite
        } else {
            HashMap<TName, TValue> dictName = new HashMap<TName, TValue>();
            dictName.put(name, value);
            dictById.put(id, dictName);
        }

        if (dictByName.containsKey(name)) {
            if (isIncremented && dictByName.get(name).containsKey(id)) {
                isIncremented = false; // will be overwritten
            }
            dictByName.get(name).put(id, value); // add or rewrite
        } else {
            HashMap<TId, TValue> dictId = new HashMap<TId, TValue>();
            dictId.put(id, value);
            dictByName.put(name, dictId);
        }

        if (isIncremented) {
            size++;
        }
    }

    // TODO: public synchronized void removeById(TId id)

    // TODO: public synchronized void removeByName(TName name)

    // TODO: return deleted value
    public synchronized void remove(TId id, TName name)
    {
        boolean isDecreases = false;

        if (dictById.containsKey(id) && dictById.get(id).containsKey(name)) {
            dictById.get(id).remove(name);
            isDecreases = true;
        }
        if (dictByName.containsKey(name) && dictByName.get(name).containsKey(id)) {
            dictByName.get(name).remove(id);
            isDecreases = true;
        }

        if (isDecreases) {
            size--;
        }
    }

    public int size() {
        return size;
    }

    public boolean containsKeysById(TId id) {
        return dictById.containsKey(id);
    }

    public boolean containsKeysByName(TName name) {
        return dictByName.containsKey(name);
    }

    public boolean containsKey(TId id, TName name) {
        return (dictById.containsKey(id) && dictById.get(id).containsKey(name));
    }

    public boolean containsValue(TValue value) {
        for (Map.Entry<TId, HashMap<TName, TValue>> entry : dictById.entrySet()) {
            if (entry.getValue().containsValue(value))
            {
                return true;
            }
        }
        return false;
    }

    public synchronized void clear() {
        dictById.clear();
        dictByName.clear();
        size = 0;
    }
}
