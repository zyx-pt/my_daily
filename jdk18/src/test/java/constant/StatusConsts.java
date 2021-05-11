package constant;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * <pre>
 * 描述：状态值相关常量
 * </pre>
 *
 * @Author zhengyongxian
 * @Date 2020/10/10 16:15
 */

public class StatusConsts {

    private static Map<Class, Object> enumMap = new ConcurrentHashMap<>();

    /**
     * @Description: 根据条件获取枚举对象
     *
     * @Author: zhengyongxina
     * @Date: 2020/10/10 16:19
     * @param className
     * @param predicate
     * @return: java.util.Optional<T>
     */
    public static <T> Optional<T> getEnumObject(Class<T> className, Predicate<T> predicate) {
        if (!className.isEnum()) {
            return null;
        }
        Object obj = enumMap.get(className);
        T[] ts = null;
        if (obj == null) {
            ts = className.getEnumConstants();
            enumMap.put(className, ts);
        } else {
            ts = (T[]) obj;
        }
        return Arrays.stream(ts).filter(predicate).findAny();
    }

    public enum OrderStatusEnum{
        PAID("0", "已支付"),
        UNPAID("1", "未支付"),
        ;
        private String key;
        private String value;

        OrderStatusEnum (String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static String getByKey(String key){
            for (OrderStatusEnum item : values()) {
                if(item.getKey().equals(key)) return item.getValue();
            }
            return null;
        }

        public static String getByValue(String value){
            for (OrderStatusEnum item : values()) {
                if(item.getValue().equals(value)) return item.getKey();
            }
            return null;
        }
    }

    public enum PayTypeEnum {
        ALIPAY("0", "支付宝"),
        WECHAT("1", "微信"),
        OTHER("2", "其他"),
        ;
        private String key;
        private String value;

        PayTypeEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
