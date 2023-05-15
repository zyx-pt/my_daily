package entity;

import java.util.function.Supplier;

/**
 * @Description:
 *
 * @Author: yxzheng
 * @Date: 2020/8/17 22:19
 */
public class Car {

    private String name;
    private double price;

    public Car(){}

    public Car(String name) {
        this.name = name;
    }


    /**
     * @Description: Supplier是jdk1.8的接口，这里和lamda一起使用
     *
     * @Author: yxzheng
     * @Date: 2020/8/17 22:26
     */
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}