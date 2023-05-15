package thread;

import convert.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: ThreadLocal使用
 * @ClassName thread.ThreadLocalDemo
 * @Author yxzheng
 * @Date 2022/9/26 9:10
 */
public class ThreadLocalDemo {
    /**--------------------------------------------------SimpleDateFormat---------------------------------------------*/
    // 单线程使用的日期格式化
    private static SimpleDateFormat DATE_FORMAT_SIMPLE = new SimpleDateFormat("yyyy-MM-dd");
    // 使用lambda初始化线程安全的日期格式化
    private static ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    // 非lambda初始化初始化线程安全的日期格式化
    private static ThreadLocal<SimpleDateFormat> DATE_FORMAT2 = new ThreadLocal<SimpleDateFormat>(){
        //初始化线程本地变量
        @Override
        protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 字符串转成日期类型 线程安全
     */
    public static Date dateFormat(String dateStr) {
        Date result = null;
        try {
            //ThreadLocal中的get()方法
            result=  DATE_FORMAT.get().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 字符串转成日期类型 线程不安全
     * 报错如：Exception in thread "Thread-3" java.lang.NumberFormatException: For input string: ""
     */
    public static Date dateFormat1(String dateStr) {
        Date result = null;
        try {
            result = DATE_FORMAT_SIMPLE.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**--------------------------------------------------SimpleDateFormat---------------------------------------------*/

    /**----------------------------------------------ThreadLocal解决过度传参问题-----------------------------------------*/
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    /*void work(User user) {
        setInfo(user);
        checkInfo(user);
        someThing(user);
    }*/

    void work(User user) {
        try {
            userThreadLocal.set(user);
            setInfo();
            checkInfo();
            someThing();
        } finally {
            userThreadLocal.remove();
        }
    }

    void setInfo() {
        User u = userThreadLocal.get();
        //.....
    }

    void checkInfo() {
        User u = userThreadLocal.get();
        //....
    }

    void someThing() {
        User u = userThreadLocal.get();
        //....
    }
}
