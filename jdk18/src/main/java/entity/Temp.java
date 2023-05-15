package entity;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Description: lombok同时使用@Data和@Builder会导致无参构造器失效
 * 解决方法：1.手动添加无参构造器，并使用@Tolerate
 *         2. 添加注解@AllArgsConstructor 和 @NoArgsConstructor
 * @ClassName entity.Temp
 * @Author yxzheng
 * @Date 2022/11/30 15:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Temp {
    private String str;
    private Integer size;
//    @NonNull
    private BigDecimal id;
//    @Tolerate
//    public Temp(){}
}
