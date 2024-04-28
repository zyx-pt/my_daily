package com.zyx.demo;

import cn.hutool.core.util.RandomUtil;
import com.zyx.demo.model.Dep;
import com.zyx.demo.service.DepService;
import com.zyx.demo.thread.MultiplyThreadTransactionManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@SpringBootTest
class MultiplyThreadTransactionTest {
    @Resource
    private DepService depService;
    @Resource
    private MultiplyThreadTransactionManager multiplyThreadTransactionManager;

    @Test
    void test() {
        saveDep();
    }

    private Dep saveDep() {
        Dep dep = new Dep();
        dep.setDepno(RandomUtil.randomInt(1, 10));
        dep.setDepname(RandomUtil.randomString(5));
        dep.setMemo(RandomUtil.randomString(20));
        boolean save = depService.save(dep);
        return dep;
    }

    @Test
    public void testRunAsync() {
        List<Runnable> tasks = new ArrayList<>();

        tasks.add(() -> {
            saveDep();
            throw new RuntimeException("我就要抛出异常!");
        });

        tasks.add(this::saveDep);

        multiplyThreadTransactionManager.runAsyncButWaitUntilAllDown(tasks, Executors.newCachedThreadPool());
    }

    @Test
    public void testSupplyAsync() {
        List<Supplier<Dep>> supplierList = new ArrayList<>();

        supplierList.add(() -> {
            Dep dep = saveDep();
//            throw new RuntimeException("我就要抛出异常!");
            return dep;
        });

        supplierList.add(this::saveDep);

        List<Dep> deps = multiplyThreadTransactionManager.supplyAsyncButWaitUntilAllDown(supplierList, Executors.newCachedThreadPool());
        deps.forEach(System.out::println);
    }

}
