

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
  

---
## 一、为什么要使用泛型
集合容器在声明时不能确定到底允许存什么类型的数据，``JDK5``之后引入泛型来控制集合的类型，可以在编译期就校验集合中元素的合法性。  
类似一个标签，标识容器中可以放什么。比如可回收垃圾箱内只能放可回收垃圾。  

---
## 二、定义
泛型就是在定义**类**、**接口**时通过一个标识标识类中的某个**属性**的**类型**或者某个**方法**的**返回值**及**参数类型**。  
这个类型参数在使用（继承或实现接口、声明或创建对象）时确定（传入了实际的类型参数）。  

示例:  

``List<String>``表示只能存放String的List接口


---
## 三、如何使用泛型
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
### 3.1 不使用泛型
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

### 3.2 使用泛型
```java
    @Test
    public void test2() {
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
