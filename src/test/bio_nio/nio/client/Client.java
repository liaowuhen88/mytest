package bio_nio.nio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static Logger log = LoggerFactory.getLogger(Client.class);

    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 12345;
    private static ClientHandle clientHandle;

    public static void start(String msg) throws Exception {
        start(msg, DEFAULT_HOST, DEFAULT_PORT);
    }

    public static synchronized void start(String msg, String ip, int port) throws Exception {
        if (clientHandle != null) {
            clientHandle.stop();
        }
        clientHandle = new ClientHandle(msg, ip, port);

        new Thread(clientHandle, "Client").start();
    }

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10000; i++) {
            start("send:" + i);
        }
    }
}
