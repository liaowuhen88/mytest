package dubbo;

/**
 * Created by liaowuhen on 2017/11/29.
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "Hello " + name;
    }
}
