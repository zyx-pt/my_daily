package spring.mailsend;

import constant.PropertiesConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @Author zhengyongxian
 * @Date 2020/5/14
 */
public class MailSendUtil {

    private static final JavaMailSender defaultMailSender = createDefaultMailSent();
    private static final JavaMailSender specifyMailSender = createSpecifyMailSent();

    /**
     * 创建默认的JavaMailSender
     * @return
     */
    private static JavaMailSender createDefaultMailSent() {
        System.out.println("创建默认的邮件发送");
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(PropertiesConstant.MAIL_SMTP_HOST);
        sender.setPort(PropertiesConstant.MAIL_SMTP_PORT);
        sender.setUsername(PropertiesConstant.MAIL_SMTP_USERNAME);
        sender.setPassword(PropertiesConstant.MAIL_SMTP_PASSWORD);
        sender.setDefaultEncoding(PropertiesConstant.MAIL_SMTP_DEFAULTENCODING);

        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", PropertiesConstant.MAIL_SMTP_TIMEOUT);
        p.setProperty("mail.smtp.auth", PropertiesConstant.MAIL_SMTP_AUTH);
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 创建指定的JavaMailSender
     * @return
     */
    private static JavaMailSender createSpecifyMailSent(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(MailSendEnum.HOST.getValue());
        sender.setPort(Integer.parseInt(MailSendEnum.PORT.getValue()));
        sender.setUsername(MailSendEnum.USERNAME.getValue());
        sender.setPassword(MailSendEnum.PASSWORD.getValue());
        sender.setDefaultEncoding(MailSendEnum.DEFAULT_ENCODING.getValue());

        Properties p = new Properties();
        p.put("mail.smtp.timeout", Integer.valueOf(MailSendEnum.TIMEOUT.getValue()));
        p.put("mail.smtp.auth", MailSendEnum.AUTH.getValue());
//        p.put("mail.smtp.starttls.enable", MailSendEnum.STARTTLS_ENABLE.getValue());
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 用于创建自定义的邮件发送者
     * @param mailSenderProperties
     * @return
     */
    public static JavaMailSender createMailSender(MailSenderProperties mailSenderProperties){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(mailSenderProperties.getHost());
        sender.setPort(mailSenderProperties.getPort());
        sender.setUsername(mailSenderProperties.getUsername());
        sender.setPassword(mailSenderProperties.getPassword());
        sender.setDefaultEncoding("UTF-8");

        Properties p = new Properties();
        p.put("mail.smtp.timeout", 5000);
        p.put("mail.smtp.auth", true);
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 使用指定发件人发送邮件
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void specifySendMail(MailSendParameters mailSendParameters) throws MessagingException {
        mailSendParameters.setSenderName(MailSendEnum.SENDER_NAME.getValue());
        mailSendParameters.setSenderAddress(MailSendEnum.USERNAME.getValue());
        sendMail(mailSendParameters, specifyMailSender);
    }

    /**
     * 使用默认发件人发送邮件
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void defaultSendMail(MailSendParameters mailSendParameters) throws MessagingException {
        mailSendParameters.setSenderName(PropertiesConstant.MAIL_SEND_NAME);
        mailSendParameters.setSenderAddress(PropertiesConstant.MAIL_SMTP_USERNAME);
        sendMail(mailSendParameters, defaultMailSender);
    }

    /**
     * 发送邮件
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void sendMail(MailSendParameters mailSendParameters, JavaMailSender mailSender) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        // 设置邮件主题
        messageHelper.setSubject(mailSendParameters.getSubject());
        // 设置邮件发送者
        messageHelper.setFrom(new InternetAddress(mailSendParameters.getSenderName() + "<" + mailSendParameters.getSenderAddress() + ">"));
        // 设置邮件接收者
        messageHelper.setTo(mailSendParameters.getReceivers().toArray(new String[0]));
        // 设置邮件内容
        messageHelper.setText(mailSendParameters.getText(), true);
        // 设置邮件抄送者
        if (mailSendParameters.getCc() != null) {
            messageHelper.setCc(mailSendParameters.getCc().toArray(new String[0]));
        }
        // 设置内容中的静态资源，如图片
        if (mailSendParameters.getStaticResources() != null) {
            Map<String, String> staticResources = mailSendParameters.getStaticResources();
            staticResources.forEach((k,v)->{
                try {
                    messageHelper.addInline(k,new FileSystemResource(new File(v)));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        // 设置邮件附件
        if (CollectionUtils.isNotEmpty(mailSendParameters.getAttachmentPaths())) {
            List<String> filePaths = mailSendParameters.getAttachmentPaths();
            for (String filePath : filePaths) {
                FileSystemResource file=new FileSystemResource(new File(filePath));
                String fileName=filePath.substring(filePath.lastIndexOf(File.separator));
                messageHelper.addAttachment(fileName,file);
            }
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 读取freemarker模板的方法
     */
    public static String getTemplateText(MailSendParameters mailSendParameters) {
        String result = "";
        try {
            //freemarker包
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            //设置模板加载文件夹
            configuration.setDirectoryForTemplateLoading(new File(ResourceUtils.getURL("classpath:").getPath() + "template"));
            Template template = configuration.getTemplate(mailSendParameters.getTemplateName());

            // 通过map传递动态数据
            Map<String, String> templateParam = mailSendParameters.getTemplateParam();
            // 解析模板文件
            result = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateParam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
