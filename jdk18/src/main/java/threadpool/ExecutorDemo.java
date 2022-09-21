package threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description: Executor框架示例
 *
 * @ClassName threadpool.ExecutorDemo
 * @Author zhengyongxian
 * @Date 2022/9/16 17:25
 */
public class ExecutorDemo {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {
        new ExecutorDemo(). testMonitorThread();
//        new ExecutorDemo().threadPoolExecutorDemo1();
//        System.out.println();
//        new ExecutorDemo().threadPoolExecutorDemo2();
//        System.out.println();
//        new ExecutorDemo().scheduledThreadPoolExecutorDemo();
    }

    /** 测试检测线程池运行状态 */
    public void testMonitorThread(){

        MyRejectedExecutionHandler rejectionHandler = new MyRejectedExecutionHandler();
        // 利用 guava 的 ThreadFactoryBuilder给线程池命名设置线程池名称前缀
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Test ThreadPool-%d")
                .setDaemon(true).build();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2), nameThreadFactory, rejectionHandler);

        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        for(int i=0; i<10; i++){
            executorPool.execute(new MyRunnable("cmd"+i));
        }
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorPool.shutdown();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        monitor.shutdown();
    }
    /** 自定义拒绝策略 */
    public class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r.toString() + " is rejected");
        }

    }
    /** 自定义检测线程池Runnable */
    public class MyMonitorThread implements Runnable {
        private ThreadPoolExecutor executor;
        private int seconds;
        private boolean run=true;

        public MyMonitorThread(ThreadPoolExecutor executor, int delay) {
            this.executor = executor;
            this.seconds=delay;
        }
        public void shutdown(){
            this.run=false;
        }
        @Override
        public void run() {
            while(run){
                System.out.println(
                        String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                                this.executor.getPoolSize(),
                                this.executor.getCorePoolSize(),
                                this.executor.getActiveCount(),
                                this.executor.getCompletedTaskCount(),
                                this.executor.getTaskCount(),
                                this.executor.isShutdown(),
                                this.executor.isTerminated()));
                try {
                    Thread.sleep(seconds*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 用给定的初始参数创建一个新的ThreadPoolExecutor。
     * 饱和策略
     * ThreadPoolExecutor.AbortPolicy ：抛出 RejectedExecutionException来拒绝新任务的处理，默认。
     * ThreadPoolExecutor.CallerRunsPolicy ：调用执行自己的线程运行任务，也就是直接在调用execute方法的线程中运行(run)被拒绝的任务，如果执行程序已关闭，则会丢弃该任务。
     *  因此这种策略会降低对于新任务提交速度，影响程序的整体性能。如果您的应用程序可以承受此延迟并且你要求任何一个任务请求都要被执行的话，你可以选择这个策略。
     * ThreadPoolExecutor.DiscardPolicy ：不处理新任务，直接丢弃掉。
     * ThreadPoolExecutor.DiscardOldestPolicy ： 此策略将丢弃最早的未处理的任务请求
     */
    public void ThreadPoolExecutor(int corePoolSize,//线程池的核心线程数量（最小可以同时运行的线程数量）
                                   int maximumPoolSize,//线程池的最大线程数（当队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数）
                                   long keepAliveTime,//当线程数大于核心线程数时，多余的空闲线程存活的最长时间
                                   TimeUnit unit,//时间单位
                                   BlockingQueue<Runnable> workQueue,//任务队列，用来储存等待执行任务的队列(达到核心线程数，新任务就会被存放在队列中)
                                   ThreadFactory threadFactory,//线程工厂，用来创建线程，一般默认即可
                                   RejectedExecutionHandler handler//饱和策略，达到最大线程数量并且队列也已经被放满了任务时
    ) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
//        this.corePoolSize = corePoolSize;
//        this.maximumPoolSize = maximumPoolSize;
//        this.workQueue = workQueue;
//        this.keepAliveTime = unit.toNanos(keepAliveTime);
//        this.threadFactory = threadFactory;
//        this.handler = handler;
    }

    /**
     * Runnable+ThreadPoolExecutor示例代码
     * 说明：
     * Runnable 接口不会返回结果或抛出检查异常
     * execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否
     */
    public void threadPoolExecutorDemo1(){
        //通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            executor.execute(new MyRunnable(String.valueOf(i)));
        }
        //终止线程池
        executor.shutdown();
        // 每隔一秒钟检查一次是否执行完毕（状态为 TERMINATED）
        while (true) {
            try {
                if (executor.awaitTermination(1, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread is still executing...");
        }
        // awaitTermination可以用isTerminated替换
//        while (!executor.isTerminated()) {}
        long end = System.currentTimeMillis();
        System.out.println("Finished all threads,It takes "+(end - start)+"ms in total.");
        System.out.println("");
    }

    public class MyRunnable implements Runnable {

        private String command;

        public MyRunnable(String s) {
            this.command = s;
        }

        @Override
        public void run() {
            System.out.printf("%s %s Start. Command = %s%n", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"), Thread.currentThread().getName(), command);
            processCommand();
            System.out.printf("%s %s End. Command = %s%n", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"), Thread.currentThread().getName(), command);
        }

        private void processCommand() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return this.command;
        }
    }

    /**
     * Callable+ThreadPoolExecutor示例代码
     * 说明：
     * Callable 接口会返回结果或抛出检查异常
     * submit()方法用于提交需要返回值的任务。线程池会返回一个 Future 类型的对象，通过这个 Future 对象可以判断任务是否执行成功，
     * 并且可以通过 Future 的 get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，
     * 而使用 get（long timeout，TimeUnit unit）方法则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。
     */
    public void threadPoolExecutorDemo2(){
        //通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<String> future = executor.submit(new MyCallable());
            futureList.add(future);
        }
        for (Future<String> stringFuture : futureList) {
            try {
                System.out.println(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss") + "  " + stringFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //终止线程池
        executor.shutdown();
    }

    public class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            //返回执行当前 Callable 的线程名字
            return Thread.currentThread().getName();
        }
    }

    /** FixedThreadPool: 可重用固定线程数的线程池
     *  SingleThreadExecutor: 只有一个线程的线程池
     *  不推荐使用: SingleThreadExecutor、FixedThreadPool使用无界队列 LinkedBlockingQueue作为线程池的工作队列（队列的容量为 Integer.MAX_VALUE），可能导致 OOM（内存溢出）
     * */
    public static ExecutorService newFixedThreadPool(int nThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        return executorService;
        /***
         * 1. 如果当前运行的线程数小于 corePoolSize， 如果再来新任务的话，就创建新的线程来执行任务；
         * 2. 当前运行的线程数等于 corePoolSize 后， 如果再来新任务的话，会将任务加入 LinkedBlockingQueue；
         * 3. 线程池中的线程执行完手头的任务后，会在循环中反复从 LinkedBlockingQueue 中获取任务来执行；
         */
//        return new ThreadPoolExecutor(nThreads, nThreads,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>());
    }

    public static ExecutorService newSingleThreadExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService;
//        return new FinalizableDelegatedExectorService
//                (new ThreadPoolExecutor(1, 1,
//                        0L, TimeUnit.MILLISECONDS,
//                        new LinkedBlockingQueue<Runnable>()));
    }

    /** CachedThreadPool: 根据需要创建新线程的线程池
     * 不推荐使用: 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致 OOM
     * */
    public static ExecutorService newCachedThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        return executorService;
//        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>());
    }
    /** ScheduledThreadPoolExecutor:  主要用来在给定的延迟后运行任务，或者定期执行任务
     * */
    public static ExecutorService scheduledThreadPoolExecutor(int corePoolSize) {
        ExecutorService executorService = Executors.newScheduledThreadPool(corePoolSize);
        return executorService;
//        return new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
//              new DelayedWorkQueue());
    }
    /** 1. 执行ScheduledThreadPoolExecutor的scheduleWithFixedDelay()方法，
     *     会向 ScheduledThreadPoolExecutor 的 DelayQueue 添加一个实现了 RunnableScheduledFuture 接口的 ScheduledFutureTask
     * 2. 线程池中的线程从 DelayQueue 中获取 ScheduledFutureTask，然后执行任务
     * */
    public void scheduledThreadPoolExecutorDemo() {
        Runnable runnableDelayedTask1 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" is Running Delayed Task1");
        };
        Runnable runnableDelayedTask2 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" is Running Delayed Task2");
        };

        Callable callableDelayedTask = () -> "GoodBye! See you at another invocation...";

        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);
        // 周期性调度。
        // period指的两个任务开始执行的时间间隔。第一次执行延期时间为initialDelay之后每隔period执行一次,不等待第一次执行完就开始计时. 1 2 3 4
        scheduledPool.scheduleAtFixedRate(runnableDelayedTask1, 1, 1, TimeUnit.SECONDS);
        // delay表示当前任务的结束执行时间到下个任务的开始执行时间。第一次执行延期时间为initialDelay,在第一次执行完之后延迟delay后开始下一次执行. 1 3
        scheduledPool.scheduleWithFixedDelay(runnableDelayedTask2, 1, 1, TimeUnit.SECONDS);

        // 指定延迟后运行。在5秒后执行callableDelayedTask，schedule传入的是Callable就是有返回值，传入Runnable就是无返回值
        ScheduledFuture sf = scheduledPool.schedule(callableDelayedTask, 5, TimeUnit.SECONDS);

        String value = null;
        try {
            value = String.valueOf(sf.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Callable returned "+value);

        scheduledPool.shutdown();

        System.out.println("Is ScheduledThreadPool shutting down? "+scheduledPool.isShutdown());
    }
}