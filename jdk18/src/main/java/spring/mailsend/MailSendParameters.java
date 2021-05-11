package spring.mailsend;

import lombok.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * @Author zhengyongxian
 * @Date 2020/5/14
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailSendParameters {

    /**
     * 发送者姓名
     */
    private String senderName;
    /**
     * 发送者邮件地址
     */
    private String senderAddress;
    /**
     * 邮件主题
     */
    @NonNull
    private String subject;
    /**
     * 接收者
     */
    @Nonnull
    private List<String> receivers;
    /**
     * 邮件内容
     */
    @Nonnull
    private String text;

    /**
     * 抄送者
     */
    private List<String> cc;

    /**
     * 附件路径
     */
    private List<String> attachmentPaths;

    /**
     * 文本中静态资源（图片）
     */
    private Map<String, String> staticResources;

    /**
     * 模板参数
     */
    private Map<String, String> templateParam;

    /**
     * 模板文件名称
     */
    private String templateName;

}