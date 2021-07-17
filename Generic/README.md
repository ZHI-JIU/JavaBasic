
# 泛型
- 为什么要有泛型  
    - 编译时保证数据安全
- 定义
- 如何使用泛型
    - 泛型只能是包装类或类，不能是基本数据类型
    - 定义时泛型命名无所谓，只是一个形参
- 自定义泛型结构
- 泛型在继承上的体现
- 通配符的使用
- 泛型应用举例  
  
##  1. <a name=''></a>为什么要使用泛型
集合容器在声明时不能确定到底允许存什么类型的数据，``JDK5``之后引入泛型来控制集合的类型，可以在编译期就校验集合中元素的合法性。  
类似一个标签，标识容器中可以放什么。比如可回收垃圾箱内只能放可回收垃圾。  

##  2. <a name='-1'></a>泛型的定义
泛型就是在定义**类**、**接口**时通过一个标识标识类中的某个**属性**的**类型**或者某个**方法**的**返回值**及**参数类型**。  
这个类型参数在使用（继承或实现接口、声明或创建对象）时确定（传入了实际的类型参数）。  

示例:  

``List<String>``表示只能存放String的List接口

##  3. <a name='-1'></a>如何使用泛型
- 集合接口或集合类在JDK5时都修改为带泛型的接口
- 在实例化集合类时，可以指明具体的泛型类型
- 指明完以后，在集合类或接口中凡是定义类或接口时，内部结构（``方法``、``构造器``、``属性``等）使用到类的泛型的泛型的位置，都指定为实例化时声明的类型
  ```java
  // 以iterator接口为例，声明时指定了E后(比如说指定为String)，next()方法的返回类型就确定为String了
  public interface Iterator<E> {
      boolean hasNext();
  
      E next();
  
      default void remove() {
          throw new UnsupportedOperationException("remove");
      }
  
      default void forEachRemaining(Consumer<? super E> action) {
          Objects.requireNonNull(action);
          while (hasNext())
              action.accept(next());
      }

  ```
- 泛型的类型必须是类，不能是基本数据类型，需要用对应的包装类
- 如果实例化时，没有指定泛型的类型，默认类型是Object类
###  3.1. <a name='-1'></a>不使用泛型
需要自行处理类型校验等。
```java
    
    public void test1() {
        ArrayList scores = new ArrayList();
        scores.add(78);
        scores.add(95);
        scores.add("Tom");

        int sum = 0, cnt = 0;
        for (Object score: scores) {
            // 需要判断类型且强行转换，否则会有类型转换异常
            if (score instanceof Integer) {
                sum += (Integer) score;
                cnt++;
            }
        }
        System.out.println("平均成绩：" + sum*1.0/cnt);
    }
```
输出  
```java
平均成绩：86.5
```

###  3.2. <a name='-1'></a>使用泛型
```java
    @Test
    public void test2() {
        // JDK7后声明时指定了泛型，创建实例的时候可以不指定，与声明时保持一致
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(78);
        scores.add(95);
        // 放入其他类型，编译会报错
        // scores.add("Tom");

        int sum = 0, cnt = 0;
        // 迭代器也是可以指定泛型的
        Iterator<Integer> iterator = scores.iterator();
        while(iterator.hasNext()) {
            sum += iterator.next();
            cnt++;
        }
        System.out.println("平均成绩：" + sum*1.0/cnt);
    }
```
输出  
```java
平均成绩：86.5
```

## 自定义泛型结构
- 可以定义为泛型的结构：类、接口、非静态方法的参数类型、非静态方法的返回值类型、非静态属性的类型，静态方法中不能使用泛型。参考``GenericTest``。  
    ```java
    // 这是因为泛型的类型是造对象的时候才能确定下来的，而静态方法、静态属性是早于造对象的时候就被加载的。
    // 'Generic.GenericTest.Order.this' cannot be referenced from a static context
    //    public static void show(T orderType) {
    //
    //    }
   ```
  
