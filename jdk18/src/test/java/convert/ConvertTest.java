package convert;

import com.google.common.base.Preconditions;
import constant.OrderStatusEnum;
import org.junit.Test;

/**
 * 对传输对象的处理：对象拷贝
 * @Author zhengyongxian
 * @Date 2020/5/11
 */
public class ConvertTest {

    @Test
    public void test1(){
        // 只要是用于网络传输的对象，我们都认为他们可以当做是DTO对象
        // 对接的返回值以及入参也叫DTO对象
        // 对接前端发送的参数
        UserDTO userDTO = UserDTO.builder().userName("zyx").age(18).build();
        User user1 =  userDTO.convertToUser();
        System.out.println(user1);

        // 不应该直接返回User实体，因为如果这样的话，就暴露了太多实体相关的信息，
        // 这样的返回值是不安全的，所以我们更应该返回一个DTO对象
        User user2 = User.builder().userName("hehe").age(18).build();
        UserDTO result = userDTO.convertFor(user2);
        System.out.println(result);
        Preconditions.checkNotNull(null);
    }

    @Test
    public void testEnumConvert(){
        String orderStatus = EnumConvert.convertKeyToValue("ORDER_STATUS", "0");
        String orderStatus2 = OrderStatusEnum.getByKey("0");
        String orderStatusName = OrderStatusEnum.getByValue("已支付");
        System.out.println(orderStatus);
        System.out.println(orderStatus2);
        System.out.println(orderStatusName);

    }
}
