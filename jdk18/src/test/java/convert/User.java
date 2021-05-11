package convert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * lombok的使用
 * @Author zhengyongxian
 * @Date 2020/5/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userName;
    private int age;

}
