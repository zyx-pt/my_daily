package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <pre>
 * 描述：TODO
 * </pre>
 *
 * @Author yxzheng
 * @Date 2020/10/15 17:09
 * @Description: TODO
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private Long id;
    private String sex;
    private Integer age;
    private BigDecimal score;
}
