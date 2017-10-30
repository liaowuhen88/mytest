package bio_nio.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by liaowuhen on 2017/10/30.
 */
public class CommonRead {
    private static Logger log = LoggerFactory.getLogger(CommonRead.class);

    public static String read(SelectionKey key) throws IOException {

        String result = null;

        SocketChannel sc = (SocketChannel) key.channel();
        //创建ByteBuffer，并开辟一个1M的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取请求码流，返回读取到的字节数
        int readBytes = sc.read(buffer);
        //读取到字节，对字节进行编解码
        if (readBytes > 0) {
            //将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
            buffer.flip();
            //根据缓冲区可读字节数创建字节数组
            byte[] bytes = new byte[buffer.remaining()];
            //将缓冲区可读字节数组复制到新建的数组中
            buffer.get(bytes);
            result = new String(bytes, "UTF-8");
            //处理数据

            //发送应答消息
        }


        //没有读取到字节 忽略
//              else if(readBytes==0);
        //链路已经关闭，释放资源
        else if (readBytes < 0) {
            key.cancel();
            sc.close();
        }
        return result;
    }


    //异步发送应答消息
    public static void doWrite(SelectionKey key, String response) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //log.info("write: "+response);
        //将消息编码为字节数组
        byte[] bytes = response.getBytes();
        //根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将字节数组复制到缓冲区
        writeBuffer.put(bytes);
        //flip操作
        writeBuffer.flip();
        //发送缓冲区的字节数组
        channel.write(writeBuffer);
        //****此处不含处理“写半包”的代码
        log.info("send msg {}", response);
    }
}
