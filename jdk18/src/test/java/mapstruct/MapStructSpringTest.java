package mapstruct;

import entity.Account;
import entity.Item;
import entity.Order;
import entity.Person;
import entity.Sku;
import entity.dto.PersonDTO;
import entity.dto.SkuDTO;
import entity.vo.OrderQueryParamVO;
import mapstruct.mapper.MapStructSpringMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseTestConfiguration.class)// 由于都在test下BaseTestConfiguration必须和MapStructSpringTest所在位置一样
public class MapStructSpringTest {

    @Autowired 
    private MapStructSpringMapper mapStructSpringMapper;

    @Test
    public void order2QueryParam() {
        Order order = new Order();
        order.setId(12345L);
        order.setOrderSn("orderSn");
        order.setOrderType(0);
        order.setReceiverKeyword("keyword");
        order.setSourceType(1);
        order.setStatus(2);
        OrderQueryParamVO orderQueryParam = mapStructSpringMapper.order2QueryParam(order);
        assertEquals(orderQueryParam.getOrderSn(), order.getOrderSn());
        assertEquals(orderQueryParam.getOrderType(), order.getOrderType());
        assertEquals(orderQueryParam.getReceiverKeyword(), order.getReceiverKeyword());
        assertEquals(orderQueryParam.getSourceType(), order.getSourceType());
        assertEquals(orderQueryParam.getStatus(), order.getStatus());
    }

    @Test
    public void person2DTO(){
        Person person = new Person(1L,"zyx","zyx.me@gmail.com",new Date(),new Account("zyx",18));
        PersonDTO personDTO = mapStructSpringMapper.person2DTO(person);
        assertNotNull(personDTO);
        assertEquals(personDTO.getId(), person.getId());
        assertEquals(personDTO.getName(), person.getName());
        assertEquals(personDTO.getBirth(), person.getBirthday());
        String format = DateFormatUtils.format(personDTO.getBirth(), "yyyy-MM-dd HH:mm:ss");
        assertEquals(personDTO.getBirthDateFormat(),format);
        assertEquals(personDTO.getBirthExpressionFormat(),format);

        List<Person> people = new ArrayList<>();
        people.add(person);
        List<PersonDTO> personDTOs = mapStructSpringMapper.person2DTO(people);
        assertNotNull(personDTOs);

        // 如果已经有了接收对象，更新目标对象
        Person person2 = new Person(1L,"zhige","zhige.me@gmail.com",new Date(),new Account("zhige",18));
        PersonDTO personDTO2 = mapStructSpringMapper.person2DTO(person2);
        assertEquals("zhige", personDTO2.getName());
        person2.setName("xiaozhi");
        mapStructSpringMapper.update(person2, personDTO2);
        assertEquals("xiaozhi", personDTO2.getName());
    }

    @Test
    public void testItemSku2SkuDTO() {
        Item item = new Item(1L, "iPhone X");
        Sku sku = new Sku(2L, "phone12345", 1000000);
        SkuDTO skuDTO = mapStructSpringMapper.itemSku2SkuDTO(item, sku);
        assertNotNull(skuDTO);
        assertEquals(skuDTO.getSkuId(),sku.getId());
        assertEquals(skuDTO.getSkuCode(),sku.getCode());
        assertEquals(skuDTO.getSkuPrice(),sku.getPrice());
        assertEquals(skuDTO.getItemId(),item.getId());
        assertEquals(skuDTO.getItemName(),item.getTitle());

        assertTrue(mapStructSpringMapper.convert2Bool(1));
        assertEquals((int)mapStructSpringMapper.convert2Int(true),1);
    }

}
