package Reflect.DynamicProxyTest;

public class SuperMan implements Human {
    @Override
    public String getBelief() {
        return "LLD SZD";
    }

    @Override
    public void eat(String food) {
        System.out.printf("eat %s.%n", food);
    }
}
