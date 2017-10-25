package synchronize.reOrdering;

/**
 * Created by liaowuhen on 2017/8/30.
 */
public class NotifyTest {

    int a = 0;
    boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread Run!");
        while (true) {
            NotifyTest test = new NotifyTest();
            NotifyThread notifyThread = test.new NotifyThread(test);
            WaitThread waitThread01 = test.new WaitThread(test);
            notifyThread.start();
            waitThread01.start();
        }

    }

    public void writer() {
        //System.out.println("writer");
        a = 2;                   //1
        flag = true;             //2
    }

    public void reader() {
        //System.out.println("reader");
        if (flag) {                //3
            int i = a * a;        //4
            if (i == 0) {
                System.out.println("i:" + i);
            }

        }
    }

    class NotifyThread extends Thread {
        private NotifyTest nt;

        public NotifyThread(NotifyTest nt) {
            this.nt = nt;
        }

        public void run() {
            nt.writer();
        }
    }

    class WaitThread extends Thread {
        private NotifyTest nt;

        public WaitThread(NotifyTest nt) {
            this.nt = nt;
        }

        public void run() {
            nt.reader();


        }
    }
}
