package algo.array;

/**
 * @Author zhengyongxian
 * @Date 2020/4/27
 */
public class GenericArray<T> {
    private T[] data;
    private int size;

    // 根据传入容量，构造Array
    public GenericArray(int capacity){
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }

    // 无参构造，默认数组容量为10
    public GenericArray(){
        this(10);
    }

    // 获取数组容量
    public int getCapacity(){
        return data.length;
    }

    // 获取当前元素个数
    public int count(){
        return this.size;
    }

    // 判断数组是否为空
    public boolean isEmpty(){
        return this.size == 0;
    }

    // 修改index位置的元素
    public void set(int index, T e){
        checkIndex(index);
        data[index] = e;
    }

    // 获取index位置元素
    public T get(int index){
        checkIndex(index);
        return data[index];
    }

    // 判断数组是否包含元素
    public boolean contains(T e){
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    //获取元素的下标，未找到返回-1
    public int find(T e){
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    // 在index位置插入元素e，时间复杂度O(m+n)
    public void add(int index, T e){
        checkIndexForAdd(index);
        if (size == data.length) {
            resize(2*data.length);
        }
        for (int i = size-1; i >= index; i--) {
            data[i+1] = data[i];
        }
        data[index] = e;
        size++;
    }

    // 删除index位置元素
    public T remove(int index){
        checkIndex(index);
        T result = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i-1] = data[i];
        }
        size--;
        data[size] = null;

        // 缩容
        if (size == data.length/4 && data.length/2 !=0) {
            resize(data.length/2);
        }
        return result;
    }

    // 删除指定元素
    public void removeElement(T e){
        int index = find(e);
        if (index != -1) {
            remove(index);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Array size = %d, capacity = %d \n", size, data.length));
        builder.append('[');
        for (int i = 0; i < size; i++) {
            builder.append(data[i]);
            if (i != size - 1) {
                builder.append(", ");
            }
        }
        builder.append(']');
        return builder.toString();
    }

    // 扩容素组，时间复杂度O(n)
    private void resize(int capacity){
        T[] newData = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }


    private void checkIndex(int index){
        if (index < 0 || index >= size){
            throw  new IllegalArgumentException("dd failed! Require index >=0 and index < size.");
        }
    }

    private void checkIndexForAdd(int index) {
        if(index < 0 || index > size) {
            throw new IllegalArgumentException("remove failed! Require index >=0 and index <= size.");
        }
    }
}
