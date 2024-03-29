package constant;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * <pre>
 * 描述：状态值相关常量
 * </pre>
 *
 * @Author yxzheng
 * @Date 2020/10/10 16:15
 */

public class StatusConstant {

    private static Map<Class, Object> enumMap = new ConcurrentHashMap<>();

    /**
     * @Description: 根据条件获取枚举对象
     *
     * @Author: zhengyongxina
     * @Date: 2020/10/10 16:19
     * @param className
     * @param predicate
     * @return: java.util.Optional<T>
     */
    public static <T> Optional<T> getEnumObject(Class<T> className, Predicate<T> predicate) {
        if (!className.isEnum()) {
            return null;
        }
        Object obj = enumMap.get(className);
        T[] ts = null;
        if (obj == null) {
            ts = className.getEnumConstants();
            enumMap.put(className, ts);
        } else {
            ts = (T[]) obj;
        }
        return Arrays.stream(ts).filter(predicate).findAny();
    }

}
