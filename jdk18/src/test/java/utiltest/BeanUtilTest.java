package utiltest;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import entity.Order;
import entity.Sku;
import entity.vo.OrderQueryParamVO;
import mapstruct.mapper.MapStructMapper;
import net.sf.cglib.beans.BeanCopier;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @Description: Bean拷贝工具
 * 拷贝方式：mapstruct|hutool深拷贝，cglib|spring|apache浅拷贝
 *      例如：Bean中的List对象拷贝后，使用的是同一个引用对象，修改其中个一个list，另一个中的list也会被修改
 * @Author: zhengyongxian
 * @Date: 2024/4/2 10:53
 */
public class BeanUtilTest {
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

    /**
     * @Description: MapStruct 性能强悍，需要声明bean的转换接口，自动代码生成的方式来实现拷贝
     * 拷贝方式：第一层深拷贝，嵌套对象浅拷贝
     * 注意：添加新属性后，需要重新rebuild项目重新生成自动代码使其生效
     * @Author: zhengyongxian
     * @Date: 2024/4/2 15:04
     */
    @Test
    public void testMapStruct() {
        Order orderSource1 = genOrder();
        OrderQueryParamVO orderQueryParam = MapStructMapper.INSTANCE.order2QueryParam(orderSource1);
        System.out.println("orderSource1 : " + orderSource1);
        System.out.println("orderQueryParam : " + orderQueryParam);
    }

    /**
     * @Description: BeanCopier
     * 拷贝方式：浅拷贝
     * BeanCopier只拷贝名称和类型都相同的属性，无法实现null字段跳过， 每次使用都需要create
     * 直接引入spring-core或引入纯净cglib即可使用
     * @Author: zhengyongxian
     * @Date: 2024/4/2 11:02
     */
    @Test
    public void testBeanCopier() {
        Order orderSource1 = genOrder();
        Order orderTarget1 = copy1(orderSource1, Order.class);
        System.out.println("order source1 : " + orderSource1);
        System.out.println("order target1 : " + orderTarget1);
    }

    public <K, T> T copy1(K source, Class<T> target) {
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

    /**
     * @Description: Spring BeanUtils
     * 拷贝方式：浅拷贝
     * @Author: zhengyongxian
     * @Date: 2024/4/2 15:23
     */
    @Test
    public void testSpringBeanUtils() {
        Order orderSource1 = genOrder();
        OrderQueryParamVO orderQueryParam = new OrderQueryParamVO();
        OrderQueryParamVO orderQueryParamVO = copy2(orderSource1, OrderQueryParamVO.class);
        BeanUtils.copyProperties(orderSource1, orderQueryParam);
        System.out.println("orderSource1 : " + orderSource1);
        System.out.println("orderQueryParam : " + orderQueryParam);
        System.out.println("orderQueryParamVO : " + orderQueryParamVO);
    }

    public <K, T> T copy2(K source, Class<T> target) {
        T res;
        try {
            res = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, res);
        return res;
    }

    /**
     * @Description: Hutool BeanUtil
     * 拷贝方式：第一层深拷贝，嵌套对象浅拷贝
     * 使用注意点：
     * 1.同名属性，String类型复制到BigDecimal类型，默认赋值0
     * 2.属性类型为时间类型LocalDateTime不适用，直接报错
     * @Author: zhengyongxian
     * @Date: 2024/4/2 10:05
     */
    @Test
    public void testHutoolBeanUtil() {
        Order orderSource1 = genOrder();
        Order orderSource2 = genOrder();
        orderSource2.setOrderSn(null);
        OrderQueryParamVO orderTarget1 = new OrderQueryParamVO();
        System.out.println("order source1 : " + orderSource1);
        System.out.println("order source2 : " + orderSource2);

        // source -> target
        BeanUtil.copyProperties(orderSource1, orderTarget1);
        System.out.println("order target1 [source -> target]---------------: " + orderTarget1);

        // source + target class -> target
        OrderQueryParamVO orderTarget2 = BeanUtil.copyProperties(orderSource1, OrderQueryParamVO.class);
        // OrderQueryParamVO orderTarget2 = copy3(orderSource1, OrderQueryParamVO.class);
        System.out.println("order target2 [source + target class -> target]: " + orderTarget2);

        // source exists null but dont copy null -> target
        BeanUtils.copyProperties(orderSource2, orderTarget1, "orderSn");
        // copyPropertiesNoNull(orderSource2, orderTarget1);
        System.out.println("order target1, source exists null but dont copy: " + orderTarget1);

        // source exists null -> target
        BeanUtil.copyProperties(orderSource2, orderTarget1);
        System.out.println("order target1, source exists null--------------: " + orderTarget1);

        Map<String, Object> orderMap = BeanUtil.beanToMap(orderTarget1, false, true);
        System.out.println("order bean to map ignore null property---------: " + orderMap);

        System.out.println("========================================================");

        List<Order> listSource1 = new ArrayList<>();
        listSource1.add(genOrder());
        listSource1.add(genOrder());
        listSource1.add(genOrder());
        listSource1.add(genOrder());
        System.out.println("order list source1 : " + listSource1);

        List<OrderQueryParamVO> listTarget1 = BeanUtil.copyToList(listSource1, OrderQueryParamVO.class);
        System.out.println("order list target1 : " + listTarget1);

    }

    public <K, T> T copy3(K source, Class<T> target) {
        return BeanUtil.toBean(source, target);
    }


    public static void copyPropertiesNoNull(Object source, Object target) throws BeansException {
        String[] nullPropertyNames = getNullPropertyNames(source);
        //System.out.println(Arrays.toString(nullPropertyNames));
        BeanUtils.copyProperties(source, target, nullPropertyNames);
    }

    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * @Description: Apache BeanUtils 阿里规范中，明确说明了，不要使用它，性能较差不推荐使用
     * 拷贝方式：浅拷贝
     * @Author: zhengyongxian
     * @Date: 2024/4/2 15:27
     */
    @Test
    public void testApacheBeanUtils() throws InvocationTargetException, IllegalAccessException {
        Order orderSource1 = genOrder();
        OrderQueryParamVO orderQueryParam = new OrderQueryParamVO();
        OrderQueryParamVO orderQueryParamVO = copy4(orderSource1, OrderQueryParamVO.class);
        org.apache.commons.beanutils.BeanUtils.copyProperties(orderQueryParam, orderSource1);
        System.out.println("orderSource1 : " + orderSource1);
        System.out.println("orderQueryParam : " + orderQueryParam);
        System.out.println("orderQueryParamVO : " + orderQueryParamVO);
    }
    public <K, T> T copy4(K source, Class<T> target) {
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
