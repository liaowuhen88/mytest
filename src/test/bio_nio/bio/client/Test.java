package bio_nio.bio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试方法
 *
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class Test {
    private static Logger log = LoggerFactory.getLogger(Test.class);

    //测试主方法
    public static void main(String[] args) throws InterruptedException {
        //运行客户端
        for (int count = 0; count < 1000; count++) {
            Deal deal = new Deal(count);
            new Thread(deal).start();
        }
    }
}
