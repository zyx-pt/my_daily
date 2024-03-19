package java8.function;

import java.util.function.Consumer;

public class VUtils {
    /**
     *  如果参数为true抛出异常
     **/
    public static ThrowExceptionFunction isTureThrow(boolean b){
        return (errorMessage) -> {
            if (b){
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     *  如果参数为true执行
     **/
    public static <T> void isTureConsumer(boolean b, T item, Consumer<T> consumer){
        if (b){
            consumer.accept(item);
        }
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     **/
    public static BranchHandle isTureOrFalseHandle(boolean b){
        return (trueHandle, falseHandle) -> {
            if (b){
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     **/
    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str){
        return (consumer, runnable) -> {
            if (str == null || str.length() == 0){
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }
}
