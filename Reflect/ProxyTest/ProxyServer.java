package Reflect.ProxyTest;

// 代理类
public class ProxyServer implements Network {
    private Network network;

    public ProxyServer(Network network) {
        this.network = network;
    }

    public void check() {
        System.out.println("联网前的检查");
    }

    @Override
    public void browse() {
        // 在真的访问网络前做一些检查
        check();
        network.browse();
    }
}
