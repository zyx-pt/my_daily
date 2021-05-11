package collection;

import entity.Account;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;


/**
 * @author zyx
 * 用于测试集合的相关内容
 * 注意使用单元测试4.11及以上版本不在包含hamcrest，需要导入hamcrest-core-1.3.jar
 */
public class CollectionTest {

        /**
         * Arrays.asList()的使用
         * 1、基本数据类型的array转list,只保存数组地址
         * 2、add/remove/clear方法会抛出UnsupportedOperationException异常        class java.util.Arrays$ArrayList
         * 3、array -> list 推荐使用java8： Arrays.stream(arr).collect(Collectors.toList());   class java.util.ArrayList
         *          如果是基本数据类型的array添加.boxed()
         * Collection.toArray()的使用
         * 1、String[] str = list.toArray(new String[0]); 参数的类型与数组一致，长度为0
         *      new String[0]就是起一个模板的作用，指定了返回数组的类型，0是为了节省空间，因为它只是为了说明返回的类型。
         */
        @Test
        public void testArrayAndListTransfer() {
            // 基本数据类型array转list只保存一个数组地址
            int[] arr1 = {1, 2, 3};
            List list1 = Arrays.asList(arr1);
            // 根据反编译，传入基本类型的数组后，会被转换成一个二维数组，而且是new int[1][arr.length]这样的数组
            // List list1 = Arrays.asList(new int[][] { arr1 });
            System.out.println(list1.size());   // 1
            System.out.println(list1.get(0));   // 数组地址值
    //        System.out.println(myList.get(1));    // 报错：ArrayIndexOutOfBoundsException
            int[] array = (int[]) list1.get(0);
            System.out.println(array[1]);   // 2
            System.out.println();

            // 包装类型array转list
            Integer[] arr2 = {1, 2, 3};
            List list2 = Arrays.asList(arr2);
            System.out.println(list2.size());   // 3
            System.out.println(list2.get(0));   // 1
            System.out.println(list2.get(1));   // 2
    //        list2.add(4);              // 运行时报错：UnsupportedOperationException
    //        list2.remove(1);           // 运行时报错：UnsupportedOperationException
    //        list2.clear();             // 运行时报错：UnsupportedOperationException
            // array 转 list 正确使用
            List listss = Arrays.stream(arr2).collect(Collectors.toList());
            listss.add(4);
            System.out.println(listss.get(0));    // 1
            arr2[0] = 2;
            System.out.println(list2.get(0));   // 2
            System.out.println(listss.get(0));    // 1
            System.out.println();

            // 正确转ArrayList
            List list3 = Arrays.asList(1, 2, 3);
            System.out.println(list3.getClass());   // class java.util.Arrays$ArrayList
            // new ArrayList
            System.out.println(new ArrayList<>( Arrays.asList(arr1)).getClass());   // class java.util.ArrayList
            System.out.println(new ArrayList<>( Arrays.asList(arr2)).getClass());   // class java.util.ArrayList
            // java8
            List list4 = Arrays.stream(arr2).collect(Collectors.toList());
            List list5 = Arrays.stream(arr1).boxed().collect(Collectors.toList());
            System.out.println(list4.getClass());           // class java.util.ArrayList
            System.out.println(list5.getClass());           // class java.util.ArrayList
            System.out.println();

            //Collection.toArray()的使用 list -> array
            String [] str = new String[]{"dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"};
            List<String> list = Arrays.asList(str);
            Collections.reverse(list);  // 反转
            str = list.toArray(new String[0]); // 没有指定类型的话会报错
            System.out.println(str[0]);

            int[] arrays = new int[]{1,2,3};
            List<Integer> intList = Arrays.stream(arrays).boxed().collect(Collectors.toList());
            System.out.println(Arrays.toString(intList.toArray()));
            String[] strArr = new String[]{"1", "2", "3"};
            List<String> stringList = Arrays.stream(str).collect(Collectors.toList());
            List<String> stringList2 = Arrays.asList(strArr);
            String[] objects = stringList2.toArray(new String[0]);
            System.out.println(Arrays.toString(stringList.toArray()));
            System.out.println(Arrays.toString(objects));
    }

