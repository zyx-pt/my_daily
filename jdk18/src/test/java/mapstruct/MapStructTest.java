package mapstruct;

import entity.Account;
import entity.Item;
import entity.Order;
import entity.Person;
import entity.Sku;
import entity.dto.PersonDTO;
import entity.dto.SkuDTO;
import entity.vo.OrderQueryParamVO;
import mapstruct.mapper.MapStructMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * <pre>
 * 描述：测试MapStruct
 * </pre>
 *
 * @author zhengyx
 * @email zhengyx@gillion.com.cn
 * @date 2020/9/11
 * @time 8:57
 * @description: TODO
 */
public class MapStructTest {

    @Test
    public void order2QueryParam() {
        Order order = new Order();
        order.setId(12345L);
        order.setOrderSn("orderSn");
        order.setOrderType(0);
        order.setReceiverKeyword("keyword");
        order.setSourceType(1);
        order.setStatus(2);
        OrderQueryParamVO orderQueryParam = MapStructMapper.INSTANCE.order2QueryParam(order);
        assertEquals(orderQueryParam.getOrderSn(), order.getOrderSn());
        assertEquals(orderQueryParam.getOrderType(), order.getOrderType());
        assertEquals(orderQueryParam.getReceiverKeyword(), order.getReceiverKeyword());
        assertEquals(orderQueryParam.getSourceType(), order.getSourceType());
        assertEquals(orderQueryParam.getStatus(), order.getStatus());
    }

    @Test
    public void person2DTO(){
        Person person = new Person(1L,"zyx","zyx.me@gmail.com",new Date(),new Account("zyx",18));
        PersonDTO personDTO = MapStructMapper.INSTANCE.person2DTO(person);
        assertNotNull(personDTO);
        assertEquals(personDTO.getId(), person.getId());
        assertEquals(personDTO.getName(), person.getName());
        assertEquals(personDTO.getBirth(), person.getBirthday());
        String format = DateFormatUtils.format(personDTO.getBirth(), "yyyy-MM-dd HH:mm:ss");
        assertEquals(personDTO.getBirthDateFormat(),format);
        assertEquals(personDTO.getBirthExpressionFormat(),format);

        List<Person> people = new ArrayList<>();
        people.add(person);
        List<PersonDTO> personDTOs = MapStructMapper.INSTANCE.person2DTO(people);
        assertNotNull(personDTOs);

        // 如果已经有了接收对象，更新目标对象
        Person person2 = new Person(1L,"zhige","zhige.me@gmail.com",new Date(),new Account("zhige",18));
        PersonDTO personDTO2 = MapStructMapper.INSTANCE.person2DTO(person2);
        assertEquals("zhige", personDTO2.getName());
        person2.setName("xiaozhi");
        MapStructMapper.INSTANCE.update(person2, personDTO2);
        assertEquals("xiaozhi", personDTO2.getName());
    }

    @Test
    public void testItemSku2SkuDTO() {
        Item item = new Item(1L, "iPhone X");
        Sku sku = new Sku(2L, "phone12345", 1000000);
        SkuDTO skuDTO = MapStructMapper.INSTANCE.itemSku2SkuDTO(item, sku);
        assertNotNull(skuDTO);
        assertEquals(skuDTO.getSkuId(),sku.getId());
        assertEquals(skuDTO.getSkuCode(),sku.getCode());
        assertEquals(skuDTO.getSkuPrice(),sku.getPrice());
        assertEquals(skuDTO.getItemId(),item.getId());
        assertEquals(skuDTO.getItemName(),item.getTitle());

        assertTrue(MapStructMapper.INSTANCE.convert2Bool(1));
        assertEquals((int)MapStructMapper.INSTANCE.convert2Int(true),1);
    }

}
