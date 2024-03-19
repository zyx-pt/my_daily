package java8.function;

import entity.Apple;
import org.junit.Test;

public class FunctionTest {

    /**
     * Function 函数式接口 代替 if else
     */
    @Test
    public void testVUtils(){
        // 可替换业务中判断进行抛出异常操作
        VUtils.isTureThrow(false).throwMessage("判断为true时，抛出异常！！！");
//        VUtils.isTureThrow(true).throwMessage("判断为true时，抛出异常！！！");
        // 参数为true或false时，分别进行不同的操作
        VUtils.isTureOrFalseHandle(false).trueOrFalseHandle(()->{
            System.out.println("判断为true时，执行处理！！！");
        }, ()->{
            System.out.println("判断为false时，执行处理！！！");
        });
        // 如果参数为true执行
        Apple apple = new Apple();
        VUtils.isTureConsumer(true, apple, x -> x.setWeight(100));
        // 参数为true或false时，分别进行不同的操作
        VUtils.isBlankOrNoBlank("").presentOrElseHandle(System.out::println, () -> System.out.println("参数为空"));
    }

    /**
     * 四大内置核心函数式接口
     * 类型           类               抽象方法
     * 消费型接口    Consumer<T>     void accept(T t);
     * 供给型接口    Supplier<T>     T get();
     * 函数型接口    Function<T,R>   R apply(T t);
     * 断言型接口    Predicate<T>    boolean test(T t);
     *
     */

}
