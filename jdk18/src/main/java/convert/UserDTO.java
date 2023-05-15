package convert;

import com.google.common.base.Converter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author yxzheng
 * @Date 2020/5/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userName;
    private int age;

    /**
     * DTO -> Account
     * @return
     */
    public User convertToUser(){
        UserDTOConvert userDTOConvert = new UserDTOConvert();
        User user = userDTOConvert.convert(this);
        return user;
    }

    /**
     * Account -> DTO
     * @param user
     * @return
     */
    public UserDTO convertFor(User user){
        UserDTOConvert userDTOConvert = new UserDTOConvert();
        UserDTO convert = userDTOConvert.reverse().convert(user);
        return convert;
    }


    // 将UserDTOConvert和DTO进行聚合
    // 因为每一个转化对象都是由在遇到DTO转化的时候才会出现
    public class UserDTOConvert extends Converter<UserDTO,User> {
        @Override
        protected User doForward(UserDTO userDTO) {
            User user = new User();
            try {
                // 参数左为dest  右为src
                PropertyUtils.copyProperties(user, userDTO);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return user;
        }


        @Override
        protected UserDTO doBackward(User user) {
            // 给前端返回数据，逆向转化
            UserDTO userDTO = new UserDTO();
            try {
                PropertyUtils.copyProperties(userDTO, user);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return userDTO;
            // 很多业务需求的出参和入参的DTO对象是不同的，那么你需要更明显的告诉程序：逆向是无法调用的:
            // 断言异常，而不是业务异常，这段代码告诉代码的调用者，这个方法不是准你调用的，如果你调用，我就”断言”你调用错误了
//            throw new AssertionError("不支持逆向转化方法!");
        }
    }
}
