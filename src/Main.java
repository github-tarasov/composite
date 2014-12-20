public class Main {

    public static void main(String[] args) {
        // Examples
        CompositeHashMap<User, String, String> map = new CompositeHashMap<User, String, String>();

        User Ivanov = new User("Ivanov");
        map.put(Ivanov , "param1", "value1");
        map.put(Ivanov, "param2", "value2");
        map.put(Ivanov, "param3", "value3");
        map.remove(Ivanov, "param3");

        System.out.println("Ivanov: " + map.getById(Ivanov).toString());
        System.out.println("Param1: " + map.getByName("param1").toString());
        System.out.println("Size: " + Integer.toString(map.size()));


    }
}
