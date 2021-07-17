package Generic.GenericTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class OrderTest {
    @Test
    public void test1() {
        // 不指定泛型的类型，默认为Object
        Order order = new Order();
        order.setOrderType("Test");

        Order<String> order1 = new Order<String>("淘宝订单", UUID.randomUUID(), "淘宝");
        // Type 'Generic.GenericTest.SubOrder' does not have type parameters
        //SubOrder<String> subOrder = new SubOrder();

        SubOrder1<String> subOrder1 = new SubOrder1<>();
        // 定义了泛型类型后，提供其他类型的入参编译会报错
        // Required type: String, Provided: int
        //subOrder1.setOrderType(123);
        subOrder1.setOrderType("微信商城订单");

    }

    @Test
    public void test2() {
        // 泛型不同的引用不能相互赋值
        ArrayList<String> stringList = null;
        ArrayList<Integer> intList = null;
        // Required type: ArrayList<String>, Provided: ArrayList<Integer>
        // stringList = intList;
    }
}
