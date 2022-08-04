package java8.stream;

import entity.Account;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Description: stream在集合中的应用
 * @Author zhengyongxian
 * @Date 2020/8/28 15:01
 */
public class StreamForCollectionTest {

    @Test
    public void test() {
        /** 查询集合中符合条件的总条数 */
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account("qwe", 19, "beijing", new BigDecimal("10.1")));
        accountList.add(new Account(null, 11, "beijing", new BigDecimal("10.1")));
        accountList.add(new Account("asd", 15, "shanghai", new BigDecimal("10.1")));
        accountList.add(new Account("zxc", 18, null, new BigDecimal("10.1")));
        accountList.add(new Account("hehe", 21, "hangzhou", new BigDecimal("10.1")));
        accountList.add(new Account("zyx", 25, "puitan1", new BigDecimal("10.1")));
        accountList.add(new Account("zyx", 24, "putian2", new BigDecimal("10.1")));
        accountList.add(new Account("zyx", 24, null, null));


        /** 获取集合中某个属性不重复的集合 */
        List<String> distinctAccountByName = accountList.stream()
                .map(Account::getName).distinct().collect(Collectors.toList());
        /** 筛选集合中符合某些条件 */
        List<Account> filterAccountByAge = accountList.stream()
                .filter(item -> item.getAge() > 18).collect(Collectors.toList());
        /** 根据某一属性筛选 (去重) */
        List<Account> distinctByName = accountList.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getName()))
                .filter(distinctByName(Account::getName)).collect(Collectors.toList());
        /** sorted 排序 */
        // 按年龄升序(同理也可名字排序)
        List<Account> accountSorted1 = accountList.stream()
                .sorted(Comparator.comparing(Account::getAge)).collect(Collectors.toList());
        accountSorted1.forEach(item -> System.out.println(item.getName()+"---"+item.getAge()));
        // 按年龄降序
        List<Account> accountSorted2 = accountList.stream()
                .sorted(Comparator.comparing(Account::getAge).reversed()).collect(Collectors.toList());
        System.out.println();
        accountSorted2.forEach(item -> System.out.println(item.getName()+"---"+item.getAge()));
        // 针对数字存成String，需要进行排序
        //                 .sorted(Comparator.comparing(e -> (StringUtils.isEmpty(e.getName()) ? null : Integer.valueOf(e.getName())), Comparator.nullsLast(Comparator.naturalOrder())))
        // 排序处理null   nullsFirst->null排前 nullsLast->null排后
        List<Account> accountSorted3 = accountList.stream()
                .sorted(Comparator.comparing(Account::getName, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
        System.out.println();
        accountSorted3.forEach(item -> System.out.println(item.getName()+"---"+item.getAge()));
        /**统计数据*/
        Integer totalAge = accountList.stream().mapToInt(Account::getAge).sum();
        Long weightAge = accountList.stream().filter(item -> !Objects.isNull(item.getWeight())).mapToLong(Account::getWeight).sum();
        BigDecimal total = accountList.stream()
                .filter(item -> !Objects.isNull(item.getWallet()))
                .map(Account::getWallet).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("all account wallet:" + total);


        /**
         * groupingBy 统计的使用
         * 注意点：key不能为null，要使用filter进行过滤
         *
         */
        System.out.println("----------------groupingBy----------------");
        /** 统计集合中某一属性的个数，用Map返回 */
        Map<String, Long> nameCountMap = accountList.stream()
                .filter(p -> StringUtils.isNotEmpty(p.getName()))
                .collect(Collectors.groupingBy(Account::getName, Collectors.counting()));
        System.out.println("name -> count"+nameCountMap);
        /** 把集合存储到Map中，key为某个属性，value为集合 */
        // 属性 -> 实体类（1：n）
        Map<String, List<Account>> nameToListMap = accountList.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getName()))
//                .collect(Collectors.groupingBy(Account::getName, Collectors.toList()));
                    .collect(Collectors.groupingBy(Account::getName));
        System.out.println("name -> accountList："+nameToListMap);
        /** 统计后按key进行排序 */
        // TreeMap默认为按照key升序
        TreeMap<String, List<Account>> orderByNameToListMap = accountList.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getName()))
                .collect(Collectors.groupingBy(Account::getName, TreeMap::new, Collectors.toList()));
        // 降序
        NavigableMap<String, List<Account>> stringListNavigableMap = orderByNameToListMap.descendingMap();
        /** 累加求和 */
        Map<String, Integer> nameToCountAgeMap = accountList.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getName()))
                .collect(Collectors.groupingBy(Account::getName, Collectors.summingInt(Account::getAge)));
        System.out.println("name -> sumAge："+nameToCountAgeMap);
        /** 统计某一属性，以分隔符拼接另一属性（为null也会拼接上，可以使用filter过滤） */
        // 属性 -> 属性（1：n）
        Map<String, String> nameToJoinAddressMap = accountList.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getName()))
