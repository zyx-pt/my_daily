package gof23.creation.singleton;

/**
 * @Description: static静态代码块实现单例模式
 *
 * @Author: zhengyongxina
 * @Date: 2020/7/19 19:39
 * @return:
 */
public class SingletonDemo7 {

    private static SingletonDemo7 instance = null;

    private SingletonDemo7() {

    }

    static {
        instance = new SingletonDemo7();
    }

    public static SingletonDemo7 getInstance() {
        return instance;
    }

}