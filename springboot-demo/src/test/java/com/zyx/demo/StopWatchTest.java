package com.zyx.demo;

import org.junit.Test;
import org.springframework.util.StopWatch;

public class StopWatchTest {

    @Test
    public void  testStopWatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("<------------任务耗时统计------------>");
        stopWatch.start("Task1");
        System.out.println("当前任务名称：" + stopWatch.currentTaskName());
        Thread.sleep(1000);
        stopWatch.stop();
        stopWatch.start("Task2");
        System.out.println("当前任务名称：" + stopWatch.currentTaskName());
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        System.out.println(stopWatch.shortSummary());
        System.out.println("所有任务总耗时：" + stopWatch.getTotalTimeMillis());
        System.out.println("任务总数：" + stopWatch.getTaskCount());

    }
}
