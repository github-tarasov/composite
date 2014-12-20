
import org.junit.Test;
import org.junit.Ignore;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CompositeHashMapTest {

    @Test
    public void common() {
        CompositeHashMap<User, String, String> map = new CompositeHashMap<User, String, String>();
        User Ivanov = new User("Ivanov");

        // first put
        map.put(Ivanov, "param1", "value1");
        assertTrue(
                "Bad size after put",
                map.size() == 1
        );
        assertEquals(
                "Bad put",
                "value1",
                map.get(Ivanov, "param1")
        );

        // second put
        map.put(Ivanov, "param2", "value2");
        assertTrue(
                "Bad size after put",
                map.size() == 2
        );
        assertEquals(
                "Bad put",
                "value1",
                map.get(Ivanov, "param1")
        );
        assertEquals(
                "Bad put",
                "value2",
                map.get(Ivanov, "param2")
        );

        // put with rewrite, use new object User
        map.put(new User("Ivanov") , "param2", "value2.new");
        assertTrue(
                "Bad size after rewrite put",
                map.size() == 2
        );
        assertEquals(
                "Bad put with after rewrite",
                "value2.new",
                map.get(Ivanov, "param2")
        );

        // getById
        HashMap<String, String> IvanovParams = map.getById(Ivanov);
        assertTrue(
                "Bad size getById",
                IvanovParams.size() == 2
        );
        assertEquals(
                "Bad getById HashMap",
                "value1",
                IvanovParams.get("param1")
        );
        assertEquals(
                "Bad getById HashMap",
                "value2.new",
                IvanovParams.get("param2")
        );

        // getByName
        HashMap<User, String> param1 = map.getByName("param1");
        assertTrue(
                "Bad size getByName",
                param1.size() == 1
        );
        assertEquals(
                "Bad getByName HashMap",
                "value1",
                param1.get(Ivanov)
        );

        // get
        assertEquals(
                "Bad get value",
                "value1",
                map.get(new User("Ivanov"), "param1")
        );

        // containsKeysById
        assertTrue(
                "Bad containsKeysById",
                map.containsKeysById(Ivanov)
        );
        assertFalse(
                "Bad containsKeysById",
                map.containsKeysById(new User("Petrov"))
        );

        // containsKeysByName
        assertTrue(
                "Bad containsKeysByName",
                map.containsKeysByName("param1")
        );
        assertFalse(
                "Bad containsKeysByName",
                map.containsKeysByName("param3")
        );

        // containsKey
        assertTrue(
                "Bad containsKey",
                map.containsKey(Ivanov, "param1")
        );
        assertFalse(
                "Bad containsKey",
                map.containsKey(Ivanov, "param3")
        );

        // containsValue
        assertTrue(
                "Bad containsValue",
                map.containsValue("value1")
        );
        assertFalse(
                "Bad containsValue",
                map.containsValue("value3")
        );

        // clear
        map.clear();
        assertTrue(
                "Bad cleared size",
                map.size() == 0
        );
        assertFalse(
                "Not empty",
                map.containsKeysById(Ivanov)
        );
    }

    @Test
    public void threadSafety() {
        CompositeHashMap<Integer, String, String> map = new CompositeHashMap<Integer, String, String>();

        WorkThread thread1 = new WorkThread(map);
        WorkThread thread2 = new WorkThread(map);
        WorkThread thread3 = new WorkThread(map);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(
                "Bad size",
                map.size() == 3000
        );
        for (int i = 0; i < 1000; i++) {
            assertTrue (
                    "Bad structure",
                    map.getByName(Integer.toString(i)).size() == 3
            );
        }
    }

}
