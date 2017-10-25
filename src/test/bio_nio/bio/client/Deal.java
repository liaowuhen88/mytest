package bio_nio.bio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liaowuhen on 2017/10/25.
 */
public class Deal implements Runnable {
    private static Logger log = LoggerFactory.getLogger(Deal.class);


    @SuppressWarnings("static-access")
    @Override
    public void run() {
        //随机产生算术表达式
        try {

            Client.send();
               /* Integer sl = random.nextInt(100);
                Thread.currentThread().sleep(sl);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
