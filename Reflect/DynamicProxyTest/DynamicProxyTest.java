package Reflect.DynamicProxyTest;

import Reflect.ProxyTest.Network;
import Reflect.ProxyTest.Server;
import org.junit.Test;

// 动态代理实例
public class DynamicProxyTest {
    @Test
    public void test01() {
        SuperMan superMan = new SuperMan();
        // proxySuperMan： 一个被代理类的对象，只能声明成被代理类实现的接口，被代理类不能同时是代理类
        Human proxySuperMan = (Human) ProxySuperMan.getProxyInstance(superMan);
        System.out.println(proxySuperMan);
        System.out.println("belief: " + proxySuperMan.getBelief());
        proxySuperMan.eat("玉米");

        Server server = new Server();
        Network proxyServer = (Network) ProxySuperMan.getProxyInstance(server);
        proxyServer.browse();
    }
}
