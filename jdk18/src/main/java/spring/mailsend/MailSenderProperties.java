package spring.mailsend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yxzheng
 * @Date 2020/5/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailSenderProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
