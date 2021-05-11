package collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhengyongxian
 * @date 2020/10/27 13:38
 */
public class HashMapTest {

    @Test
    public void testHashMap(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "zyx");
        hashMap.put("2", "hehe");
        System.out.println(hashMap.get("1"));
    }

    @Test
    public void testConCurrentHashMap(){
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("1", "hehe");
        System.out.println(concurrentHashMap.get("1"));
    }
}
