package utiltest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * 描述：Lists相关用法
 * </pre>
 *
 * @author zhengyx
 * @email zhengyx@gillion.com.cn
 * @date 2020/9/28
 * @time 15:56
 * @description: TODO
 */

public class ListsTest {

    @Test
    public void testLists(){
        // com.google.common.collect.Lists
        // 初始化
        // 静态工厂方法 -> 调用的是new ArrayList<>()
        List<String> list1 = Lists.newArrayList();
        // 工厂方法模式 -> 初始化时就指定起始元素
        List<String> list2 = Lists.newArrayList(null, "str1");    // 传入多参数
        List<String> list22 = Lists.newArrayList(new String[]{"22", "22"}); // 传入数组
        List<String> list222 = Lists.newArrayList(list22);                  // 传入集合
        // 工厂方法命名 -> 提高集合初始化大小的可读性.
        // 优点有：节约内存，节约时间，节约性能。代码质量提高。
        List<String> list3 = Lists.newArrayListWithCapacity(10);
        List<String> list4 = Lists.newArrayListWithExpectedSize(10);

        Set strSet = Sets.newHashSet();
    }

    @Test
    public void testMaps(){
        // com.google.common.collect.Maps
        //1、Maps.newHashMap()获得HashMap();
        Map<Integer, Integer> map1 = Maps.newHashMap();
        for (int i = 0; i < 10; i++) {
            map1.put(i, i);
        }
        System.out.println("map1：" + map1);
        //2、传入map1参数构建map2
        Map<Integer, Integer> map2 = Maps.newHashMap(map1);

        //3、使用条件：你确定你的容器会装多少个，不确定就用一般形式的
        //说明：这个容器超过3个还是会自动扩容的。不用担心容量不够用。默认是分配一个容量为16的数组，不够将扩容
        Map<Integer, Integer> map3 = Maps.newHashMapWithExpectedSize(3);

        //4、LinkedHashMap<K, V> 有序map
        Map<Integer,Integer> map4_1 = Maps.newLinkedHashMap();
        Map<Integer,Integer> map4_2 = Maps.newLinkedHashMapWithExpectedSize(11);
        Map<Integer, Integer> map4_3 = Maps.newLinkedHashMap(map1);
    }

}
