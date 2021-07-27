package Reflect;

import Reflect.ReflectTest.Person;
import org.junit.Test;

public class ClassLoaderTest {
    @Test
    public void test01() {
        // 自定义的类都是系统加载器加载的
        ClassLoader cl = ClassLoaderTest.class.getClassLoader();
        // jdk.internal.loader.ClassLoaders$AppClassLoader@3fee733d
        System.out.println(cl);

        // 系统类加载器的Parent是扩展类加载器
        // jdk.internal.loader.ClassLoaders$PlatformClassLoader@d7b1517
        ClassLoader clParent = cl.getParent();
        System.out.println(clParent);

        // 再上一层引导类加载器无法获取到
        // null
        ClassLoader clParent2 = clParent.getParent();
        System.out.println(clParent2);

        // java核心类库的加载器是引导类加载器
        // null
        ClassLoader cl2 = String.class.getClassLoader();
        System.out.println(cl2);
    }

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
}
