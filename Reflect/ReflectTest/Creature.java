package Reflect.ReflectTest;

import java.io.Serializable;

public class Creature<T> implements Serializable {
    private static final long serialVersionUID = -6824199913576353586L;
    private char gender;
    public double weight;

    private void breath() {
        System.out.println("breath");
    }

    public void eat() {
        System.out.println("eat");
    }
}
