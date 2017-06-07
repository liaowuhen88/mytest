package multithreading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liaowuhen on 2017/6/7.
 */
public class Main {
    private static final List<String> cids = Collections.synchronizedList(new ArrayList());

    private static AtomicInteger count = new AtomicInteger();
    private static Integer countInt =0;

    public static void main(String[] args) {
        Main main = new Main();
        cids.add("a");

        Dispather dispather = new Dispather(main);
        DeleteDispather deleteDispather = new DeleteDispather(main);
        AddDispather addDispather = new AddDispather(main);

        Thread thread = new Thread(dispather);
        Thread thread1 = new Thread(dispather);
        Thread thread2 = new Thread(dispather);

        Thread thread3 = new Thread(deleteDispather);
        Thread thread4 = new Thread(addDispather);

        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }


    public void getDispatcher() {
        synchronized (cids) {
            if (cids.size() > 0) {
                System.out.println(count.get());
                count.getAndIncrement();
                System.out.println(count.get());
                while (count.get() >= cids.size()) {
                    System.out.println("before"+count.get()+"-----"+cids.size());
                    count.getAndAdd(-cids.size());
                    System.out.println("after"+count.get()+"-----"+cids.size());
                }
                System.out.println(cids.get(count.get()));

            }else {
                System.out.println(cids.size());
            }
        }
    }

    public void  deleteCustomer(String cJid) {
        synchronized (cids) {
            cids.remove(cJid);
        }
    }

    public void  addCustomer(String cJid) {
        synchronized (cids) {
            cids.add(cJid);
        }
    }

    public void getDispatcherInt() {
        synchronized (cids) {
            if (cids.size() > 0) {
                countInt++;
                if (countInt >= cids.size()) {
                    countInt = countInt-cids.size();
                    System.out.println(cids.get(countInt));
                }
            }else {
                System.out.println(cids.size());
            }
        }

    }

}
