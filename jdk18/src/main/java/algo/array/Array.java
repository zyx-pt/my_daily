package algo.array;

/**
 * 数组的插入、删除、按下标随机访问操作；
 * 数组中的数据类型为int
 * @Author zhengyongxian
 * @Date 2020/4/26
 */
public class Array {
    public static void main(String[] args) {
        Array array = new Array(5);
        array.printAll();
        array.insert(0, 3);
        array.insert(0, 4);
        array.insert(1, 5);
        array.insert(3, 9);
        array.insert(3, 10);
        array.insert(3, 11);
        array.printAll();
        array.delete(3);
        array.printAll();
    }
    // 定义整型数据data保存数据
    private int[] data;
    // 定义数组长度
    private int n;
    // 定义实际个数
    private int count;

    // 构造方法，定义数组大小
    public Array(int capacity) {
        this.data = new int[capacity];
        this.n = capacity;
        this.count = 0;
    }

    // 根据索引，获取数据中的元素
    public int find(int index){
        if (index < 0 || index >=count){
            return -1;
        }
        return data[index];
    }
    // 插入元素，仅支持头部插入和尾部插入
    public boolean insert(int index, int value){
        // 插入位置不合法
        if (index < 0 || index > count ){
            return false;
        }
        // 数据空间已满
        if (count == n) {
            return false;
        }
        // 位置合法，插入位置的后面元素后移一位
        for (int i = count; i >index; --i) {
            data[i] = data[i-1];
        }
        data[index] = value;
        ++count;
        return true;
    }

    // 删除索引位置的元素
    public boolean delete(int index){
        // 位置不合法
        if (index < 0 || index >= count ){
            return false;
        }
        // 位置合法，删除位置的后面元素前移一位
        for (int i = index + 1; i < count; i++) {
            data[i-1] = data[i];
        }
        // 重新分配数组空间
//        int[] arr = new int[count-1];
//        for (int i = 0; i < count - 1; i++) {
//            arr[i] = data[i];
//        }
//        this.data = arr;
        --count;
        return true;
    }

    // 打印
    public void printAll(){
        for (int i = 0; i < count; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }
}
