package Reflect.ProxyTest;

// 被代理类
public class Server implements Network {
    @Override
    public void browse() {
        System.out.println("真实的服务器访问网络");
    }
}
