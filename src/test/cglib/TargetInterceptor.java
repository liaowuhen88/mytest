package cglib;

/**
 * Created by liaowuhen on 2017/8/15.
 */

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TargetInterceptor implements MethodInterceptor{

    /**
     * 重写方法拦截在方法前和方法后加入业务
     * Object obj为目标对象
     * Method method为目标方法
     * Object[] params 为参数，
     * MethodProxy proxy CGlib方法代理对象
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] params,
                            MethodProxy proxy) throws Throwable {
        System.out.println("************调用前****************************");
        Object result = proxy.invokeSuper(obj, params);
        System.out.println(result);
        System.out.println("************调用后****************************");
        return result;
    }


}
