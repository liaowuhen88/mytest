package cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by liaowuhen on 2017/8/15.
 *
 * 参考资料
 * http://blog.csdn.net/zghwaicsdn/article/details/50957474
 *
 */
public class TestCglib2 {
    protected static Logger logger = LoggerFactory.getLogger(TestCglib2.class);

    public static void main(String args[]) {
        Enhancer enhancer =new Enhancer();

        CallbackFilter callbackFilter = new TargetMethodCallbackFilter();

        /**
         * (1)callback1：方法拦截器
         (2)NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
         (3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
         */
        Callback noopCb= NoOp.INSTANCE;
        Callback callback1=new TargetInterceptor();
        Callback fixedValue=new TargetResultFixed();
        Callback[] cbarray=new Callback[]{callback1,noopCb,fixedValue};

        //enhancer.setCallback(new TargetInterceptor());
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallbackFilter(callbackFilter);
        enhancer.setCallbacks(cbarray);


        TargetObject targetObject2=(TargetObject)enhancer.create();
        //logger.info(""+targetObject2);
        logger.info(targetObject2.method1("mmm1"));
        logger.info(""+targetObject2.method2(100));
        logger.info(""+targetObject2.method3(100));
        logger.info(""+targetObject2.method3(200));
    }
}
