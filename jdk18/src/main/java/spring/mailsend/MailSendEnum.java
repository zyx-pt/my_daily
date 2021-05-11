package spring.mailsend;

/**
 * @Author zhengyongxian
 * @Date 2020/5/18
 */
public enum MailSendEnum {
    /**
     * 配置指定发送邮件者的配置
     */
    HOST("smtp.qq.com"),
    PORT("465"),
    SENDER_NAME("Yong"),
    USERNAME("1367245342@qq.com"),
    PASSWORD("rfwlxgrryctzijac"),
    DEFAULT_ENCODING("UTF-8"),
    TIMEOUT("5000"),
    AUTH("true"),
    STARTTLS_ENABLE("true");

    private String value;

    MailSendEnum(String value){ this.value = value;}

    public String getValue(){ return value; }
}
