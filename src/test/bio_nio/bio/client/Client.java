package bio_nio.bio.client;

import bio_nio.bio.service.ServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * 阻塞式I/O创建的客户端
 *
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class Client {
    final static char operators[] = {'+', '-', '*', '/'};
    private static Logger log = LoggerFactory.getLogger(ServerHandler.class);
    //默认的端口号
    private static int DEFAULT_SERVER_PORT = 12345;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void send(String msg) {
        send(DEFAULT_SERVER_PORT, msg);
    }

    public static void send(int port, String msg) {

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            Long start = System.currentTimeMillis();
            socket = new Socket(DEFAULT_SERVER_IP, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            //while(true){
            doIt(start, in, out, msg);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(in, out, socket);
        }
    }

    public static void doIt(Long start, BufferedReader in, PrintWriter out, String msg) throws IOException {
        Random random = new Random(System.currentTimeMillis());
        String expression = random.nextInt(100) + "" + operators[random.nextInt(4)] + (random.nextInt(100) + 1);
        log.info("算术表达式为：" + msg);

        out.println(msg);
        String result = in.readLine();
        Long end = System.currentTimeMillis();
        log.info("___结果为：" + result + "---------" + (end - start));
    }

    public static void close(BufferedReader in, PrintWriter out, Socket socket) {
        //一下必要的清理工作
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            out.close();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
