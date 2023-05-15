package spring.mailsend;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yxzheng
 * @Date 2020/5/15
 */
public class SpringMailSendDemo {

    public static void main(String[] args) {
//        mailTest();
//        testDefaultSendMail();
//        testSpecifySendMail();
        testSendMailByTemplate();
    }

    public static void mailTest() {
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
            MailSend.specifySendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 自定义邮件内容发送
     */
    public static void testDefaultSendMail(){
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
            MailSend.defaultSendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 自定义邮件内容发送
     */
    public static void testSpecifySendMail(){
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
            MailSend.specifySendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据邮件模板发送
     */
    public static void testSendMailByTemplate(){
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
        String text = MailSend.getTemplateText(mailSendParameters);
        mailSendParameters.setText(text);

        try {
            MailSend.specifySendMail(mailSendParameters);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
