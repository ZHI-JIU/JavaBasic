package Reflect.ProxyTest;

import org.junit.Test;

public class ProxyTest {
    // 静态代理示例
    @Test
    public void test01() {
        Server server = new Server();
        // 用被代理类实例化被代理类
        ProxyServer proxyServer = new ProxyServer(server);
        // 联网前的检查
        // 真实的服务器访问网络
        proxyServer.browse();
    }
}
