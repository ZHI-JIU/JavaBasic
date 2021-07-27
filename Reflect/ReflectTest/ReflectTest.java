package Reflect.ReflectTest;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class ReflectTest {
    @Test
    // 反射之前，对Person的操作，无法调用Person类的私有结构
    public void test01() {
        // 实例化对象
        Person p = new Person("Tom", 15, 1);

        // 调用属性、方法
        p.age = 16;
        System.out.println(p.toString());

        // 无法调用Person类的私有结构
//        p.name = "Tom_1";
//        p.showNation();
    }

    @Test
    // 反射之后，对Person的操作
    public void test02() throws Exception {
        // 通过反射实例化Person对象
        Class clazz = Person.class;
        // ps：这里类时写死的，实际运用中可以通过传进来的对象的getClass方法获取到对应的类。
        Constructor<Person> constructor = clazz.getConstructor(String.class, int.class);

        Object p = constructor.newInstance("Tom", 15);

        // 调用属性、方法
        System.out.println(p);
        Field age = clazz.getDeclaredField("age");
        age.set(p, 16);
        System.out.println(p.toString());

        Method show = clazz.getDeclaredMethod("show");
        show.invoke(p);

        // 调用私有结构（构造器、方法、属性），可以在获取结构后改变可见性，从而实现调用
        Constructor privateConstructor = clazz.getDeclaredConstructor(String.class);
        privateConstructor.setAccessible(true);
        Object p2 = privateConstructor.newInstance("Harry");
        System.out.println(p2);

        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p, "Harry_1");

        // 方法有参数时，需要注明参数类型
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        String nation = (String) showNation.invoke(p2, "China");
        System.out.println("showNation return: " + nation);
    }

    @Test
    // 获取Class实例
    public void test03() throws ClassNotFoundException {
        // 方式一
        Class clazz1 = Person.class;
        System.out.println(clazz1);

        // 方式二
        Person p2 = new Person("Tom", 15, 1);
        Class clazz2 = p2.getClass();
        System.out.println(clazz2);

        // 方式三
        Class clazz3 = Class.forName("Reflect.ReflectTest.Person");
        System.out.println(clazz3);

        System.out.println(clazz1 == clazz2);
        System.out.println(clazz1 == clazz3);

        // 方式四
        ClassLoader cl = ReflectTest.class.getClassLoader();
        Class clazz4 = cl.loadClass("Reflect.ReflectTest.Person");
        System.out.println(clazz4);
        System.out.println(clazz1 == clazz4);

    }

    // 通过反射获取类的结构--获取所有属性
    @Test
    public void test04() {
        Class clazz = Person.class;

        // 获取属性结构
        // getFields() 得到类和父类的pubic属性
        Field[] fields = clazz.getFields();
        // public int Reflect.ReflectTest.Person.age
        // public double Reflect.ReflectTest.Creature.weight
        for (Field field: fields) {
            System.out.println(field);
        }

        System.out.println();
        // getDeclaredFields() 获取当前运行时类中声明的所有属性，不考虑权限。不包含父类中声明的属性
        // private static final long Reflect.ReflectTest.Person.serialVersionUID
        // private java.lang.String Reflect.ReflectTest.Person.name
        // public int Reflect.ReflectTest.Person.age
        // int Reflect.ReflectTest.Person.id
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field: declaredFields) {
            System.out.println(field);
        }

        System.out.println("=====================");
        // 获取属性具体的结构（权限修饰符，数据类型，变量名）
        for (Field field: declaredFields) {
            System.out.println(field);
            // 获取权限修饰符
            int modifiers = field.getModifiers();
            System.out.println(modifiers);
            System.out.println(Modifier.toString(modifiers));

            // 获取数据类型
            Class type = field.getType();
            System.out.println("type: " + type);

            // 获取变量名
            String name = field.getName();
            System.out.println("name: " + name);

            System.out.println();
        }
    }

    // 通过反射获取类的结构--获取方法
    @Test
    public void test05() {
        Class clazz = Person.class;

        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            System.out.println(method);
        }

        System.out.println("================");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method: declaredMethods) {
            System.out.println(method);
        }

        // 获取方法声明的注解（只能获取生命周期是runtime的）
        System.out.println("================");
        for (Method method: declaredMethods) {
            System.out.println("方法： " + method);
            Annotation[] annotations  = method.getAnnotations();
            System.out.print("注解： ");
            for (Annotation annotation: annotations) {
                System.out.print(annotation + " ");

            }
            System.out.println();
        }

        // 获取权限修饰符

    }

    // 通过反射获取类的结构--获取构造器
    @Test
    public void test06() {
        Class clazz = Person.class;

        System.out.println("================");
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor: constructors) {
            System.out.println(constructor);
        }

        System.out.println("================");
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor constructor: declaredConstructors) {
            System.out.println(constructor);
        }
    }

    // 通过反射获取类的结构--获取父类
    @Test
    public void test07() {
        Class clazz = Person.class;

        System.out.println("================");
        Type superclass = clazz.getSuperclass();
        // class Reflect.ReflectTest.Creature
        System.out.println(superclass);

        System.out.println("================");
        Type genericSuperclass = clazz.getGenericSuperclass();
        // Reflect.ReflectTest.Creature<java.lang.String>
        System.out.println(genericSuperclass);

        // 获取泛型的类型
        System.out.println("================");
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (Type ata : actualTypeArguments) {
            System.out.println(ata);
        }
        System.out.println("ownerType: " + parameterizedType.getOwnerType());
        System.out.println("rawType: " + parameterizedType.getRawType());
        System.out.println("typeName: " + parameterizedType.getTypeName());
    }

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
}
