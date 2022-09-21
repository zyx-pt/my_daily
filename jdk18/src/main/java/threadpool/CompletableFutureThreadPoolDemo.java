package threadpool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @Description: CompletableFuture与线程池的使用
 */
public class CompletableFutureThreadPoolDemo {
    public static void main(String[] args) {
        supplyAsync();System.out.println();
        supplyAsyncThenJoin();System.out.println();
        allOf();System.out.println();
        anyOf();System.out.println();
        thenApplyAsync();System.out.println();
    }

    /** supplyAsync 异步后去结果 */
    public static void supplyAsync() {
        // 创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> list = Arrays.asList(1, 2, 3);
        for (Integer key : list) {
            // 提交异步任务
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "结果" + key;
            }, executorService).whenCompleteAsync((result, exception) -> {
                // 异步获取结果，替换成whenComplete则同步获取结果
                System.out.println(result);
            });
        }
        executorService.shutdown();
        // 由于whenCompleteAsync获取结果的方法是异步的，所以要阻塞当前线程才能输出结果
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** supplyAsync使用join同步获取结果 */
    public static void supplyAsyncThenJoin() {
        // 创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<String> results = list.stream()
                .map(key -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "结果" + key;
                }, executorService)).map(CompletableFuture::join).collect(Collectors.toList());
        executorService.shutdown();
        // 获取结果
        System.out.println(results);
    }

    /** allOf()方法使用示例 */
    public static void allOf(){
        // 1. 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> list = Arrays.asList(1, 2, 3);
        // CompletableFuture的allOf()方法的作用就是，等待所有任务处理完成
        // 2. 提交任务，并调用join()阻塞等待所有任务执行完成
        CompletableFuture.allOf(list.stream().map(key -> CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结果" + key);
        }, executorService)).toArray(CompletableFuture[]::new)).join();
        executorService.shutdown();
    }

    /** anyOf()方法使用示例 只要有其中一个任务处理完成就返回 */
    public static void anyOf(){
        // 1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> list = Arrays.asList(1, 2, 3);
        // 2. 提交任务
        CompletableFuture<Object> completableFuture =
                CompletableFuture.anyOf(list.stream().map(key -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "结果" + key;
                }, executorService)).toArray(CompletableFuture[]::new));
        executorService.shutdown();
        // 3. 获取结果
        System.out.println(completableFuture.join());
    }

    /** thenApplyAsync()方法使用示例  一个线程执行完成，交给另一个线程接着执行*/
    public static void thenApplyAsync(){
        // 1. 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 2. 提交任务，并调用join()阻塞等待任务执行完成
        String result2 = CompletableFuture.supplyAsync(() -> {
            // 睡眠一秒，模仿处理过程
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "结果1";
        }, executorService).thenApplyAsync(result1 -> {
            // 睡眠一秒，模仿处理过程
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result1 + "结果2";
        }, executorService).join();

        executorService.shutdown();
        // 3. 获取结果
        System.out.println(result2);
    }
}
