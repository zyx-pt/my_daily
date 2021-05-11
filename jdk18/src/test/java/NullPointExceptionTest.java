import entity.Account;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * 描述：空指针异常的出现情况积累
 * </pre>
 * null.method()
 *
 * @author zhengyx
 * @email zhengyx@gillion.com.cn
 * @date 2020/9/28
 * @time 18:08
 * @description: TODO
 */

public class NullPointExceptionTest {

    /**
     * @Description: String使用相关
     *
     * @Author: zhengyongxina
     * @Date: 2020/9/29 9:06
     * @param
     * @return: void
     */
    @Test
    public void test1(){
        // 1. 判断String相等时
        String str1 = null;
        String str2 = "test";
        // 当前一个为null时出现空指针异常，一般是确认一定有值的放在前--不推荐使用
        boolean equals = str1.equals(str2); // java.lang.NullPointerException
        // 推荐使用StringUtils.equals(str1, str2)
        boolean equals1 = StringUtils.equals(str1, str2);
        boolean equals2 = StringUtils.equalsAny(str1, str2, "str3", "str4");
        // 2. String.contains(null)
        boolean equalsContains = str2.contains(null); // java.lang.NullPointerException

    }

    /**
     * @Description: 空对象调用方法
     *
     * @Author: zhengyongxina
     * @Date: 2020/9/29 9:11
     * @param
     * @return: void
     */
    @Test
    public void test2(){
        Account account = null;
        // 在不确定对象为空时，要进行判空
        // String name1 = account.getName(); //java.lang.NullPointerException
        account = new Account();
        String name2 = Objects.isNull(account) || StringUtils.isEmpty(account.getName())? "" : account.getName();
        String name3 = Objects.nonNull(account) && StringUtils.isNotEmpty(account.getName())? "" : account.getName();

    }

    /**
     * @Description: 集合操作
     *
     * @Author: zhengyongxina
     * @Date: 2020/9/29 9:22
     * @param
     * @return: void
     */
    @Test
    public void test3(){
        List<String> list = new ArrayList<>();
        list = null;
        // 对为null的集合进行for循环，不确定时要进行判空
        if (CollectionUtils.isNotEmpty(list)) {
            for (String s : list) { //java.lang.NullPointerException
                System.out.println("xxxx");
            }
        }
        // 原因是，foreach循环时通过调用Collection.iterator()方法返回迭代器。因此隐含null.method()
        for (String s : list) { //java.lang.NullPointerException
            System.out.println("xxxx");
        }
    }

    /**
     * @Description: 数值比较
     *
     * @Author: zhengyongxina
     * @Date: 2020/9/29 9:29
     * @param
     * @return: void
     */
    @Test
    public void test4(){
        Integer num1 = null;
        // null 自动拆箱抛NPE
        // num2 = num1; //// java.lang.NullPointerException
        int num2 = Objects.isNull(num1) ? 1 : num1;
        // Integer为null时，进行比较（场景从数据库获取的数据
        boolean equals1 = num1 == num2;                      // java.lang.NullPointerException
        boolean equal2 = Integer.compare(num1, num2) == 0; // java.lang.NullPointerException
        // 对不确定数值的比较要进行判空
        boolean equals3 = Objects.nonNull(num1) && num1 == num2;

        // BigDecimal进行比较的两个数只要一个为null，进行比较就报错
        BigDecimal bd1 = null;
        BigDecimal bd2 = BigDecimal.ONE;
         boolean bdEquals1 = bd1.compareTo(bd2) == 0;              // java.lang.NullPointerException
         boolean bdEquals2 = BigDecimal.ONE.compareTo(bd1) == 0; // java.lang.NullPointerException
        // 判断前进行判空
        boolean bdEquals3 = Objects.nonNull(bd1) && Objects.nonNull(bd2) && bd1.compareTo(bd1) == 0;

    }
}
