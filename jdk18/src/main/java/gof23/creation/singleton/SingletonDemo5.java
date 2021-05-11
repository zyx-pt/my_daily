package gof23.creation.singleton;

/**
 * @Description: 枚举式实现单例模式(没有延时加载，可避免止反射和反序列化漏洞)
 * 				 反汇编查看枚举类的代码发现其继承自Enum类，并且静态代码块中初始化实例
 * @Author: zhengyongxina
 * @Date: 2020/6/22 22:40
 */
public enum SingletonDemo5 {
	
	/**这个枚举元素，本身就是单例对象！*/
	INSTANCE;
	
	/**添加自己需要的操作！*/
	public void singletonOperation(){

	}

	public static SingletonDemo5 getInstance(){
		return INSTANCE;
	}
}