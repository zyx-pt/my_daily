package java8.lambda;

import entity.Car;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * @Description: lambda在 方法引用 、 函数式接口中的应用
 *
 * 方法引用通过方法的名字来指向一个方法，使用一对冒号 :: 。
 *
 * 函数式接口(Functional Interface)
 *      是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口，可以被隐式转换为 lambda 表达式。
 *
 * @Author zhengyongxian
 * @Date 2020/8/17 22:25
 */
public class LambdaForFunTest {

    /**
     * 四大内置核心函数式接口
     * 类型           类               抽象方法
     * 消费型接口    Consumer<T>     void accept(T t);
     * 供给型接口    Supplier<T>     T get();
     * 函数型接口    Function<T,R>   R apply(T t);
     * 断言型接口    Predicate<T>    boolean test(T t);
     *
     */
    public static void main(String[] args) {

        System.out.println("----- 1. Runnable -----");
        // 在1.8之前使用匿名内部类实现
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Test Runnable before java 1.8.");
            }
        };
        runnable.run();
        // 使用Lambda表达式来表示该接口的一个实现
        Runnable runnable1 = () -> System.out.println("Test Runnable use java 1.8.");
        runnable1.run();

        System.out.println("----- 2. Supplier -----");
        // 构造器引用：它的语法是Class::new，或者更一般的Class< T >::new
        // create方法使用Supplier接收
        final Car car = Car.create(Car::new);
        Supplier<Car> supplier = Car::new;
        Car car1 = supplier.get();

        final List<Car> cars = Arrays.asList(car);
        // 静态方法引用：它的语法是Class::static_method
        cars.forEach(Car::collide);
        // 特定类的任意对象的方法引用：它的语法是Class::method
        cars.forEach(Car::repair);
        // 特定对象的方法引用：它的语法是instance::method
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);

        System.out.println("----- 3. Consumer -----");
        Consumer<String> consumer = System.out::println;
        consumer.accept("Test Consumer.");

        System.out.println("----- 4. Comparator -----");
        // 相当于 (x, y) -> Integer.compare(x,y);
        Comparator<Integer> comparator = Integer::compare;
        System.out.println(comparator.compare(1,2));

        System.out.println("----- 5. Function -----");
        System.out.println(concatName("zyx", x->x+" hehe"));

        Function<String, String> function = (x) -> x +" and hehe";
        System.out.println(function.apply("zyx"));

        // 必须存在一个String参数的构造器
        Function<String, Car> function1 = Car::new;
        Car car2 = function1.apply("zyx");
        System.out.println(car2.getName());

        System.out.println("----- 6. Predicate -----");
        Predicate<Integer> predicate = x -> x>10;
        boolean test = predicate.test(5);
        System.out.println("5>10:"+test);

        // BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        BiPredicate<String, String> biPredicate = String::equals;
        boolean test1 = biPredicate.test("a", "A");
        System.out.println("a==A:"+test1);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println("输出所有数据:");
        // 传递参数 n
        eval(list, n -> true);
        System.out.println();

        System.out.println("输出所有偶数:");
        eval(list, n -> n%2 == 0 );
        System.out.println();

        System.out.println("输出大于 3 的所有数字:");
        eval(list, n -> n > 3 );
        System.out.println();
    }

    public static String concatName(String name1, Function<String, String> fun){
        return fun.apply(name1);
    }

    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
        for(Integer n: list) {
            if(predicate.test(n)) {
                System.out.print(n + " ");
            }
        }
    }
}
