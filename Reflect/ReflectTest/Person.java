package Reflect.ReflectTest;

import java.io.Serializable;

@MyAnnotation(value = "hi")
public class Person extends Creature<String> implements Comparable<String>, MyInterface {

    private static final long serialVersionUID = 8619426540279363279L;
    // 属性，private、public、default三种权限
    private String name;
    @MyAnnotation(value = "age")
    public int age;
    int id;

    // 构造器，分private、public、default三种权限
    Person() {
    }

    @MyAnnotation(value = "abc")
    private Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // 方法，
    void show() {
        System.out.println("show test: " + toString());
    }

    @MyAnnotation()
    private String showNation(String nation) throws NullPointerException, ClassCastException {
        System.out.println("国籍：" + nation);
        return nation;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }

    @Override
    public void info() {
        System.out.println("类：Person");
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    private static void showDesc() {
        System.out.println("类静态方法：Person");
    }
}
