package java8.lambda;

import entity.Apple;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Description: 行为参数化--允许你定义一个代码块来表示一个行为，然后传递它
 *
 * @ClassName java8.lambda.BehaviorParam
 * @Author yxzheng
 * @Date 2022/7/21 17:05
 */
public class BehaviorParamTest {

    @Test
    public void test() {
        // 例如：需求：根据不同需要筛选苹果
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"),
                new Apple(165, "red"));
        // 1.常规方法筛选绿色苹果 -> 缺点：筛选条件变更，需要调整if的判断方法
        List<Apple> result1 = filterGreenApples(inventory);
        System.out.println("---------常规方法-----------");
        result1.forEach(item -> System.out.println("color: " + item.getColor() + ", weight: " + item.getWeight()));

        List<Apple> result1_1 = filterWeightApples(inventory);
        System.out.println("---------常规方法-----------");
        result1_1.forEach(item -> System.out.println("color: " + item.getColor() + ", weight: " + item.getWeight()));

        // 2.行为参数化-使用抽象条件筛选绿色苹果 -> 缺点：每种条件都要新建一个ApplePredicate，类似策略模式，但是比较啰嗦
        List<Apple> result2 = filterApples(inventory, new AppleGreenColorPredicate());
        System.out.println("---------行为参数化-抽象条件-----------");
        result2.forEach(item -> System.out.println("color: " + item.getColor() + "---weight: " + item.getWeight()));

        // 3.行为参数化-使用匿名类筛选绿色苹果 -> 缺点：每种条件都要新建一个ApplePredicate，类似策略模式，太多模板代码
        List<Apple> result3 = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "green".equals(apple.getColor());
            }
        });
        System.out.println("---------行为参数化-匿名类-----------");
        result3.forEach(item -> System.out.println("color: " + item.getColor() + "---weight: " + item.getWeight()));

        // 4.行为参数化-使用Lambda
        List<Apple> result4 = filterApples(inventory, (Apple apple) -> "green".equals(apple.getColor()));
        System.out.println("---------行为参数化-lamdba-----------");
        result4.forEach(item -> System.out.println("color: " + item.getColor() + "---weight: " + item.getWeight()));

        // 5.行为参数化-抽象化
        List<Apple> result5 = filter(inventory, (Apple apple) -> "green".equals(apple.getColor()));
        System.out.println("---------行为参数化-抽象化-----------");
        result5.forEach(item -> System.out.println("color: " + item.getColor() + "---weight: " + item.getWeight()));

    }

   /**
    * @Description: 常规方法，定义一个筛选绿色苹果的方法
    * @Author: yxzheng
    * @Date 2022/7/22 9:47
    * @param inventory
    * @return java.util.List<entity.Apple>
    */
    public List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * @Description: 常规方法，定义一个筛选重量超过150g苹果的方法
     * @Author: yxzheng
     * @Date 2022/7/22 9:47
     * @param inventory
     * @return java.util.List<entity.Apple>
     */
    public List<Apple> filterWeightApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * @Description: 行为参数化过滤苹果
     * @Author: yxzheng
     * @Date 2022/7/22 9:46
     * @param inventory
     * @param p
     * @return java.util.List<entity.Apple>
     */
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    /***
     * @Description: 通用过滤集合的方法
     * @Author: yxzheng
     * @Date 2022/7/21 18:00
     * @param list
     * @param p
     * @return java.util.List<T>
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T apple : list) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    /***
     * @Description: 通用过滤集合--精简
     * @Author: yxzheng
     * @Date 2022/7/21 18:00
     * @param list
     * @param p
     * @return java.util.List<T>
     */
    public static <T> List<T> filterList(List<T> list, Predicate<T> p) {
        return Optional.ofNullable(list).orElseGet(Collections::emptyList).stream()
                .filter(p).collect(Collectors.toList());
    }
}

