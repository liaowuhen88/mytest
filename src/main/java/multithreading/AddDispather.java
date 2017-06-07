package multithreading;

/**
 * Created by liaowuhen on 2017/6/7.
 */
public class AddDispather implements Runnable{
    private Main main ;

    public AddDispather(Main main){
        this.main = main;
    }
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("AddDispather");
            main.addCustomer("a");
        }

    }
}
