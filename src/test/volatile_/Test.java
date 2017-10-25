package volatile_;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liaowuhen on 2017/10/20.
 */
public class Test {
    private static Logger log = LoggerFactory.getLogger(Test.class);
    public volatile int inc = 0;

    public static void main(String[] args) {
        final Test test = new Test();
        System.out.println("Thread.activeCount()" + Thread.activeCount());
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++)
                        test.increase();
                }
            }.start();
        }

        while (Thread.activeCount() > 2) {
            System.out.println("Thread.activeCount()" + Thread.activeCount());
            Thread.yield();
        } //保证前面的线程都执行完
        System.out.println(test.inc);

    }

    public void increase() {
        inc = inc + 1;
        //inc++;
    }
}
