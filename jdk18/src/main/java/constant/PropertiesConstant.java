package constant;


import util.PropUtils;

/**
 * 配置类中对应的常量
 * @Author zhengyongxian
 * @Date 2020/5/14
 */
public class PropertiesConstant {
    protected static final String APPLICATION_PROPERTIES = "application.properties";
    protected static final PropUtils APPLICATION_UTIL = new PropUtils(APPLICATION_PROPERTIES);
    protected static final String MAIL_PROPERTIES = "mail.properties";
    protected static final PropUtils MAIL_UTIL = new PropUtils(MAIL_PROPERTIES);

    public static String MAIL_SMTP_HOST = MAIL_UTIL.get("mail.smtp.host");
    public static String MAIL_SMTP_DEFAULTENCODING = MAIL_UTIL.get("mail.smtp.defaultEncoding");
    public static String MAIL_SMTP_USERNAME = MAIL_UTIL.get("mail.smtp.username");
    public static String MAIL_SMTP_PASSWORD = MAIL_UTIL.get("mail.smtp.password");
    public static Integer MAIL_SMTP_PORT = Integer.valueOf(MAIL_UTIL.get("mail.smtp.port"));
    public static String MAIL_SMTP_AUTH = MAIL_UTIL.get("mail.smtp.auth");
    public static String MAIL_SMTP_DEBUG = MAIL_UTIL.get("mail.smtp.debug");
    public static String MAIL_SMTP_TIMEOUT = MAIL_UTIL.get("mail.smtp.timeout");
    public static String MAIL_SEND_NAME = MAIL_UTIL.get("mail.send.name");





}
