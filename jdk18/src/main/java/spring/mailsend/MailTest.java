package spring.mailsend;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhengyongxian
 * @Date 2020/5/18
 */
public class MailTest {
    public static void main(String[] args) {
        List<String> receivers = new ArrayList<>(3);
        Map<String, String> templateText = new HashMap<>(16);
        receivers.add("zyx_pt@163.com");

        templateText.put("subject","zyx&hehe");
        templateText.put("time","2019-6-25");
        templateText.put("address","add");
        templateText.put("sponsor","xxx");
        templateText.put("description","xxxxxxxxxx");

        MailSendParameters mailSendParameters = MailSendParameters.builder()
                .receivers(receivers)
                .subject("zyx-测试发送邮件")
                .templateName("mail.ftl")
                .templateParam(templateText)
                .build();
        try {
            MailSendUtil.specifySendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
