/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 02.03.14
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class WorkThread extends Thread {
    private CompositeHashMap map;

    WorkThread(CompositeHashMap map) {
        this.map = map;
    }

    @Override
    public void run() {

        // put
        for (int i = 0; i < 2000; i++) {
            map.put(this.getId(), Integer.toString(i), 'v');
        }

        // remove
        for (int i = 1000; i < 2000; i++) {
            map.remove(this.getId(), Integer.toString(i));
        }
    }

}
