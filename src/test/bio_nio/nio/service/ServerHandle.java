package bio_nio.nio.service;

import bio_nio.nio.CommonRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO服务端
 *
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class ServerHandle implements Runnable {
    private static Logger log = LoggerFactory.getLogger(ServerHandle.class);
    private Selector selector;
    private volatile boolean started = true;

    ServerHandle(Selector selector) {
        this.selector = selector;
    }


    @Override
    public void run() {
        //循环遍历selector
        while (started) {
            try {

                //阻塞,只有当至少一个注册的事件发生的时候才会继续.
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    // 删除已选的key,以防重复处理
                    it.remove();
                    handleInput(key);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        //selector关闭后会自动释放里面管理的资源
        close();
    }

    public void close() {
        if (selector != null)
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    private void handleInput(SelectionKey key) throws IOException, InterruptedException {
        //处理新接入的请求消息
        if (key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            log.info("请求Accept");
            //通过ServerSocketChannel的accept创建SocketChannel实例
            //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
            SocketChannel sc = ssc.accept();
            //设置为非阻塞的
            sc.configureBlocking(false);
            //注册为读
            sc.register(selector, SelectionKey.OP_READ);

        } else
            //读消息
            if (key.isReadable()) {
                String read = CommonRead.read(key);
                log.info("收到消息：" + read);
                Thread.sleep(1000 * 10);
                CommonRead.doWrite(key, "receive-------" + read);
            }
    }
}