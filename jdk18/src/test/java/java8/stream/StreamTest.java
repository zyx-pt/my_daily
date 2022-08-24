package java8.stream;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @ClassName java8.stream.StreamTest
 * @Author zhengyongxian
 * @Date 2022/7/21 15:50
 */
public class StreamTest {

    @Test
    public void test(){
        String str = "12PL34PL56";
        // 计算字符串中的匹配子字符串个数
        long count = Pattern.compile("\\PL+").splitAsStream(str).count();
        System.out.println(count);

        // 分割字符串并返回其列表
        List<String> pl = Stream.of(StringUtils.split(str, "\\PL+")).collect(Collectors.toList());
        pl.forEach(System.out::println);

        // limit限制个数，skip跳过前几个
        List<Double> collect1 = Stream.generate(Math::random).limit(100).skip(99).collect(Collectors.toList());
        collect1.forEach(System.out::println);
        System.out.println("---------------");
        // limit限制个数，skip跳过前几个
        List<Double> collect2 = Stream.generate(Math::random).limit(10).skip(1).collect(Collectors.toList());
        collect2.forEach(System.out::println);
    }
}
