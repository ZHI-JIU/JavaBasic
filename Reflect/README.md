# Java反射机制概述
反射是动态语言的关键，允许程序在执行期间借助于Reflection API取得任何类的内部信息，并能直接操作任意对象的内部属性及方法。  
加载完类之后，在堆的方法区中就产生了一个Class类型的对象，一个类只有一个Class对象，这个对象包含类的完整结构信息。可以通过这个对象获取类的结构。这个对象就像镜子，透过镜子看到类的结构，故称之为反射。  
* 正常方式：引入类 -> new一个实例 -> 获取实例化对象
* 反射方式：实例化对象 -> getClass() -> 获取类的信息

## 静态语言 & 动态语言
* 动态语言：运行时可以改变其结构的语言：如函数、对象、代码可以被引进。已有函数可以被删除或变化。即运行时代码可以根据条件改变自身结构。
    * 例如：JavaScript、Python
* 静态语言：运行时结构不可改变的语言。
    * 例如：Java、C、C++
* Java不是动态语言，是“准动态语言”。有一定的动态性，可以通过反射机制、字节码操作获得类似动态语言的特性。
    * Java可以在运行期间决定造哪个类的对象，调用哪个类的方法。

## 反射的应用
* 在运行时判断对象的类
* 在运行时构造任意类的对象
* 在运行时判断任意类所具有的成员变量和方法
* 在运行时获取泛型信息
* 在运行时调用任意对象的成员变量和方法(包括私有的变量、方法、构造器)
* 在运行时处理注解
* 生成动态代理

## 反射相关的API
java.lang.Class: 代表一个类，描述类的类
java.lang.reflect.Method: 代表类的方法
java.lang.reflect.Field: 代表类的成员变量
java.lang.reflect.Construction: 代表类的构造器

## 反射和封装性
Q：反射和直接new都能实例化对象，并调用方法，该如何选择？  
A：编译时能确定要用什么对象，推荐直接new。不能确定时用反射。  

Q：反射可以调用私有方法，和封装性是否矛盾？  
A：不矛盾。封装性体现得是建议，将要保护的属性、方法私有化。

# 理解Class类并**获取Class实例**

类的加载过程：
```java
          javac.ext                                             java.exe
A.java  --------------> A.class(一个类对应一个或多个字节码文件) ------------> 解释运行，将字节码文件加载到内存中
```

加载到内存中的类成为**运行时类**，运行时类是Class的一个实例。

可以赋值给Class对象的类型：  
类、Override、基本数据类型、void、Class本身  
对于数组而言，只要**元素类型**和**维度**相同，就认为是同一个Class。


## 获取Class实例
有三种方式：
1. 调用运行时类的属性：``class``
    ```java
    Class clazz1 = Person.class;
    ```
2. 通过运行时类的对象的方法：``getClass()``
    ```java
    Person p2 = new Person("Tom", 15);
    Class clazz2 = p2.getClass();
    ```
3. 调用Class的静态方法：``forName(String classPath)``
    ```java
    Class clazz3 = Class.forName("Reflect.ReflectTest.Person");
    ```
4. 使用类的加载器：``classLoader``
    ```java
    ClassLoader cl = ReflectTest.class.getClassLoader();
    Class clazz4 = cl.loadClass("Reflect.ReflectTest.Person");
    ```
### 说明：
* 三种方法获取到的Class实例相同，都执行内存中的同一个运行时类。运行时类在内存中会缓存一段时间，通过不同方法获取到的都是这个缓存的对象。


# 类的加载与ClassLoader的理解
当程序使用某个类时，如果类没有加载到内存中，则系统会对类进行初始化：
1. 加载：将类的class文件读入内存中，并创建一个Class对象。
2. 链接：将Java类的二进制代码合并到JVM的运行状态中的过程。  
2.1. 验证：取保加载的类信息符合JVM规范。  
2.2. 准备：将类中的**静态变量**使用**默认值**初始化，并从方法区分配内存。  
2.3. 解析： 虚拟机常量池中的符号引用（常量名）替换成直接引用（地址）的过程。
3. 初始化：执行类构造器方法<clinit>()（是构造类信息的，不是造对象的那个构造器）。由编译器自动收集类中**所有变量的赋值动作**和**静态代码块**中的语句合并产生的。即将显示赋值的变量初始化。  
3.1. 类初始化时，如果父类还没有初始化，则会先触发父类的初始化。  
3.2. JVM保证一个类的<clinit>()在多线程中被正确地加锁和同步。

