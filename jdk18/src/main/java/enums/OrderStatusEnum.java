package enums;

/**
 * @Description:
 * @ClassName enums.OrderStatusEnum
 * @Author yxzheng
 * @Date 2021/7/1 20:55
 */
public enum OrderStatusEnum {
    PAID("0", "已支付"),
    UNPAID("1", "未支付"),
    ;
    private String key;
    private String value;

    OrderStatusEnum(String key, String value) {
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

    public static String getByKey(String key) {
        for (OrderStatusEnum item : values()) {
            if (item.getKey().equals(key)) return item.getValue();
        }
        return null;
    }

    public static String getByValue(String value) {
        for (OrderStatusEnum item : values()) {
            if (item.getValue().equals(value)) return item.getKey();
        }
        return null;
    }
}
