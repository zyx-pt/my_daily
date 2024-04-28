package threadpool;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import entity.dto.AnalysisDemoDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 数据分析使用CompletableFuture
 * 1异步按分组执行
 * 2.异步按维度查询不同表最后整合结果
 * 3.异步查询后整合结果
 */
public class AsyncDataAnalysisDemo {
    public static void main(String[] args) {
        asyncPerformByGroup();
//        asyncQueryList();
//        asyncGroupQueryThenUnionSet();
    }

    /** 异步按分组执行 */
    public static List<String> asyncPerformByGroup() {
        // 模拟要查询的数据
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            data.add("Data " + i);
        }

        // 创建CompletableFuture列表，用于存储每个查询的结果
        List<CompletableFuture<List<String>>> futures = new ArrayList<>();

        // 每个CompletableFuture负责查询一部分数据
        int batchSize = 20;
        List<List<String>> splitData = CollUtil.split(data, batchSize);
        for (List<String> subList : splitData) {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> performQuery(subList));
            futures.add(future);
        }
        /*
        for (int i = 0; i < data.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, data.size());
            List<String> subList = data.subList(i, endIndex);
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> performQuery(subList));
            futures.add(future);
        }
        */

        // 使用CompletableFuture的allOf方法等待所有查询完成
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // 当所有查询完成时，对所有结果进行合并
        CompletableFuture<List<String>> mergedResult = allFutures.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );
        /** 使用工具类简化调用 */
//        CompletableFuture<List<String>> mergedResult = FutureUtils.sequenceList(futures);

        // 阻塞等待合并结果
        List<String> result = mergedResult.join();

        // 输出合并后的结果
        System.out.println("==============Async Perform Merged Result:==============");
        result.forEach(System.out::println);

        return result;
    }

    private static List<String> performQuery(List<String> data) {
        // 在这里进行查询操作，返回查询结果

        // 模拟查询耗时
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 返回查询结果
        List<String> result = new ArrayList<>();
        for (String item : data) {
            result.add("Result for " + item);
        }
        return result;
    }

    /** 异步按维度查询不同表最后整合结果 */
    public static List<AnalysisDemoDTO> asyncGroupQueryThenUnionSet() {
        CompletableFuture<List<AnalysisDemoDTO>> cf1 = CompletableFuture.supplyAsync(() -> queryRyzsGroupByAab001());
        CompletableFuture<List<AnalysisDemoDTO>> cf2 = CompletableFuture.supplyAsync(() -> queryCbzsGroupByAab001());
        CompletableFuture<List<AnalysisDemoDTO>> cf3 = CompletableFuture.supplyAsync(() -> queryDdzsGroupByAab001());

        CompletableFuture<List<AnalysisDemoDTO>> mergedCf = CompletableFuture.allOf(cf1, cf2, cf3).thenApply(v -> {
            // 查询结果
            List<AnalysisDemoDTO> ryzs00 = cf1.join();
            List<AnalysisDemoDTO> cbzs00 = cf2.join();
            List<AnalysisDemoDTO> ddzs00 = cf3.join();
            Collection<AnalysisDemoDTO> values = Stream.of(ryzs00, cbzs00, ddzs00)
                    .flatMap(List::stream)
                    .collect(Collectors.toMap(AnalysisDemoDTO::getAab001, Function.identity(), (result, data) -> {
                        Optional.ofNullable(data.getRyzs00()).ifPresent(result::setRyzs00);
                        Optional.ofNullable(data.getCbzs00()).ifPresent(result::setCbzs00);
                        Optional.ofNullable(data.getDdzs00()).ifPresent(result::setDdzs00);
                        return result;
                    })).values();
            return new ArrayList<>(values);
        });

        List<AnalysisDemoDTO> result = mergedCf.join();
        result.forEach(System.out::println);
        return result;
    }

    private static List<AnalysisDemoDTO> queryRyzsGroupByAab001() {
        // 模拟查询耗时
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AnalysisDemoDTO dto1 = new AnalysisDemoDTO();
        dto1.setAab001("A001");
        dto1.setRyzs00(101L);
        AnalysisDemoDTO dto2 = new AnalysisDemoDTO();
        dto2.setAab001("A002");
        dto2.setRyzs00(102L);
        AnalysisDemoDTO dto3 = new AnalysisDemoDTO();
        dto3.setAab001("A003");
        dto3.setRyzs00(103L);
        return Lists.newArrayList(dto1, dto2, dto3);
    }

    private static List<AnalysisDemoDTO> queryCbzsGroupByAab001() {
        // 模拟查询耗时
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AnalysisDemoDTO dto1 = new AnalysisDemoDTO();
        dto1.setAab001("A001");
        dto1.setCbzs00(101L);
        AnalysisDemoDTO dto2 = new AnalysisDemoDTO();
        dto2.setAab001("A002");
        dto2.setCbzs00(102L);
        AnalysisDemoDTO dto3 = new AnalysisDemoDTO();
        dto3.setAab001("A003");
        dto3.setCbzs00(103L);
        return Lists.newArrayList(dto1, dto2, dto3);
    }

    private static List<AnalysisDemoDTO> queryDdzsGroupByAab001() {
        // 模拟查询耗时
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AnalysisDemoDTO dto1 = new AnalysisDemoDTO();
        dto1.setAab001("A001");
        dto1.setDdzs00(1001L);
        AnalysisDemoDTO dto2 = new AnalysisDemoDTO();
        dto2.setAab001("A002");
        dto2.setDdzs00(1002L);
        AnalysisDemoDTO dto3 = new AnalysisDemoDTO();
        dto3.setAab001("A003");
        dto3.setDdzs00(1003L);
        return Lists.newArrayList(dto1, dto2, dto3);
    }

   /** 异步查询后整合结果 */
    public static List<String> asyncQueryList() throws RuntimeException {
        CompletableFuture<List<String>> cf1 = CompletableFuture.supplyAsync(() -> queryByCondition("A"));
        CompletableFuture<List<String>> cf2 = CompletableFuture.supplyAsync(() -> queryByCondition("B"));
        CompletableFuture<List<String>> cf3 = CompletableFuture.supplyAsync(() -> queryByCondition("C"));

        CompletableFuture<List<String>> mergedResult = CompletableFuture.allOf(cf1, cf2, cf3).thenApply(v -> {
            List<String> list1 = cf1.join();
            List<String> list2 = cf2.join();
            List<String> list3 = cf3.join();
            List<String> collect = Stream.of(list1, list2, list3).flatMap(Collection::stream).collect(Collectors.toList());
            return collect;
        });
        /** 使用工具类简化调用 */
//        CompletableFuture<List<String>> mergedResult = FutureUtils.sequenceList(Lists.newArrayList(cf1, cf2, cf3));
        List<String> result = mergedResult.join();
        System.out.println("==============Async Query Merged Result:==============");
        result.forEach(System.out::println);
        return result;
    }

    private static List<String> queryByCondition(String type) {
        // 模拟查询耗时
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            data.add(String.format("Type[%s]Data[%s]", type, i));
        }
        return data;
    }
}
