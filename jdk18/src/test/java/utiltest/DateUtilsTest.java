package utiltest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: DateUtils DateFormatUtils 测试
 * @Author zhengyongxian
 * @Date 2020/7/21 16:11
 */
public class DateUtilsTest {
    public final static FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

    @Test
    public void testDate2String() throws Exception{
        Date date = new Date();
        String date2Str1 = DateFormatUtils.format(date, "yyyy-MM-dd");
        String date2Str2 = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
        String date2Str3 = DATE_FORMAT.format(date);
       }

    @Test
    public void testString2Date() throws Exception{
        String str = "2020-5-20";
        Date str2Date1 = DATE_FORMAT.parse(str);
        // 根据 pattern解析
        Date str2Date2 = DateUtils.parseDate(str, "yyyy-MM-dd");
        // 许宽泛解析时, 可传入多个 pattern, 工具类将逐个尝试解析
        Date str2DateMulti1 = DateUtils.parseDate(str, "yyyy-MM-dd", "yyyy-MM-dd HH:mm");
        Date str2DateMulti2 = DateUtils.parseDate("2020-5-20 13:14", "yyyy-MM-dd", "yyyy-MM-dd HH:mm");
    }

    @Test
    public void testAddDate(){
        // 提供 addDays, addMinutes, addSeconds 等操作方法
        Date birthDay = new Date();
        Date addOneDay = DateUtils.addHours(birthDay, 1);
    }

    @Test
    public void test()throws Exception{
        String str = "2020-7-23";
        Date str2Date2 = DateUtils.parseDate(str, "yyyy-MM-dd");
        int i = str2Date2.compareTo(new Date());
        System.out.println(i);
    }

    @Test
    public void test2()throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
        String str = "2020/7/23";
        Date str2Date2 = dateFormat.parse(str);
        int i = str2Date2.compareTo(new Date());
        System.out.println(i);
    }
}
