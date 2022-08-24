package enums;

/**
 * @Description:
 * @ClassName enums.PayTypeEnum
 * @Author zhengyongxian
 * @Date 2021/7/1 20:55
 */
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
