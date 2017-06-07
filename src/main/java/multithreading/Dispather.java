package multithreading;

/**
 * Created by liaowuhen on 2017/6/7.
 */
public class Dispather implements Runnable{
    private Main main ;

    public Dispather(Main main){
        this.main = main;
    }
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Dispather");
            main.getDispatcher();
        }

    }
}
