package multithreading;

/**
 * Created by liaowuhen on 2017/6/7.
 */
public class DeleteDispather implements Runnable{
    private Main main ;

    public DeleteDispather(Main main){
        this.main = main;
    }
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("DeleteDispather");
            main.deleteCustomer("a");
        }

    }
}
