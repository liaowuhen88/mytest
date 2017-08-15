package cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by liaowuhen on 2017/8/15.
 *
 * 参考资料
 * http://blog.csdn.net/zghwaicsdn/article/details/50957474
 *
 */
public class TestCglib {
    public static void main(String args[]) {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject targetObject2=(TargetObject)enhancer.create();


        System.out.println("main_____"+targetObject2);
        System.out.println("main_____"+targetObject2.method1("mmm1"));
        System.out.println("main_____"+targetObject2.method2(100));
        System.out.println("main_____"+targetObject2.method3(200));
    }
}
