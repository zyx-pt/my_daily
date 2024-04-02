package utiltest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.StopWatch;
import com.google.common.collect.Lists;
import entity.Order;
import entity.Sku;
import entity.vo.OrderQueryParamVO;
import mapstruct.mapper.MapStructMapper;
import net.sf.cglib.beans.BeanCopier;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Description: Bean拷贝工具性能比较：mapstruct > cglib > spring > hutool > apache
 * 说明：apache|hutool|spring基于java反射;
 *      cglib基于Cglib代理[基于字节码文件的操作,代码运行期间动态执行];
 *      Mapstruct在编译期间就生成了 Bean 属性复制的代码，运行期间就无需使用反射或者字节码技术，所以具有很高的性能
 * @Author: zhengyongxian
 * @Date: 2024/4/2 16:12
 */
public class BeanUtilCompareTest {

    @Test
    public void test() throws Exception {
        autoCheck(OrderQueryParamVO.class, 10000);
        autoCheck(OrderQueryParamVO.class, 10000_0);
        autoCheck(OrderQueryParamVO.class, 50000_0);
        autoCheck(OrderQueryParamVO.class, 10000_00);
    }

    private <T> void autoCheck(Class<T> target, int size) {
        StopWatch stopWatch = new StopWatch();
        runCopier(stopWatch, "copyByApache", size, (s) -> copyByApache(s, target), this::genOrder);
        runCopier(stopWatch, "copyBySpring", size, (s) -> copyBySpring(s, target), this::genOrder);
        runCopier(stopWatch, "copyByCglib", size, (s) -> copyByCglib(s, target), this::genOrder);
        runCopier(stopWatch, "copyByHutool", size, (s) -> copyByHutool(s, target), this::genOrder);
        runCopier(stopWatch, "copyByMapStruct", size, (s) -> copyByMapStruct((Order) s), this::genOrder);
        System.out.println((size / 10000) + "w -------- cost: " + stopWatch.prettyPrint());
    }

    private <T, R> void runCopier(StopWatch stopWatch, String key, int size, Function<T, R> func, Supplier<T> genT) {
        stopWatch.start(key);
        for (int i = 0; i < size; i++) {
            func.apply(genT.get());
        }
        stopWatch.stop();
    }

    public Order genOrder() {
        Random random = new Random();
        Order order = new Order();
        order.setId(random.nextLong());
        order.setOrderSn("orderSn" + random.nextInt());
        order.setOrderType(0);
        order.setReceiverKeyword("keyword" + random.nextInt());
        order.setSourceType(1);
        order.setStatus(2);
        order.setDetailIds(Lists.newArrayList(random.nextLong(), random.nextLong(), random.nextLong()));
        order.setSkuList(Lists.newArrayList(
                new Sku(1L, "sku1",10),
                new Sku(2L, "sku2",20),
                new Sku(3L, "sku3",30)
        ));
        return order;
    }


    public OrderQueryParamVO copyByMapStruct(Order orderSource1) {
        return MapStructMapper.INSTANCE.order2QueryParam(orderSource1);
    }

    public <K, T> T copyByCglib(K source, Class<T> target) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target, false);
        T res;
        try {
            res = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        copier.copy(source, res, null);
        return res;
    }

    public <K, T> T copyBySpring(K source, Class<T> target) {
        T res;
        try {
            res = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        org.springframework.beans.BeanUtils.copyProperties(source, res);
        return res;
    }

    public <K, T> T copyByHutool(K source, Class<T> target) {
        return BeanUtil.toBean(source, target);
    }

    public <K, T> T copyByApache(K source, Class<T> target) {
        T res;
        try {
            res = target.newInstance();
            // 注意： 与其他的正好相反， 第一个参数为target，第二个参数为source
            org.apache.commons.beanutils.BeanUtils.copyProperties(res, source);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