### 泛型类、泛型接口
- 定义后声明时不指定泛型的类型，则默认为Object类型。不推荐这么使用。
- 子类继承带泛型的父类时，可以选择继承泛型，也可以选择结束泛型。如果指明了父类的泛型类型，则子类不是带泛型的，实例化子类对象时不需要指明泛型类型。如果没有指明父类的泛型类型，则子类仍然是泛型类。
    ```java
    // 子类不保留泛型，指定父类的具体类型
    public class SubOrder extends Order<String> {
    }
      
    // 子类不保留泛型，不指定类型，默认为Object
    public class SubOrder extends Order {
    }
    
    // 等价于public class SubOrder extends Order<Object> {}
  
    // 子类保留泛型，全部保留
    public class SubOrder1<T1, T2> extends Order<T1, T2> {
    }
  
    // 子类保留泛型，部分保留，仅保留了T2，T1固定为Integer
    public class SubOrder1<T2> extends Order<Integer, T2> {
    }
  
    // 子类不保留泛型，指定父类的具体类型，又定义了自己的泛型
    public class SubOrder<A, B> extends Order<String> {
    }
      
    // 子类不保留泛型，不指定类型，默认为Object，又定义了自己的泛型
    public class SubOrder<A, B> extends Order {
    }
    
    // 等价于public class SubOrder extends Order<Object> {}
    
    // 子类保留泛型，全部保留，又定义了自己的泛型
    public class SubOrder1<T1, T2, A, B> extends Order<T1, T2> {
    }
    
    // 子类保留泛型，部分保留，仅保留了T2，T1固定为Integer，又定义了自己的泛型
    public class SubOrder1<T2, A, B> extends Order<Integer, T2> {
    }
    ```
  使用时，SubOrder如果指定了泛型类型，编译器会报错
    ```java
    // Type 'Generic.GenericTest.SubOrder' does not have type parameters
    //SubOrder<String> subOrder = new SubOrder();

    SubOrder1<String> subOrder1 = new SubOrder1<>();
    // 定义了泛型类型后，提供其他类型的入参编译会报错
    // Required type: String, Provided: int
    //subOrder1.setOrderType(123);
    subOrder1.setOrderType("微信商城订单");
    ```
- 泛型类可能有多个泛型参数
    ```java
    public interface Map<K, V>
    ```
- 泛型类的泛型类的构造器无需加<>，但在实例化时需要加
- 实例化后，泛型的结构必须与声明时保持一致
- 泛型不同的引用不能相互赋值
    ```java
    // 泛型不同的引用不能相互赋值
    ArrayList<String> stringList = null;
    ArrayList<Integer> intList = null;
    // Required type: ArrayList<String>, Provided: ArrayList<Integer>
    // stringList = intList;
    ```
- 如果泛型结构是一个接口或者抽象类，则不能创建泛型对象。（这是由接口和抽象类的特质决定的）
- 异常类不能定义为泛型结构，catch的类型也不能是泛型。
    ```java
    // Generic class may not extend 'java.lang.Throwable'
    // public class ExceptionTest<T> extends Exception{
    // }
    ```
- 泛型本身是一个参数，不能指定new T[], 但是可以(T[]) new Object[]，先造一个Object类型的数组，再强转成T[]类型的。
    ```java
    public void generateBindOrder(T[] bindOrder) {
        // Type parameter 'T' cannot be instantiated directly
        // this.bindOrder = new T[10];
        this.bindOrder = (T[]) new Order[10];
    }
    ```
  
### 泛型方法
在方法中出现了泛型的结构，泛型的参数与类的泛型参数没有任何关系。
泛型方法所述的类是不是泛型类没有关系。  
如``Order``类中``getOrderType``并不是泛型方法，返回类型T就是类的泛型参数。
```java
// public后的<E>是将E声明为一个泛型参数，如果不加声明
public <E> List<E> ArrayToList(E[] arr) {
    return new ArrayList<>(Arrays.asList(arr));
}
```
- 泛型方法可以是静态方法，因为泛型方法中的泛型参数是调用时指定的，并非实例化对象时指定的。
