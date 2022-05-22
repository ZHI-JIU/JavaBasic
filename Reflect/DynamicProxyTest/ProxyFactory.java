package Reflect.DynamicProxyTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    // 根据传入的obj，动态生成一个被代理类为obj的代理类
    // obj: 被代理类的对象
    public static Object getProxyInstance(Object obj) {
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
        myInvocationHandler.bind(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), myInvocationHandler);
    }
}

// 构造MyInvocationHandler， 继承newProxyInstance中的第三个参数
class MyInvocationHandler implements InvocationHandler {

    // 被代理类的对象
    private Object obj;

    // 给obj赋值
    public void bind(Object obj) {
        this.obj = obj;
    }

    // 调用代理类的对象调用方法a时， 会自动调用此方法
    // 将被代理类要执行的方法a声明在此方法中就能实现调用代理类时处理被代理类的方法
    // proxy：代理类的对象
    // method：代理类的方法，也即被代理类的方法
    // args：代理类方法的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HumanUtil util = new HumanUtil();

        util.method1();
        // 调用obj对象的method方法
        Object ret = method.invoke(obj, args);
        util.method2();

        return ret;
    }
}
