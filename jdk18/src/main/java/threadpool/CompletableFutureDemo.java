package threadpool;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.CompletableFuture;

/**
 * @Description: CompletableFuture常用API
 * 常用API说明:
 * 1.提交任务
 * supplyAsync
 * runAsync
 * 2.接力处理
 * thenRun thenRunAsync
 * thenAccept thenAcceptAsync
 * thenApply thenApplyAsync
 * handle handleAsync
 * applyToEither applyToEitherAsync
 * acceptEither acceptEitherAsync
 * runAfterEither runAfterEitherAsync
 * thenCombine thenCombineAsync
 * thenAcceptBoth thenAcceptBothAsync
 * 3.获取结果
 * join 阻塞等待，不会抛异常
 * get 阻塞等待，会抛异常
 * complete(T value) 不阻塞，如果任务已完成，返回处理结果。如果没完成，则返回传参value。
 * completeExceptionally(Throwable ex) 不阻塞，如果任务已完成，返回处理结果。如果没完成，抛异常。
 *
 * 带run的方法，无入参，无返回值。
 * 带accept的方法，有入参，无返回值。
 * 带supply的方法，无入参，有返回值。
 * 带apply的方法，有入参，有返回值。
 * 带handle的方法，有入参，有返回值，并且带异常处理。
 * 以Async结尾的方法，都是异步的，否则是同步的。
 * 以Either结尾的方法，只需完成任意一个。
 * 以Both/Combine结尾的方法，必须所有都完成。
 */
public class CompletableFutureDemo {
    public static void main(String[] args) {
        supplyThenHandle(); System.out.println();
        complete(); System.out.println();
        either(); System.out.println();
    }
    /** then、handle方法使用示例 */
    public static void supplyThenHandle(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->{
            System.out.println("1. 开始淘米");
            return "2. 淘米完成";
        }).thenApplyAsync(result ->{
            System.out.println(result);
            System.out.println("3. 开始煮饭");
            // 生成一个1~10的随机数
            if (RandomUtils.nextInt(1, 10) > 5) {
                throw new RuntimeException("4. 电饭煲坏了，煮不了");
            }
            return "4. 煮饭完成";
        }).handleAsync((result, exception) -> {
            if (exception != null) {
                System.out.println(exception.getMessage());
                return "5. 今天没饭吃";
            } else {
                System.out.println(result);
                return "5. 开始吃饭";
            }
        });

        try {
            String result = completableFuture.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** complete 方法使用示例 */
    public static void complete(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "饭做好了";
        });
        //
        //try {
        //    Thread.sleep(1L);
        //} catch (InterruptedException e) {
        //}

        completableFuture.complete("饭还没做好，我点外卖了");
        System.out.println(completableFuture.join());
    }

    /** either 方法使用示例 */
    public static void either(){
        CompletableFuture<String> meal = CompletableFuture.supplyAsync(() -> {
            return "饭做好了";
        });
        CompletableFuture<String> outMeal = CompletableFuture.supplyAsync(() -> {
            return "外卖到了";
        });

        // 饭先做好，就吃饭。外卖先到，就吃外卖。就是这么任性。
        CompletableFuture<String> completableFuture = meal.applyToEither(outMeal, myMeal -> {
            return myMeal;
        });

        System.out.println(completableFuture.join());
    }

}
