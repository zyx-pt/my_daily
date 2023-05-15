package collection;

import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedMap;

/**
 * <pre>
 * 描述：NavigableMap接口的相关说明
 * </pre>
 *
 * @Author yxzheng
 * @Date 2020/10/13 8:46
 * @Description: TODO
 */

public interface NavigableMapSrc <K,V> extends SortedMap<K,V> {

    /** 找到第一个比指定的key小的值 */
    Entry<K,V> lowerEntry(K key);

    /** 找到第一个比指定的key小的key */
    K lowerKey(K key);

    /** 找到第一个小于或等于指定key的值 */
    Entry<K,V> floorEntry(K key);

    /** 找到第一个小于或等于指定key的key */
    K floorKey(K key);

    /**  找到第一个大于或等于指定key的值 */
    Entry<K,V> ceilingEntry(K key);

    /** 找到第一个大于或等于指定key的key */
    K ceilingKey(K key);

    /** 找到第一个大于指定key的值 */
    Entry<K,V> higherEntry(K key);

    /** 找到第一个大于指定key的key */
    K higherKey(K key);

    /** 获取最小值 */
    Entry<K,V> firstEntry();

    /** 获取最大值 */
    Entry<K,V> lastEntry();

    /** 删除最小的元素 */
    Entry<K,V> pollFirstEntry();

    /** 删除最大的元素 */
    Entry<K,V> pollLastEntry();

    /** 返回一个倒序的Map */
    NavigableMap<K,V> descendingMap();

    /** 返回一个Navigable的key的集合，NavigableSet和NavigableMap类似 */
    NavigableSet<K> navigableKeySet();

    /** 对上述集合倒序 */
    NavigableSet<K> descendingKeySet();

}
