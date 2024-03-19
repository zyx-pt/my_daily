package utiltest;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import entity.Account;
import entity.Person;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HutoolUtilTest {
    // Convert：类型转换工具
    @Test
    public void testConvert(){
        // Object转数字
        Integer anInt = Convert.toInt("123");
        // 给定值为空或转换失败，返回默认值
        Integer anInt1 = Convert.toInt("123a", 0);
        // 转字符串
        String str = Convert.toStr(11);
        // 转BigDecimal
        BigDecimal bigDecimal = Convert.toBigDecimal("111");
        // 金额转中文形式
        String digitToChinese = Convert.digitToChinese(1234);
    }

    // 时间工具
    @Test
    public void testDateUtil(){
        //1、当前时间
        Date date = DateUtil.date();
        Date date2 = DateUtil.date(System.currentTimeMillis());
        //2、当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
        String today = DateUtil.today();
        //3、获得月份，从0开始计数
        int month = DateUtil.month(new Date());
        //4、格式化后的字符串 默认yyyy-MM-dd HH:mm:ss
        String dateTime = DateUtil.formatLocalDateTime(LocalDateTime.now());
        //格式化后的字符串 指定格式类型。所以类型在DatePattern工具类也可以找 不用在手写
        String format = DateUtil.format(new Date(), DatePattern.CHINESE_DATE_PATTERN);
        //5、获取某月的开始时间
        DateTime dateTime1 = DateUtil.beginOfMonth(new Date());
        //获取昨天时间
        DateTime yesterday = DateUtil.yesterday();
        //6、计时，常用于记录某段代码的执行时间，单位：毫秒
        long spendMs = DateUtil.spendMs(111111111L);
    }

    // 数字类工具
    @Test
    public void testNumberUtil(){
        //1、数字相加 如果为空默认加0
        BigDecimal decimal = BigDecimal.valueOf(10);
        //返回10
        BigDecimal add = NumberUtil.add(decimal, null);
    }

    // 身份认证工具
    @Test
    public void testIdcardUtil(){
        String ID_18 = "119127200009255110";
        // 是否有效
        boolean valid = IdcardUtil.isValidCard(ID_18);
        // 年龄
        int age = IdcardUtil.getAgeByIdCard(ID_18);
        int age2 = IdcardUtil.getAgeByIdCard(ID_18, DateUtil.parseDate("2023-09-24"));
        int age3 = DateUtil.age(DateUtil.parse("20000925", "yyyyMMdd"), DateUtil.parseDate("2023-09-24"));
        // 生日
        String birth = IdcardUtil.getBirthByIdCard(ID_18);
        // 省份
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
        // 男：1，女：0
        int genderByIdCard = IdcardUtil.getGenderByIdCard(ID_18);
    }

    // 信息脱敏工具
    @Test
    public void testValidator(){
        //【中文姓名】只显示第一个汉字，其他隐藏为2个星号，比如：李**
        DesensitizedUtil.chineseName("张三");
        //【身份证号】前1位 和后2位
        DesensitizedUtil.idCardNum("33012345",1,2);
        //【手机号码】前三位，后4位，其他隐藏，比如135****2210
        DesensitizedUtil.mobilePhone("13712345678");
        //【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
        DesensitizedUtil.address("北京市海淀区被顺街道",4);
        //还有 邮箱、车牌、邮箱、银行卡、密码......
    }

    // 字段校验工具
    @Test
    public void testDesensitizedUtil(){
        //1、判断字符串是否全部为字母组成，包括大写和小写字母和汉字
        Validator.isLetter("小小宝贝");
        //2、验证该字符串是否是数字
        Validator.isNumber("123");
        //3、验证是否为可用邮箱地址
        Validator.isEmail("123456@mall.com");
        //4、验证是否为手机号码（中国）
        Validator.isMobile("15612345678");
        //5、验证是否为身份证号码（支持18位、15位和港澳台的10位）
        Validator.isCitizenId("330127210006111234");
        //6、验证是否为中国车牌号
        Validator.isPlateNumber("小A.88888");
        //7、验证是否都为汉字
        Validator.isChinese("小A");
        //8、检查给定的数字是否在指定范围内
        Validator.isBetween(3,2,1);
    }

    // 集合工具类
    @Test
    public void testCollStreamUtil(){
        List<Person> list = new ArrayList<>();
        list.add(new Person(1L, "张三", "1", new Date(), new Account()));
        list.add(new Person(1L, "李四", "1", new Date(), new Account()));
        list.add(new Person(1L, "王五", "1", new Date(), new Account()));
        //1、学生id -> 学生对象
        Map<Long, Person> map = CollStreamUtil.toIdentityMap(list, Person::getId);
        //2、学生id -> Person
        Map<Long, String> map1 = CollStreamUtil.toMap(list, Person::getId, Person::getName);
        //3、学生id -> 学生集合
        Map<Long, List<Person>> map2 = CollStreamUtil.groupByKey(list, Person::getId);
        //4、获取用户名集合
        List<String> list1 = CollStreamUtil.toList(null, Person::getName);
    }
}
