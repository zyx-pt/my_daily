package utiltest;

import spring.mailsend.MailSendParameters;
import spring.mailsend.MailSendUtil;
import org.junit.Test;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhengyongxian
 * @Date 2020/5/15
 */
public class SpringMailSendTest {

    /**
     * 自定义邮件内容发送
     */
    @Test
    public void testDefaultSendMail(){
        List<String> receivers = new ArrayList<>(3);
        List<String> filePaths = new ArrayList<>(3);
        receivers.add("1367245342@qq.com");
        filePaths.add("E:\\test.txt");
        HashMap<String, String> staticResources = new HashMap<>();
        String text = "<html><body><p>测试发送带图片带附件的邮件<p><img src=\"cid:myImg\" ></body></html>";
        staticResources.put("myImg","E:\\test.JPG");
        MailSendParameters mailSendParameters = MailSendParameters.builder()
                .receivers(receivers)
                .subject("测试发送邮件")
                .text(text)
                .attachmentPaths(filePaths)
                .staticResources(staticResources)
                .build();
        try {
            MailSendUtil.defaultSendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 自定义邮件内容发送
     */
    @Test
    public void testSpecifySendMail(){
        List<String> receivers = new ArrayList<>(3);
        List<String> filePaths = new ArrayList<>(3);
        receivers.add("xxx@163.com");
        filePaths.add("E:\\test.txt");
        HashMap<String, String> staticResources = new HashMap<>();
        String text = "<html><body><p>测试发送带图片带附件的邮件<p><img src=\"cid:myImg\" ></body></html>";
        staticResources.put("myImg","E:\\test.JPG");
        MailSendParameters mailSendParameters = MailSendParameters.builder()
                .receivers(receivers)
                .subject("测试发送邮件")
                .text(text)
                .attachmentPaths(filePaths)
                .staticResources(staticResources)
                .build();
        try {
            MailSendUtil.specifySendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据邮件模板发送
     */
    @Test
    public void testSendMailByTemplate(){
        List<String> receivers = new ArrayList<>(3);
        Map<String, String> templateParam = new HashMap<>(16);
        receivers.add("zyx_pt@163.com");

        templateParam.put("subject","zyx&hehe");
        templateParam.put("time","2019-6-25");
        templateParam.put("address","add");
        templateParam.put("sponsor","xxx");
        templateParam.put("description","xxxxxxxxxx");
        MailSendParameters mailSendParameters = MailSendParameters.builder()
                .receivers(receivers)
                .subject("测试发送邮件")
                .templateName("mail.ftl")
                .templateParam(templateParam)
                .build();
        String text = MailSendUtil.getTemplateText(mailSendParameters);
        mailSendParameters.setText(text);

        try {
            MailSendUtil.specifySendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
