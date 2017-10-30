package bio_nio.nio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SysInfoAcquirerService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by liaowuhen on 2017/10/25.
 */
public class Server {
    private static Logger log = LoggerFactory.getLogger(ServerHandle.class);
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

            ServerHandle serverHandle = new ServerHandle(selector);


            new Thread(serverHandle, "Server").start();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}