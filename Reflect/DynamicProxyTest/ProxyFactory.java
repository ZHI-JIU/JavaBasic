package Reflect.DynamicProxyTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    // 用被代理类的对象实例化一个代理类
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

    public void bind(Object obj) {
        this.obj = obj;
    }

    // 调用代理类的对象调用方法a时， 会自动调用此方法
    // 将被代理类要执行的方法a声明在此方法中就能实现调用代理类时处理被代理类的方法
    // proxy：代理类的对象
    // method：代理类的方法
    // args：代理类方法的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用obj对象的method方法
        return method.invoke(obj, args);
    }
}