## ClassLoader
![image](https://github.com/ZHI-JIU/JavaBasic/blob/main/Reflect/png/classLoader.png)

类装载器的作用：用来将类的class文件读入内存中。它会创建一个Class对象，并缓存一段时间不被回收。

JVM定义了三种类加载器：
1. Bootstap ClassLoader：引导类加载器，用C++编写，JVM自带的类加载器，负责Java平台核心库，用来加载核心类库。这个加载器无法直接获取。
2. Extension ClassLoader：扩展类加载器，负责jre/lib/ext目录下的jar包或-D java.ext.dirs指定目录下的jar包加载到工作库。
3. System ClassLoader：系统类加载器，负责java -classpath或-D java.class.path所指定的目录下的类与jar包加载入工作库，是最常用的加载器。
4. 自定义类加载器

调用底层的类加载器的.getParent()方法可以获取上层的类加载器。引导类加载器无法获取，是用来加载java的核心类库的，无法加载自定义类。

# **创建运行时类的对象**
通过反射获取运行时类，再由``newInstance()``创建这个类的对象。
1. 运行时类必须提供空参构造器。newInstance实际调用的是运行时类的**空参构造器**，如果没有定义空参构造器会报错``NoSuchMethodException``
2. 空参构造器的访问权限要可见。通常设置为public。否则报错``IllegalAccessException``

在javabean中要求提供一个public的空参构造器的原因：
1、便于通过反射创建运行时类对象。
2、便于子类继承运行时类时，调用super()时保证父类有该构造器。

```java
@Test
public void test02() throws IllegalAccessException, InstantiationException {
    // 通过反射获取运行时类，再由newInstance创建这个类的对象
    // 方式一：强转类型
    Class clazz = Person.class;
    Person obj = (Person) clazz.newInstance();
    // Person{name='null', age=0}
    System.out.println(obj);

    // 方式二：使用泛型
    Class<Person> clazz1 = Person.class;
    Person obj1 = clazz1.newInstance();
    // Person{name='null', age=0}
    System.out.println(obj1);
    }
```
## 反射的动态性
可能写代码时不确定要创建的类是什么,运行时才能确定。尤其是底层框架这种具有广泛适配性的，更不能确定要造的对象了。
可以通过反射来构造对象还不需要在写代码时就明确类的构造器。

【todo】怎么使用非空参的构造器来创建对象呢？

# 获取运行时类的完整结构
首先构造一个复杂结构的Person类，详见ReflectTest.Person。  
有继承，有实现接口。属性、构造器、方法都有private、default、public权限的。

* 获取属性结构：
    * ``getFields()``：获取当前运行时类及其父类中声明为**public**访问权限的属性
    * ``getDeclaredFields()``：获取当前运行时类中声明的所有属性，不考虑权限。不包含父类中声明的属性
    * ``getModifiers()``：获取属性的权限修饰符
    * ``getType()``：获取属性的类型
    * ``getName()``：获取属性的变量名
    
* 获取方法：
    * ``getMethods()``：获取当前运行时类及其父类中声明为public访问权限的方法
    * ``getDeclaredMethods()``：获取当前运行时类中声明的所有方法，不考虑权限。不包含父类中声明的方法


* 获取构造器：
    * ``getConstructors()``：获取当前运行时类及其父类中声明为public访问权限的构造器
    * ``getDeclaredConstructors()``：获取当前运行时类中声明的所有构造器，不考虑权限。不包含父类中声明的构造器

* 获取父类：
    * ``getSuperClass()``：获取父类
    * ``getGenericSuperClass()``：获取带泛型的父类
    * ``(ParameterizedType ) clazz.getGenericSuperClass()``：获取带泛型的父类的泛型

```java
    @Test
    public void test04() {
        Class clazz = Person.class;

        // 获取属性结构
        // getFields() 得到类和父类的pubic属性
        System.out.println("=====================");
        System.out.println("     getFields()     ");
        System.out.println("=====================");
        Field[] fields = clazz.getFields();
        // public int Reflect.ReflectTest.Person.age
        // public double Reflect.ReflectTest.Creature.weight
        for (Field field: fields) {
            System.out.println(field);
        }

        System.out.println();
        System.out.println("=====================");
        System.out.println(" getDeclaredFields() ");
        System.out.println("=====================");
        // getDeclaredFields() 获取当前运行时类中声明的所有属性，不考虑权限。不包含父类中声明的属性
        // private static final long Reflect.ReflectTest.Person.serialVersionUID
        // private java.lang.String Reflect.ReflectTest.Person.name
        // public int Reflect.ReflectTest.Person.age
        // int Reflect.ReflectTest.Person.id
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field: declaredFields) {
            System.out.println(field);
        }

        System.out.println();
        // 获取属性具体的结构（权限修饰符，数据类型，变量名）
        for (Field field: declaredFields) {
            System.out.printf("field: %s%n", field);
            // 获取权限修饰符
            int modifiers = field.getModifiers();
            System.out.printf("modifiers: %s%n", modifiers);
            System.out.printf("string modifiers: %s%n", Modifier.toString(modifiers));

            // 获取数据类型
            Class type = field.getType();
            System.out.printf("type: %s%n",type);

            // 获取变量名
            String name = field.getName();
            System.out.printf("name: %s%n", name);

            System.out.println();
        }
//        private static final long Reflect.ReflectTest.Person.serialVersionUID
//        private java.lang.String Reflect.ReflectTest.Person.name
//        public int Reflect.ReflectTest.Person.age
//        int Reflect.ReflectTest.Person.id
//
//        field: private static final long Reflect.ReflectTest.Person.serialVersionUID
//        modifiers: 26
//        string modifiers: private static final
//        type: long
//        name: serialVersionUID
//
//        field: private java.lang.String Reflect.ReflectTest.Person.name
//        modifiers: 2
//        string modifiers: private
//        type: class java.lang.String
//        name: name
//
//        field: public int Reflect.ReflectTest.Person.age
//        modifiers: 1
//        string modifiers: public
//        type: int
//        name: age
//
//        field: int Reflect.ReflectTest.Person.id
//        modifiers: 0
//        string modifiers:
//        type: int
//        name: id
    }
```

# **调用运行时类的指定结构**
## 调用指定的属性

## 调用指定的方法
```java

```

## 调用指定的构造器
虽然可以这么写，其实更通用的是使用空参构造器``newInstance()``。
```java
    @Test
    public void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Person.class;
        // 获取指定的构造器
        Constructor constructor = clazz.getDeclaredConstructor(String.class, int.class);
        // 保证构造器可访问
        constructor.setAccessible(true);
        // 调用构造器构造对象
        Person p = (Person) constructor.newInstance("Tom", 17);
        System.out.println(p);
    }
```
# 反射的应用：动态代理
## 代理模式
使用代理将对象包装起来，使用代理对象来取代原始对象。  
任何对原始对象的调用都要经过代理，代理对象决定了**是否**、**何时**转发到原始对象上。  
按代理类和目标对象的类是否在编译期间确定，可分为：
1. 静态代理：不利于程序扩展；每一个代理类只能为一个接口服务，会导致代理过多。
2. 动态代理：抽象角色（接口）中声明的所有方法都背转义到处理器一个集中的方法中处理，可以通过一个代理类完成全部的代理功能。

### 静态代理
代理类和被代理类实现同一个接口，代理类的方法中调用被代理类的方法，再做一些额外的操作。
详见ProxyTest

### 动态代理
代理类和被代理类实现同一个接口，代理类是动态生成的，编译期不确定具体是哪个类。
1. 如何根据加载到内存中的被代理类，动态创建代理类及其对象。
2. 当通过代理类的方法时，如何动态去调用被代理类中的同名方法。
详见DynamicProxyTest

### 动态代理与AOP（Aspect Orient Programming）
多个代码段调用相同的方法，而又希望这个方法可以是动态的，可以放入任意的方法。
[!image(AOP)]
