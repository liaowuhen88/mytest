package bio_nio.nio.client;

import bio_nio.nio.CommonRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO客户端
 *
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class ClientHandle implements Runnable {
    final static char operators[] = {'+', '-', '*', '/'};
    private static Integer i = 0;
    private static Logger log = LoggerFactory.getLogger(Client.class);
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean started;
    private String msg;

    public ClientHandle(String msg, String ip, int port) {
        this.host = ip;
        this.port = port;
        this.msg = msg;
    }

    public void stop() {
        started = false;
    }

    public void init() {
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            socketChannel = SocketChannel.open();
            //如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            socketChannel.configureBlocking(false);//开启非阻塞模式
            started = true;

            socketChannel.connect(new InetSocketAddress(host, port));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        init();
        //循环遍历selector
        while (started) {
            try {
                //阻塞,只有当至少一个注册的事件发生的时候才会继续.
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        //selector关闭后会自动释放里面管理的资源
        if (selector != null)
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void handleInput(SelectionKey key) throws IOException {

        if (key.isConnectable()) {
            SocketChannel channel = (SocketChannel) key
                    .channel();
            // 如果正在连接，则完成连接
            if (channel.isConnectionPending()) {
                channel.finishConnect();

            }
            // 设置成非阻塞
            channel.configureBlocking(false);

            //在这里可以给服务端发送信息哦
            CommonRead.doWrite(key, this.getMsg());
            //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
            channel.register(this.selector, SelectionKey.OP_READ);

            // 获得了可读的事件
        } else if (key.isReadable()) {
            String read = CommonRead.read(key);
            log.info("read:" + read);
        }

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
