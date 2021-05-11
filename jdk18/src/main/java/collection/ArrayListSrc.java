package collection;

import sun.misc.SharedSecrets;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * <pre>
 * 描述：ArrayList源码分析
 * </pre>
 *
 * @Author zhengyongxian
 * @Date 2020/10/12 11:16
 * @Description: TODO
 */

public class ArrayListSrc<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 8683452581122892189L;
    /** 默认初始容量大小 */
    private static final int DEFAULT_CAPACITY = 10;
    /** 空数组（用于空实例）*/
    private static final Object[] EMPTY_ELEMENTDATA = {};
    /** 用于默认大小空实例的共享空数组实例 */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    /** 保存ArrayList数据的数组 */
    transient Object[] elementData;
    /** ArrayList所包含的元素个数 */
    private int size;

    /** 带初始容量参数的构造函数 */
    public ArrayListSrc(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    /** 默认构造函数 当添加第一个元素的时候数组容量才变成10*/
    public ArrayListSrc() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /** 构造一个包含指定集合的元素的列表，按照它们由集合的迭代器返回的顺序 */
    public ArrayListSrc(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**--------------------------------------- ArrayList扩容机制 -------------------------------------------------------------------*/
    /**
     * 将指定的元素追加到此列表的末尾。
     */
    public boolean add(E e) {
        //添加元素之前，先调用ensureCapacityInternal方法
        ensureCapacityInternal(size + 1);
        //这里看到ArrayList添加元素的实质就相当于为数组赋值
        elementData[size++] = e;
        return true;
    }

    /** 得到最小扩容量 */
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 获取默认的容量和传入参数的较大值
            // 当要add 进第1个元素时，minCapacity为1，在Math.max()方法比较后，minCapacity 为10
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    /** 判断是否需要扩容 */
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // minCapacity在第一次add时赋值10，进行第一次扩容。而后直到添加第11个元素，minCapacity(为11)比elementData.length（为10）要大。
        // 再次进入grow方法进行扩容
        if (minCapacity - elementData.length > 0)
            //调用grow方法进行扩容，调用此方法代表已经开始扩容了
            grow(minCapacity);
    }

    /**
     * 要分配的最大数组大小
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * ArrayList扩容的核心方法。
     */
    private void grow(int minCapacity) {
        // oldCapacity为旧容量，newCapacity为新容量
        int oldCapacity = elementData.length;
        //将oldCapacity 右移一位，其效果相当于oldCapacity /2，
        //我们知道位运算的速度远远快于整除运算，整句运算式的结果就是将新容量更新为旧容量的1.5倍，
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        //然后检查新容量是否大于最小需要容量，若还是小于最小需要容量，那么就把最小需要容量当作数组的新容量，
        // 当add第1个元素时，oldCapacity 为0，经比较后判断成立，newCapacity取minCapacity(为10)；
        // 其余情况判断均不成立（除调用ensureCapacity传入自定义的minCapacity外
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;

        // 如果新容量大于 MAX_ARRAY_SIZE,进入(执行) `hugeCapacity()` 方法来比较 minCapacity 和 MAX_ARRAY_SIZE，
        //如果minCapacity大于最大容量，则新容量则为`Integer.MAX_VALUE`，否则，新容量大小则为 MAX_ARRAY_SIZE 即为 `Integer.MAX_VALUE - 8`。
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError
                    ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    /**
     * ensureCapacity
     *
     * 如有必要，增加此 ArrayList 实例的容量，以确保它至少可以容纳由minimum capacity参数指定的元素数。
     * 向 ArrayList 添加大量元素之前最好先使用ensureCapacity 方法，以减少增量重新分配的次数。
     *
     * @param   minCapacity   所需的最小容量
     */
    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                // any size if not default element table
                ? 0
                // larger than default for default empty table. It's already
                // supposed to be at default size.
                : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    /**--------------------------------------- System.arraycopy() 和 Arrays.copyOf()方法 -------------------------------------------------------------------*/

    // 联系：copyOf() 内部实际调用了 System.arraycopy() 方法
    // 区别：
    //   arraycopy() :需要目标数组，将原数组拷贝到你自己定义的数组里或者原数组，而且可以选择拷贝的起点和长度以及放入新数组中的位置
    //   copyOf(): 是系统自动在内部新建一个数组，并返回该数组。

    /**
     * System.arraycopy
     *
     * 在此列表中的指定位置插入指定的元素。
     *
     * 1.先调用 rangeCheckForAdd 对index进行界限检查；然后调用 ensureCapacityInternal 方法保证capacity足够大；
     * 2.再将从index开始之后的所有成员后移一个位置；
     * 3.将element插入index位置；
     * 4.最后size加1。
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);

        //arraycopy()方法：自己复制自己实现让index开始之后的所有成员后移一个位置
        //elementData:源数组;
        // index:源数组中的起始位置;
        // elementData：目标数组；
        // index + 1：目标数组中的起始位置；
        // size - index：要复制的数组元素的数量；
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * Arrays.copyOf
     *
     * 以正确的顺序返回一个包含此列表中所有元素的数组（从第一个到最后一个元素）;
     * 返回的数组的运行时类型是指定数组的运行时类型。
     * 主要是为了给原有数组扩容
     */
    public Object[] toArray() {
        //elementData：要复制的数组；size：要复制的长度
        return Arrays.copyOf(elementData, size);
    }

    /** 返回此列表中指定位置的元素 */
    @Override
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    /** 返回此列表中的元素数 */
    @Override
    public int size() {
        return size;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }
}
