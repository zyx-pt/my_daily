package thread;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Description: 线程相关测试
 * @ClassName thread.ThreadTest
 * @Author yxzheng
 * @Date 2022/9/26 9:31
 */
public class ThreadTest {
    @Test
    public void printThreadPoolDesc(){
        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
        // 根据结果可以得出：一个 Java 程序的运行是 main 线程和多个其他线程同时运行。
        //[6] Monitor Ctrl-Break
        //[5] Attach Listener       添加事件
        //[4] Signal Dispatcher     分发处理给 JVM 信号的线程
        //[3] Finalizer             调用对象 finalize 方法的线程
        //[2] Reference Handler     清除 reference 线程
        //[1] main                  main 线程,程序入口
    }

    @Test
    public void testCommonDateFormat(){
        // 报错如：Exception in thread "Thread-3" java.lang.NumberFormatException: For input string: ""
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> ThreadLocalDemo.dateFormat1("2022-09-26"));
            thread.start();
        }
    }

    @Test
    public void testThreadLocalDateFormat(){
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> ThreadLocalDemo.dateFormat("2022-09-26"));
            thread.start();
        }
    }


}
