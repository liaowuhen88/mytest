package bio_nio.bio.service;

import utils.SysInfoAcquirerService;

import java.io.IOException;

/**
 * Created by liaowuhen on 2017/10/25.
 */
public class MainThreadPool {
    public static void main(String[] args) {
        //运行服务器

        SysInfoAcquirerService sas = new SysInfoAcquirerService();
        sas.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerNormalThreadPool.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
