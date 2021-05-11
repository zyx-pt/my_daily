package gof23.creation.singleton;

/**
 * @Description: 饿汉式
 *
 * @Author: zhengyongxina
 * @Date: 2020/6/22 22:37
 */
public class SingletonDemo2 {
	
	/**类初始化时，不初始化这个对象（延时加载，真正用的时候再创建）。*/
	private static SingletonDemo2 instance;  
	
	private SingletonDemo2(){ //私有化构造器
	}
	
	/** 方法同步，调用效率低！ */
	public static  synchronized SingletonDemo2  getInstance(){
		if(instance==null){
			instance = new SingletonDemo2();
		}
		return instance;
	}

	public static SingletonDemo2 getInstanceNoSyn(){
		if(instance==null){
			try {
				Thread.sleep(3*1000);
				instance = new SingletonDemo2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
}