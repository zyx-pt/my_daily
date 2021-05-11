package gof23.creation.singleton;

/**
 * @Description: 测试静态内部类实现单例模式--推荐使用
 * 这种方式：线程安全，调用效率高，并且实现了延时加载！
 *
 * @Author: zhengyongxina
 * @Date: 2020/6/22 22:38
 */
public class SingletonDemo4 {

    private static class SingletonClassInstance {
    	private static final SingletonDemo4 instance = new SingletonDemo4();
    }
    
    private SingletonDemo4(){
    }
    
    /**方法没有同步，调用效率高！*/
    public static SingletonDemo4  getInstance(){
    	return SingletonClassInstance.instance;
    }
}