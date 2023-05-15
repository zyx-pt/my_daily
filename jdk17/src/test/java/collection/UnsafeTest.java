package collection;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author yxzheng
 * @date 2020/10/27 13:46
 */
public class UnsafeTest {

    private int i;
    private static sun.misc.Unsafe UNSAFE;
    private static long I_OFFSET;

    private String[] table = {"1", "2", "3"};

    static{
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe)field.get(null);
        }catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        tesGetStringArr();
//        testMultiThread();
    }

    public static void tesGetStringArr(){
        final UnsafeTest unsafeTest = new UnsafeTest();
        // 数组中存储对象的头大小
        int ns = UNSAFE.arrayIndexScale(String[].class);
        // 数组中第一个元素的下标位置
        int base = UNSAFE.arrayBaseOffset(String[].class);
        System.out.println(UNSAFE.getObject(unsafeTest.table, base+1*ns));

    }

    public static void testMultiThread(){
        final UnsafeTest unsafeTest = new UnsafeTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    unsafeTest.i++;

                    boolean b = UNSAFE.compareAndSwapInt(unsafeTest, I_OFFSET, unsafeTest.i, unsafeTest.i + 1);
                    if (b) {
                        System.out.println(UNSAFE.getIntVolatile(unsafeTest, I_OFFSET));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    unsafeTest.i++;

                    boolean b = UNSAFE.compareAndSwapInt(unsafeTest, I_OFFSET, unsafeTest.i, unsafeTest.i + 1);
                    if (b) {
                        System.out.println(UNSAFE.getIntVolatile(unsafeTest, I_OFFSET));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
