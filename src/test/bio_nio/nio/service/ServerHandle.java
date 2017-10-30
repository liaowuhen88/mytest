package bio_nio.nio.service;

import bio_nio.nio.CommonRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * NIO服务端
 *
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class ServerHandle implements Runnable {
    private static Logger log = LoggerFactory.getLogger(ServerHandle.class);
    private SelectionKey key;
    private String read;
    private volatile boolean started = true;

    ServerHandle(String read, SelectionKey key) {
        this.read = read;
        this.key = key;
    }


    @Override
    public void run() {
        //循环遍历selector
            try {
                //Thread.sleep(1000 * 10);
                CommonRead.doWrite(key, "receive-------" + read);
            } catch (Throwable t) {
                log.error("", t);
        }
        //selector关闭后会自动释放里面管理的资源
        //close(key);
    }

    public void close(SelectionKey key) {
        if (key.selector() != null)
            try {
                key.selector().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    private void handleInput(SelectionKey key) throws IOException, InterruptedException {

    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}