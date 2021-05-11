package collection;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <pre>
 * 描述：HashMap源码分析
 * </pre>
 *
 * @Author zhengyongxian
 * @Date 2020/10/12 15:13
 * @Description: TODO
 */

public class HashMapSrc <K,V> extends AbstractMap<K,V>
        implements Map<K,V>, Cloneable, Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 362498820763181265L;
    /** 默认的初始容量是 16*/
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /** 最大容量 */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /** 默认的填充因子 */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /** 当桶(bucket)上的结点数大于这个值时会转成红黑树 */
    static final int TREEIFY_THRESHOLD = 8;
    /** 当桶(bucket)上的结点数小于这个值时树转链表 */
    static final int UNTREEIFY_THRESHOLD = 6;
    /** 桶中结构转化为红黑树对应的table的最小大小 */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /** 存储元素的数组，总是2的幂次倍 */
    transient Node<K,V>[] table;
    /** 存放具体元素的集 */
    transient Set<Entry<K,V>> entrySet;
    /** 存放元素的个数，注意这个不等于数组的长度*/
    transient int size;
    /** 每次扩容和更改map结构的计数器 */
    transient int modCount;
    /** 临界值 当实际大小(容量*填充因子 --> capacity * loadFactor)超过临界值时，会进行扩容 */
    int threshold;
    /** 加载因子 */
    final float loadFactor;

    /** Node节点 */
    static class Node<K,V> implements Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Entry<?,?> e = (Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    /**--------------------------------------------- 构造函数 --------------------------------------------------------------*/
    public HashMapSrc() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    /** 指定“容量大小”的构造函数 */
    public HashMapSrc(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /** 指定“容量大小”和“加载因子”的构造函数 */
    public HashMapSrc(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    /** 包含另一个Map的构造函数 */
    public HashMapSrc(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }

    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {
            // 判断table是否已经初始化
            if (table == null) {
                // 未初始化，s为m的实际元素个数
                float ft = ((float)s / loadFactor) + 1.0F;
                int t = ((ft < (float)MAXIMUM_CAPACITY) ? (int)ft : MAXIMUM_CAPACITY);
                // 计算得到的t大于阈值，则初始化阈值
                if (t > threshold)
                    threshold = tableSizeFor(t);
            }
            // 已初始化，并且m元素个数大于阈值，进行扩容处理
            else if (s > threshold)
                resize();
            // 将m中的所有元素添加至HashMap中
            for (Entry<? extends K, ? extends V> e : m.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false, evict);
            }
        }
    }

    /**------------------------------------------------- put方法 -----------------------------------------------------------*/

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof HashMapSrc.TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }

    /**------------------------------------------------- get方法 -----------------------------------------------------------*/
    public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
    
    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    /**------------------------------------------------- resize方法-扩容 -----------------------------------------------------------*/
    final Node<K,V>[] resize() {
        return null;
    }

    final void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index; Node<K,V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;
            do {
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }
    
    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
    }

    void afterNodeAccess(Node<K,V> p) { }

    void afterNodeInsertion(boolean evict) { }
    
    TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }
    
    static final class TreeNode<K,V> extends  Node<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }

        /**
         * Returns root of tree containing this node.
         */
        final TreeNode<K,V> root() {
            for (TreeNode<K,V> r = this, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

        final TreeNode<K,V> putTreeVal(HashMapSrc<K,V> map, Node<K,V>[] tab,
                                               int h, K k, V v) {
            return null;
        }

        final TreeNode<K,V> getTreeNode(int h, Object k) {
            return ((parent != null) ? root() : this).find(h, k, null);
        }

        final TreeNode<K,V> find(int h, Object k, Class<?> kc) {
            return null;
        }

        final void treeify(Node<K,V>[] tab) {
        }
    }

}
