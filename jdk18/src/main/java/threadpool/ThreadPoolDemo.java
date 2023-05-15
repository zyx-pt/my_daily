package threadpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description: 线程池使用示例
 * 优点：
 * ~降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
 * ~提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。
 * ~提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。
 * @ClassName threadpool.ThreadPoolDemo
 * @Author yxzheng
 * @Date 2022/9/15 15:27
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        commonTreadPool();System.out.println();
        commonTreadPoolCountDownLatch();System.out.println();
    }

    /** 一般的线程池处理任务  使用CompletableFuture优化：CompletableFutureThreadPoolDemo.supplyAsync() */
    public static void commonTreadPool() {
        // 创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Future<String>> futures = new ArrayList<>();
        for (Integer key : list) {
            // 提交任务
            Future<String> future = executorService.submit(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "结果" + key;
            });
            futures.add(future);
        }
        // 获取结果
        for (Future<String> future : futures) {
            String result = null;
            try {
                // future.get()方法，会阻塞当前线程，直到返回结果，降低性能
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(result);
        }
        executorService.shutdown();
    }

    /** 一般的线程池+CountDownLatch使用示例  使用CompletableFuture优化：CompletableFutureThreadPoolDemo.allOf()*/
    public static void commonTreadPoolCountDownLatch() {
        // 1. 创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Integer> list = Arrays.asList(1, 2, 3);
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (Integer key : list) {
            // 2. 提交任务
            executorService.execute(() -> {
                // 睡眠一秒，模仿处理过程
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("结果" + key);
                countDownLatch.countDown();
            });
        }
        // 3. 阻塞等待所有任务执行完成
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
