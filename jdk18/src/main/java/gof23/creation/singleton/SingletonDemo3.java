package gof23.creation.singleton;

/**
 * @Description: 双重检查锁实现单例模式--不推荐使用
 *
 * @Author: zhengyongxina
 * @Date: 2020/6/22 22:38
 */
public class SingletonDemo3 {

    private static SingletonDemo3 instance = null;

    public static SingletonDemo3 getInstance() {
        if (instance == null) {
            SingletonDemo3 sc;
            synchronized (SingletonDemo3.class) {
                sc = instance;
                if (sc == null) {
                    synchronized (SingletonDemo3.class) {
                        if (sc == null) {
                            sc = new SingletonDemo3();
                        }
                    }
                    instance = sc;
                }
            }
        }
        return instance;
    }
}