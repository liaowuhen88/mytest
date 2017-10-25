package string;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liaowuhen on 2017/9/7.
 */
public class StringTest {
    @Test
    public void init() throws IOException {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 1000000; i++) {
            sb.append("我们都是中国人，我们热爱祖国，我们都是好亲年" + i);
        }
        System.out.println(sb.length());
        //System.out.print(sb.toString().getBytes().length/1000);
        //System.out.println(sb.toString());
        File file = new File("F:\\file.txt");
        System.out.println(file.canWrite());
        //FileInputStream fi = new FileInputStream(file);
        FileOutputStream fi = new FileOutputStream(file);
        //fi.read(sb.toString().getBytes());
        fi.write(sb.toString().getBytes());
        fi.close();
    }

    @Test
    public void init1() {
        String sb = "";

        for (int i = 0; i < 100000; i++) {
            sb = sb + "我们都是中国人，我们热爱祖国，我们都是好亲年" + i;
        }
        System.out.println(sb.length());
        System.out.println(sb.toString());
    }
}
