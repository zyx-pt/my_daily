package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @ClassName entity.Apple
 * @Author yxzheng
 * @Date 2022/7/21 17:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Apple {
    private int weight;
    private String color;
}