    /**
     * 判断空的相关操作
     */
    @Test
    public void testNull(){
        Account account = new Account("xxx", 11);
        System.out.println(account == null);   // false

        String[] strs1 = StringUtils.split("111", ";");  // {"111"} "111;"->{"111"}
        String[] strs2 = StringUtils.split(null, ";");   // null
        String[] strs3 = StringUtils.split("", ";");     // {}
        List<String> list1  = Arrays.asList(strs1); // [111] size=1
//        List<String> list2  = Arrays.asList(strs2);  // java.lang.NullPointerException
//        List<String> list4 = Arrays.stream(strs2).collect(Collectors.toList()); // java.lang.NullPointerException
        List<String> list3  = Arrays.asList(strs3);  //[] size = 0;
        System.out.println();

    }

    /**
     * foreach循环不能进行add、remove、clear
     * 原因：
     *      增强for循环，是依赖iterator实现。
     *      遍历时进行iterator的next()方法时会判断
     *      modCount（集合实际被修改的次数，属于集合的成员变量）和
     *      expectedModCount（迭代器期望该集合被修改的次数，属于集合内部类Itr[一个Iterator的实现]的成员变量，ArrayList.iterator方法被调用的时候初始化的）
     *      是否不相等来抛出ConcurrentModificationException并发修改的异常。
     *      两个Count不相等的原因是：执行集合自己的add/remove方法，会修改modCount，并没有对expectedModCount做任何操作。
     *
     */
    @Test
    public void testForeach(){
        List<String> list1 = new ArrayList<String>();
        list1.add("Tom");
        list1.add("Jack");
        list1.add("Mic");
        list1.add("Seven");
        for (String s : list1) {
            if (s.equals("Jack")) {
//                list2.remove(s);    // java.util.ConcurrentModificationException
            }
        }
        System.out.println(list1);
        // 反编译结果如下
        ArrayList list2 = new ArrayList();
        list2.add("Tom");
        list2.add("Jack");
        list2.add("Mic");
        list2.add("Seven");
        Iterator iterator = list2.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(s.equals("Jack")){
//                list2.remove(s); // 执行后再次iterator.next()抛出 java.util.ConcurrentModificationException并发修改异常
                // 正确使用
                // 1.普通for循环
                // 2.直接使用Iterator进行操作
                iterator.remove();
            }
        } while(true);
        System.out.println(list2);


        // 解决方法：1.使用Java 8中提供的filter过滤
        List<String> list3 = new ArrayList<String>();
        list3.add("Tom");
        list3.add("Jack");
        list3.add("Mic");
        list3.add("Seven");
        list3 = list3.stream().filter(userName -> !userName.equalsIgnoreCase("Jack")).collect(Collectors.toList());
        System.out.println(list3);

        // 解决方法：2.直接使用fail-safe的集合类
        ConcurrentLinkedDeque<String> list4 = new ConcurrentLinkedDeque<String>();
        list4.add("Tom");
        list4.add("Jack");
        list4.add("Mic");
        list4.add("Seven");
        for (String userName : list4) {
            if (userName.equals("Jack")) {
                list4.remove(userName);
            }
        }
        System.out.println(list4);
    }

    /**
     * @Description: 测试ArrayList扩容--每次会扩容为原来的1.5倍，奇数的话会丢掉小数
     * int newCapacity = oldCapacity + (oldCapacity >> 1);
     *
     * @Author: zhengyongxina
     * @Date: 2020/10/12 14:08
     * @param
     * @return: void
     */
    @Test
    public void testArrayListGrow(){
        List<Integer> arrayList = new ArrayList<>();
        System.out.println(getArrayListCapacity(arrayList)); // 0
        //增加元素，使其扩容
        arrayList.add(0);
        System.out.println(getArrayListCapacity(arrayList)); // 10

        for(int i = 0; i < 10; ++i)
            arrayList.add(0);
        System.out.println(getArrayListCapacity(arrayList)); // 15

        for(int i = 0; i < 5; ++i)
            arrayList.add(0);
        System.out.println(getArrayListCapacity(arrayList)); // 22
    }

    public static int getArrayListCapacity(List<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[])field.get(arrayList);
            return objects.length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Test
    public void testArrayListEnsureCapacity(){
        ArrayList<Object> list = new ArrayList<Object>();
        final int N = 10000000;
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法前："+(endTime1 - startTime1)); // 2870

        ArrayList<Object> list2 = new ArrayList<Object>();
        long startTime2 = System.currentTimeMillis();
        list2.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list2.add(i);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法后："+(endTime2 - startTime2)); // 2480
    }
}
