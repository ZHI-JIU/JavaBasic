package Generic.GenericTest;

import org.junit.Test;

import java.util.*;

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
        ArrayList<String> stringList = new ArrayList<>(Arrays.asList("11", "22", "33"));
        ArrayList<Integer> intList = new ArrayList<>(Arrays.asList(1,2,3));
        // Required type: ArrayList<String>, Provided: ArrayList<Integer>
        // stringList = intList;

        // ArrayList<?>是个泛型实例的通用父类
        ArrayList<?> list = null;
        list = intList;
        print(list);
        print(stringList);

        // Required type: capture of ?, Provided:int
//        list.add(1);

        // Required type: int, Provided: capture of ?
//        int value = list.get(0);
        Object value = list.get(0);
    }

    public void print(List<?> list) {
        for(Object i: list) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    @Test
    public void test3() {
        List<? extends Order> list1 = null;
        List<? super Order> list2 = null;

        List<Order> list3 = null;
        List<SubOrder> list4 = null;
        List<Object> list5 = null;

        // ? extends Order表示可以接受Order或Order的子类作为泛型的类型，父类Object没法给它赋值
        list1 = list3;
        list1 = list4;
        //list1 = list5;

        // ? super Order表示可以接受Order或Order的父类作为泛型的类型，子类SubOrder没法给它赋值
        list2 = list3;
        //list2 = list4;
        list2 = list5;
    }
}
