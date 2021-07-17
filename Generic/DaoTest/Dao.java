package Generic.DaoTest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
    定义泛型类Dao<T>
 */
public class Dao<T> {
    private Map<String, T> map = new HashMap<>();

    public void save(String id, T entity) {
        map.put(id, entity);
    }

    public T get(String id) throws Exception {
        if(map.containsKey(id)) {
            return map.get(id);
        } else {
            throw new Exception("id不存在！");
        }
    }

    public void update(String id, T entity) {
        map.put(id, entity);
    }

    public List<T> list() {
        List<T> mapValues = new LinkedList<>();
        for (Map.Entry<String, T> entry: map.entrySet()) {
            mapValues.add(entry.getValue());
        }
        return mapValues;
    }

    public void delete(String id) throws Exception {
        if (map.containsKey(id)) {
            map.remove(id);
        } else {
            throw new Exception("id不存在！");
        }
    }

    @Override
    public String toString() {
        return "Dao{" +
                "map=" + map +
                '}';
    }
}
