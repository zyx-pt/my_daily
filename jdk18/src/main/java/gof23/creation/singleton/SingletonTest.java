package gof23.creation.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * @Description: 反射和反序列化破坏单例模式
 *
 * @Author: zhengyongxina
 * @Date: 2020/7/19 19:04
 * @param
 * @return:
 */
public class SingletonTest {
	public static void main(String[] args) throws Exception {
		System.out.println("正常情况下可以实现单例");
		SingletonDemo6 s1 = SingletonDemo6.getInstance();
		SingletonDemo6 s2 = SingletonDemo6.getInstance();
		System.out.println(s1);
		System.out.println(s2);

//		System.out.println("测试没有加synchronized，多线程下的单例被破坏");
//		multiThreadTest();

		System.out.println("测试反射使单例被破坏");
		reflectionTest();

		System.out.println("测试通过反序列化的方式构造多个对象");
		serializationTest();
	}

	/**
	 * @Description: 多线程下的单例模式--如果没有加synchronized，会创建多个实例
	 *
	 * @Author: zhengyongxina
	 * @Date: 2020/7/19 19:08
	 * @param
	 * @return: void
	 */
	public static void multiThreadTest(){
		Runnable runnable = () -> System.out.println(SingletonDemo2.getInstanceNoSyn());
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
	}

	/**
	 * @Description: 通过反射的方式直接调用私有构造器，破坏单例
	 *
	 * @Author: zhengyongxina
	 * @Date: 2020/7/19 19:20
	 * @param
	 * @return: void
	 */
	public static void reflectionTest() throws Exception {
		Class<SingletonDemo6> clazz = (Class<SingletonDemo6>) Class.forName("gof23.creation.singleton.SingletonDemo6");
		Constructor<SingletonDemo6> c = clazz.getDeclaredConstructor((Class<?>) null);
		c.setAccessible(true);
		// 可通过对私有构造器添加限制
		SingletonDemo6 s3 = c.newInstance();
		SingletonDemo6 s4 = c.newInstance();
		System.out.println(s3);
		System.out.println(s4);

//		Class clazz2 = SingletonDemo6.class;
//		Constructor declaredConstructor = clazz.getDeclaredConstructor();
//		declaredConstructor.setAccessible(true);
//		SingletonDemo6 s5 = c.newInstance();
//		SingletonDemo6 s6 = c.newInstance();
//		System.out.println(s5);
//		System.out.println(s6);
	}

	/**
	 * @Description: 通过反序列化的方式构造多个对象
	 *
	 * @Author: zhengyongxina
	 * @Date: 2020/7/19 19:28
	 * @param
	 * @return: void
	 */
	public static void serializationTest() throws Exception{
		SingletonDemo6 s1 = SingletonDemo6.getInstance();
		System.out.println(s1);
		FileOutputStream fos = new FileOutputStream("d:/a.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(s1);
		oos.close();
		fos.close();

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("d:/a.txt"));
		// 会自动调用readResolve，可通过定义readResolve()直接返回此方法指定的对象
		SingletonDemo6 s2 = (SingletonDemo6) ois.readObject();
		ois.close();
		System.out.println(s2);
	}
}
