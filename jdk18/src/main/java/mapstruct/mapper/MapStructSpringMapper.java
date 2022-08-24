package mapstruct.mapper;

import entity.Item;
import entity.Order;
import entity.Person;
import entity.Sku;
import entity.dto.PersonDTO;
import entity.dto.SkuDTO;
import entity.vo.OrderQueryParamVO;
import org.mapstruct.*;

import java.util.List;

/**
 * <pre>
 * 描述：用于类之间的复制
 * </pre>
 *
 * @author zhengyx
 * @date 2020/9/11
 * @time 8:49
 * @description: 方法的入参对应要被转化的对象， 返回值对应转化后的对象
 */
@Mapper(componentModel="spring")// 是和常用的框架 Spring 结合
public interface MapStructSpringMapper {

    /**
     * @Description: 订单实体转换成查询参数的VO
     *
     * @Author: zhengyongxian
     * @Date: 2020/9/11 8:53
     * @param order
     * @return: entity.vo.OrderQueryParamVO
     */
    OrderQueryParamVO order2QueryParam(Order order);


    /**
     * @Description: 属性名不同的映射
     *
     * @Author: zhengyongxian
     * @Date: 2020/9/14 17:20
     * @param person
     * @return: entity.dto.PersonDTO
     */
    @Mappings({
            @Mapping(source = "birthday", target = "birth"),
            @Mapping(source = "birthday", target = "birthDateFormat", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "birthExpressionFormat", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(person.getBirthday(),\"yyyy-MM-dd HH:mm:ss\"))"),
            @Mapping(source = "account.age", target = "age"),
            @Mapping(target = "email", ignore = true)
    })
    PersonDTO person2DTO(Person person);

    List<PersonDTO> person2DTO(List<Person> people);

    // 比如在 PersonConverter 里面加入如下，@InheritConfiguration 用于继承刚才的配置
    @InheritConfiguration(name = "person2DTO")
    void update(Person person, @MappingTarget PersonDTO personDTO);



    /*-------- 多对一转换 -----------------------------------------*/
    @Mappings({
            @Mapping(source = "sku.id",target = "skuId"),
            @Mapping(source = "sku.code",target = "skuCode"),
            @Mapping(source = "sku.price",target = "skuPrice"),
            @Mapping(source = "item.id",target = "itemId"),
            @Mapping(source = "item.title",target = "itemName")
    })
    SkuDTO itemSku2SkuDTO(Item item, Sku sku);


    /*-------- 自定义方法 -----------------------------------------*/
    default Boolean convert2Bool(Integer value) {
        if (value == null || value < 1) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    default Integer convert2Int(Boolean value) {
        if (value == null) {
            return null;
        }
        if (Boolean.TRUE.equals(value)) {
            return 1;
        }
        return 0;
    }


}