//                .filter(item -> StringUtils.isNotEmpty(item.getAddress()))
                .collect(Collectors.groupingBy(Account::getName, Collectors.mapping(Account::getAddress, Collectors.joining(","))));
        System.out.println("name -> join address："+nameToJoinAddressMap);

        List<List<Account>> repeatList = accountList.stream().
                collect(Collectors.groupingBy(item -> item.getName()))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
        /**
         * toMap的使用
         * 把集合存储到Map中，key为某个属性，value为另一个属性或实体类
         * 注意：属性->实体类: 其中[key可为null], [key不重复]
         *      属性->属  性: 其中 [key不为null]，[value不为null]，[key不重复]
         *
         */
        System.out.println("----------------toMap----------------");
        // 属性 -> 属性（1：1）
        Map<String, String> nameToAddressMap1 = accountList.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getName()) && StringUtils.isNotEmpty(item.getAddress()))
                .collect(Collectors.toMap(Account::getName, Account::getAddress, (oldValue, newValue)-> newValue));
        // 属性 -> 属性（1：n）
        Map<String, String> nameToAddressMap2 = accountList.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getName()) && StringUtils.isNotEmpty(item.getAddress()))
                .collect(Collectors.toMap(Account::getName, Account::getAddress, (oldValue, newValue)->oldValue+","+newValue));
        // 这种处理方法可以不用判断key和value是否为null
        // 属性 -> 属性（1：n）
        Map<String, List<String>> nameToAddressMap3 = accountList.stream()
                .collect(Collectors.toMap(Account::getName,
                        item -> {
                            List<String> getAddressList = new ArrayList<>();
                            getAddressList.add(item.getAddress());
                            return getAddressList;
                        },
                        (List<String> oldList, List<String> newList) -> {
                            oldList.addAll(newList);
                            return oldList;
                        }));
        System.out.println("name -> address："+nameToAddressMap1);
        System.out.println("name -> join address with ','："+nameToAddressMap2);
        System.out.println("name -> addressList："+nameToAddressMap3);

        // 属性 -> 实体类（1：1）
//        Map<String, Account> nameToAccountMap1 = Maps.uniqueIndex(accountList, Account::getName);
//        Map<String, Account> nameToAccountMap2 = accountList.stream()
//                .collect(Collectors.toMap(Account::getName, Function.identity()));
        // 注意点：转换成Map时，集合中作为key的属性存在重复，则会报错duplicate key，需要另行处理
        // duplicate key的处理方案
        Map<String, Account> nameToAccountMap3 = accountList.stream()
                .collect(Collectors.toMap(Account::getName, Function.identity(), (v1, v2) -> v2));
        System.out.println("name -> account："+nameToAccountMap3);
        // Map --> List<entity>
        List<Account> accounts = nameToAccountMap3.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        /** String-List-Map互转 */
        String str1 = "a,b,c";
        String str2 = "1,1,2,3,3";
        // String（，隔开）==> List<String> 注意：str1为null会报错，空字符串不会，建议进行判空
        List<String> list1= Arrays.asList(StringUtils.split(str1, ","));
        // String（，隔开）==> List<Long>:
        List<Long> list2 = Arrays.asList(str2.split(","))
                .stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        // List<String> ==> String (，隔开)
        String lsitToStr = StringUtils.join(list1, ",");

        // 统计数量
        Map<Long, Long> countListToMap = countListToMap(list2);
        System.out.println(countListToMap);

        // sorted排序
        Random random = new Random();
//        random.ints().limit(10).sorted().forEach(System.out::println);

        // parallel并行
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        Long count = strings.parallelStream().filter(StringUtils::isEmpty).count();

        // Collectors归约操作toList toMap counting joining
        System.out.println("-----------筛选------------");
        List<String> filtered = strings.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(","));
        System.out.println("合并字符串: " + mergedString);

        // 统计 使用统计结果的收集器
        System.out.println("------------统计收集器的使用-------------");
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());


    }

    /**
     * @Description: 统计数量
     *
     * @Author: zhengyongxian
     * @Date: 2020/9/3 17:51
     * @param list
     * @return: java.util.Map<java.lang.Long,java.lang.Long>
     */
    public static Map<Long,Long> countListToMap(List<Long> list){
        Map<Long, Long> map = new HashMap<>();
        Set<Long> set = new HashSet<>(list);
        for (Long productIdOnly : set) {
            for (Long productId : list) {
                if (productId.equals(productIdOnly)) {
                    if (map.containsKey(productIdOnly)) {
                        Long count = map.get(productIdOnly);
                        count++;
                        map.put(productIdOnly, count);
                    } else {
                        map.put(productIdOnly, 1L);
                    }
                }
            }
        }
        return map;
    }

    /**
     * @Description: 根据某一属性去重
     *
     * @Author: zhengyongxian
     * @Date: 2020/9/14 14:07
     * @param keyExtractor
     * @return: java.util.function.Predicate<T>
     */
    private static <T> Predicate<T> distinctByName(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    // putIfAbsent
    // key不存在或者key已存在但是值为null --> put进去，返回结果null
    // 如果结果等于null，说明该对象的不重复,返回true --> filter恰好会留下表达式为true的数据
}
