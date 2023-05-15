package java8.optional;

import com.google.common.collect.Lists;
import entity.Car;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * @Description: Optional<T>是一个可以包含或不可以包含非空值的容器对象，目的是解决 NullPointerExceptions的问题
 * @Author yxzheng
 * @Date 2023/4/24 15:26
 */
public class OptionalTest {

    @Test
    public void test(){
        Integer value1 = 10;
        Integer value2 = null;
        Car car = new Car();
        // Optional.of 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> optionalA = Optional.of(value1);
        // Optional.ofNullable - 允许传递为 null 参数，为 null，则返回一个空的 Optional
        Optional<Integer> optionalB = Optional.ofNullable(value2);

        // isPresent() 值存在返回 true，否则返回 false
        System.out.println("optionalA.isPresent(): " + optionalA.isPresent());
        System.out.println("optionalB.isPresent(): " + optionalB.isPresent());
        // ifPresent() 有值则调用 consumer 处理，否则不处理
        optionalA.ifPresent(item -> {
            item++;
            System.out.println("optionalA.ifPresent()+1: "+item);
        });

        // get() 有值就返回，没有抛出 NoSuchElementException
        System.out.println(optionalA.get());
        // orElse() 如果有值则将其返回，否则返回指定的其它值， orElse 方法将传入的字符串作为默认值
        System.out.println("optionalA.orElse() " + optionalB.orElse(new100()));
        // orElseGet()  与 orElse 方法类似，区别在于得到的默认值 ，orElseGet 方法可以接受 Supplier 接口的实现用来生成默认值
        System.out.println("optionalA.orElseGet() " + optionalB.orElseGet(() -> new100()));
        // orElse 和 orElseGet 在Optional无值时差别不大，在有值时orElse 仍然会去调用方法创建对象，而 orElseGet 不会再调用方法
        System.out.println("optionalB.orElse() " + optionalA.orElse(new100()));
        System.out.println("optionalB.orElseGet() " + optionalA.orElseGet(() -> new100()));
        // orElseThrow() 如果有值则将其返回，否则抛出 supplier 接口创建的异常。
        try {
            optionalB.orElseThrow(() -> new Exception("异常！！！"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // map() 如果有值，则对其执行调用 mapping 函数得到返回值。否则返回空 Optional
        Integer map = optionalA.map(item -> item+11).orElse(0);
        System.out.println("Optional map(): " + map);
        // flatMap() 返回值必须是 Optional，而 map 的返回值可以是任意的类型 T
        Integer flatMap = optionalA.flatMap(item -> Optional.of(item+11)).orElse(0);
        System.out.println("Optional flatMap(): " + flatMap);
        // filter()
        List<String> strings = Arrays.asList("rmb", "doll", "ou");
        for (String s : strings) {
            Optional<String> o = Optional.of(s).filter(s1 -> s1.contains("o"));
            System.out.println(o.orElse(s+"不包含o"));
        }
    }

    public static Integer new100(){
        System.out.println("new Integer 100.");
        return 100;
    }

    /**
     * @Description: Optional的预期用途主要是作为返回类型。获取此类型的实例后，可以提取该值（如果存在）或提供其他行为（如果不存在）
     * 什么时候不使用
     * a) 不要将其用作类中的字段，因为它不可序列化。
     * 如果确实需要序列化包含Optional值的对象，则Jackson库提供了将Optionals视为普通对象的支持。
     * 这意味着Jackson将空对象视为空，将具有值的对象视为包含该值的字段。可以在jackson-modules-java8项目中找到此功能。
     * b) 不要将其用作构造函数和方法的参数，因为这会导致不必要的复杂代码。
     * @Author: yxzheng
     * @Date 2023/4/24
     */
    @Test
    public void commonUse(){
        // 断言处理空集合
        List<String> list = null;
//        System.out.println(list.size());// java.lang.NullPointerException
        list = Optional.ofNullable(list).orElseGet(Collections::emptyList);
        System.out.println(list.size());
        List<Car> carList = Lists.newArrayList();

        Car car1 = carList.stream().findFirst().orElse(new Car("default"));
    }

}
