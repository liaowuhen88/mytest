package volatile_;

/**
 * Created by liaowuhen on 2017/10/20.
 */
public class TestSynchronized {
    public int inc = 0;

    public static void main(String[] args) {
        final TestSynchronized test = new TestSynchronized();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                }

                ;
            }.start();
        }

        while (Thread.activeCount() > 2)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }

    public synchronized void increase() {
        inc++;
    }
}
