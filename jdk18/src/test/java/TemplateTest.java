import com.google.common.collect.Lists;
import entity.Account;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 临时测验
 * @Author zhengyongxian
 * @Date 2020/6/22 22:11
 */
public class TemplateTest {
    
    /**
     * @Description: 测试List Map的为空的循环问题
     * 结论：对List Map的初始化不能赋空值null，故对所传List Map参数要判空
     *      Map 中key可以为null
     * @Author: zhengyongxina
     * @Date: 2020/6/22 22:12
     * @param 
     * @return: void
     */
    @Test
    public void test1(){
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        // objectObjectHashMap = null; // java.lang.NullPointerException
        objectObjectHashMap.forEach((k, v) -> {
            System.out.println("aaaaa");
        });
        System.out.println(objectObjectHashMap.get(null));
        objectObjectHashMap.put(null, "1111");
        objectObjectHashMap.put(null, "2222");
        System.out.println(objectObjectHashMap.get(null)); // 2222

        ArrayList<Object> objects = new ArrayList<>();
        // objects = null; // java.lang.NullPointerException
        objects.forEach(x -> {
            System.out.println("cccc");
        });

    }
    
    @Test
    public void test2(){
        String str1 = new String("111");
        String str2 = new String("111");
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());
        System.out.println(str1 != str2);

        List<String> list1 = Lists.newArrayList();
        list1.add("xx");
        list1.add(null);
        // collect1.size() = 2 collect1.get(0):"xx"  collect1.get(1):null
        List<String> collect1 = list1.stream().collect(Collectors.toList());
        // collect1.size() = 1 collect1.get(0):null
        List<String> collect2 = list1.stream().filter(item -> StringUtils.isEmpty(item)).collect(Collectors.toList());
        // collect1.size() = 1 collect1.get(0):"xx"
        List<String> collect3 = list1.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());

        List<Account> accountList = Lists.newArrayList();
        Account account1 = new Account(null, 18);
        Account account2 = new Account("hehe", 18);
        accountList.add(account1);
        accountList.add(account2);
        // java.lang.NullPointerException: element cannot be mapped to a null key
        // Map<String, List<Account>> map1 = accountList.stream().collect(Collectors.groupingBy(Account::getName));
        // System.out.println(collect2);
        // groupingBy前要进行过滤
         Map<String, List<Account>> map2= accountList.stream()
                 .filter(item -> StringUtils.isNotEmpty(item.getName()))
                 .collect(Collectors.groupingBy(Account::getName));
         System.out.println(map2);

    }

    @Test
    public void test3(){
        System.out.println( Calendar.JANUARY);
        BigDecimal weight = new BigDecimal("0");
        System.out.println(weight.divide(new BigDecimal("10000")));
        set(weight);
        List<String> list = Lists.newArrayList();
        setList(list);
        list.forEach(x -> System.out.println(x));
        String yr0537 = "123;4,5，6";
        String[] splitYr0537 = org.apache.commons.lang.StringUtils.split(yr0537, ",，;；、");
        String yr05374 = "123;4,5，6";


    }

    private void setList(List<String> list) {
        list.add("xxxx");
    }

    public void set(BigDecimal weight){
        weight = weight.add(BigDecimal.ONE);
    }
}
