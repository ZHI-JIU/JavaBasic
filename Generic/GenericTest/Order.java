package Generic.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Order<T> {
    private String orderName;
    private UUID orderId;
    T orderType;
    T[] bindOrder;

    // 泛型类的构造器无需加<>，但在实例化时需要加
    public Order() {
    }

    public Order(String orderName, UUID orderId, T orderType) {
        this.orderName = orderName;
        this.orderId = orderId;
        this.orderType = orderType;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public T getOrderType() {
        return orderType;
    }

    public void setOrderType(T orderType) {
        this.orderType = orderType;
    }

    public T[] getBindOrder() {
        return bindOrder;
    }

    public void setBindOrder(T[] bindOrder) {
        this.bindOrder = bindOrder;
    }

    public void generateBindOrder(T[] bindOrder) {
        // Type parameter 'T' cannot be instantiated directly
        // this.bindOrder = new T[10];
        this.bindOrder = (T[]) new Order[10];
    }

    // 'Generic.GenericTest.Order.this' cannot be referenced from a static context
//    public static void show(T orderType) {
//
//    }

    // public后的<E>是将E声明为一个泛型参数，如果不加声明
    public <E> List<E> ArrayToList(E[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }
}
