package bio_nio.nio.service;

import bio_nio.nio.CommonRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SysInfoAcquirerService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liaowuhen on 2017/10/25.
 */
public class Server {
    private static Logger log = LoggerFactory.getLogger(ServerHandle.class);
    private static ExecutorService executorService = Executors.newFixedThreadPool(20);
    private static int DEFAULT_PORT = 12345;
    private Selector selector;
    private ServerSocketChannel serverChannel;

    public static void main(String[] args) {
        SysInfoAcquirerService sas = new SysInfoAcquirerService(1000 * 10);
        sas.start();

        Server server = new Server();
        server.start();
    }

    public void start() {
        start(DEFAULT_PORT);
    }

    public synchronized void start(int port) {

        try {
            //创建选择器
            selector = Selector.open();
            // 获得一个ServerSocket通道
            serverChannel = ServerSocketChannel.open();
            //如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            serverChannel.configureBlocking(false);//开启非阻塞模式
            //绑定端口 backlog设为1024
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            //监听客户端连接请求
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            //标记服务器已开启
            log.info("服务器已启动，端口号：" + port);

            while (true) {
                int readyChannels = selector.select();
                //log.info("readyChannels {}",readyChannels);
                if (readyChannels == 0) {
                    log.info("continue");
                    continue;
                }
                if (!selector.isOpen()) {
                    System.out.println("selector is closed");
                    break;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    //log.info(key.hashCode()+"");
                    it.remove();

                    if (key.isValid()) {

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
                            sc.register(key.selector(), SelectionKey.OP_READ);

                        } else
                            //读消息
                            if (key.isReadable()) {
                               /* String read = CommonRead.read(key);
                                log.info("收到消息：" + read);
                                Thread.sleep(1000 * 10);
                                CommonRead.doWrite(key, "receive-------" + read);*/
                                String read = CommonRead.read(key);
                                log.info("收到消息：" + read);
                                ServerHandle serverHandle = new ServerHandle(read, key);
                                //CommonRead.doWrite(key, "receive-------" + read);
                                executorService.execute(serverHandle);
                            }
                    }
                    // 删除已选的key,以防重复处理

                }
            }


        } catch (IOException e) {
            log.error("", e);
            System.exit(1);
        }


    }
}