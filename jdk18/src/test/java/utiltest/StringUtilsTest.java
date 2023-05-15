package utiltest;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 测试StringUtils的相关使用
 * @Author yxzheng
 * @Date 2020/7/21 11:39
 */
public class StringUtilsTest {


    @Test
    public void test1(){
        // 判空
        // isNotBlank -> 非空;且非空字符串;且非全部空格的字符串
        boolean notBlank = StringUtils.isNotBlank("  ");// false
        // isNotEmpty -> 非空；非空字符串
        boolean notEmpty = StringUtils.isNotEmpty("  ");// true

        // 判断包含字符串
        StringUtils.contains("abc", "A");// false
        StringUtils.containsIgnoreCase("abc", "A");// true

        // 前后去空格但可以规避 NPE
        String trimToEmpty1 = StringUtils.trimToEmpty(null);// ""
        String trimToEmpty2 = StringUtils.trimToEmpty("    abc    ");// "abc"
        String trimToEmpty3 = StringUtils.trimToEmpty("    a  b  c    ");// "a  b  c"

        // 加前缀
        String preUsername = StringUtils.prependIfMissing("username", "user.");// user.username
        //补齐，可用于生成格式化单号
        String leftPad = StringUtils.leftPad("15", 5, "0");// 00015 左补齐
        System.out.println(leftPad);
    }

    @Test
    public void testJoin() {
        // 拼接字符串 可以用于拼接数组、集合
        String[] strArr = {"a", "b", "c"};
        String join1 = StringUtils.join(new String[]{"a", "b", "c"}, ",");// a,b,c
        List<String> strList = Lists.newArrayList("a", "b", "c");
        String join2 = StringUtils.join(strList, ",");// a,b,c

        // String join() 和 StringUtils join() 区别
        String str = null;
        String[] strArr2 = {null, "b", "c"};
        System.out.println("string.join(): " + str.join("str1"));  // ""
        // System.out.println("string.join(): " + "str1".join(str)); // java.lang.NullPointerException
        System.out.println("StringUtils.join(): " + StringUtils.join(strArr2,"/")); // /b/c
    }

    @Test
    public void testSplit() {
        // 拆分字符串
        String[] split = StringUtils.split("a.b.c", ',');
        System.out.println("split:" + Arrays.toString(split));// ["a", "b", "c"]
        // String split() 和 StringUtils split() 区别
        String splitStr = ",, ,a,1,2;3,,";
        String[] split1 = splitStr.split(",");
        // 有加-1，是可以包含有效数字后面所有的空字符串.
        String[] split3 = splitStr.split(",", -1);
        // 过滤所有的空字符串. 当然空格不会被过滤.并且分隔符可以设置多个
        String[] split2 = StringUtils.split(splitStr, ",;");

        System.out.println("str.split(\",\")：" + Arrays.toString(split1)); // [, ,  , a, 1, 2;3]
        System.out.println("StringUtils.split(str, \",;\")：" + Arrays.toString(split2)); // [ , a, 1, 2, 3]
        System.out.println("str.split(\",\", -1)：" + Arrays.toString(split3)); // [, ,  , a, 1, 2;3, , ]
    }

    @Test
    public void testSubstring(){
        // 截取字符串 -> 左包含，右不包含
        String substring = StringUtils.substring("abcba", 1);// bcba
        String substring2 = StringUtils.substring("abcba", 1, 3);// bc
        String substringBeforeLast = StringUtils.substringBeforeLast("abcba", "b");// abc
        String substringAfterLast = StringUtils.substringAfterLast("abcba", "b");// a

    }

    @Test
    public void testEqual(){
        // equalsAny(final CharSequence string, final CharSequence... searchStrings) 使用可变参数编程
        // alibaba规范，可变参数必须放置在参数列表的最后（提倡同学们尽量不用可变参数编程）-> 为什么不提倡？
        String str = "zyx";
        boolean equals = StringUtils.equals(str, "hehe"); // false
        boolean equalsAny = StringUtils.equalsAny(str, "hehe", "zyx"); // true
        boolean equalsIgnoreCase = StringUtils.equalsIgnoreCase(str, "ZYX"); // true
        boolean equalsAnyIgnoreCase = StringUtils.equalsAnyIgnoreCase(str, "ZYX", "HEHE"); // true
        System.out.println();

        // 判断字符串相等(忽略大小写和全角半角)
        boolean result = StringUtils.equalsIgnoreCase(ToDBC("中集东瀚（上海）航运有限公司AAAbb"), ToDBC("中集东瀚（上海)航运有限公司aaaBB"));
        System.out.println(result);

    }

    /**
     * 半角转全角
     *
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 全角转半角
     *
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }
}
