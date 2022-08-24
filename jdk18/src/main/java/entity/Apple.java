package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @ClassName entity.Apple
 * @Author zhengyongxian
 * @Date 2022/7/21 17:06
 */
@Data
@Builder
@AllArgsConstructor
public class Apple {
    private int weight;
    private String color;
}
