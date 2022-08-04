package convert;

import constant.OrderStatusEnum;
import constant.PayTypeEnum;
import constant.StatusConsts;

import java.util.Optional;

/**
 * <pre>
 * 描述：根据类型对应枚举，由key获取value
 * </pre>
 *
 * @Author zhengyongxian
 * @Date 2020/10/10 16:25
 */

public class EnumConvert {

    /**
     * @Description: TODO
     *
     * @Author: zhengyongxina
     * @Date: 2020/10/10 16:46
     * @param enumType 枚举类型
     * @param key key值
     * @return: java.lang.String
     */
    public static String convertKeyToValue(String enumType, String key){
        String result = "";
        switch (enumType){
            case "ORDER_STATUS":
                result = getOrderStatusValue(key);
                break;
            case "PAY_TYPE":
                result = getPayTypeValue(key);
                break;
            default:
                break;
        }
        return result;
    }

    private static String getOrderStatusValue(String key) {
        Optional<OrderStatusEnum> m1 = StatusConsts.getEnumObject(OrderStatusEnum.class, e -> e.getKey().equals(key));
        return m1.isPresent() ? m1.get().getValue() : "";
    }

    private static String getPayTypeValue(String key) {
        Optional<PayTypeEnum> m1 = StatusConsts.getEnumObject(PayTypeEnum.class, e -> e.getKey().equals(key));
        return m1.isPresent() ? m1.get().getValue() : "";
    }

}
