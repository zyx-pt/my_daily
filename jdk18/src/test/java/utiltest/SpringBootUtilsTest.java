package utiltest;

import com.google.common.collect.Lists;
import entity.Person;
import entity.Student;
import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;

import java.util.List;

public class SpringBootUtilsTest {
    // StopWatch计时工具
    @Test
    public void  testStopWatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("<------------任务耗时统计------------>");
        stopWatch.start("Task1");
        System.out.println("当前任务名称：" + stopWatch.currentTaskName());
        Thread.sleep(1000);
        stopWatch.stop();
        stopWatch.start("Task2");
        System.out.println("当前任务名称：" + stopWatch.currentTaskName());
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        System.out.println(stopWatch.shortSummary());
        System.out.println("所有任务总耗时：" + stopWatch.getTotalTimeMillis());
        System.out.println("任务总数：" + stopWatch.getTaskCount());

    }

    // Assert 断言工具类
    @Test
    public void testAssert(){
        Person entity = new Person();
        List<String> list = Lists.newArrayList("111");
        // 求参数 object 必须为非空（Not Null），否则抛出异常，不予放行 message为自定义异常信息
        // isNull与其规则相反
        Assert.notNull(entity, "未查询到实体对象");
        // 要求参数必须为真（True），否则抛出异常，不予『放行』。
        Assert.isTrue(2 > 1, "校验不通过");
        // 要求参数（List/Set）必须非空（Not Empty），否则抛出异常，不予放行
        Assert.notEmpty(list, "列表不能为空");
        // 要求参数（String）必须有长度（即，Not Empty），否则抛出异常，不予放行
        Assert.hasLength(" ", "参数不能为空");
        // 要求参数（String）必须有内容（即，Not Blank），否则抛出异常，不予放行
        Assert.hasText("1  ", "参数不能为空");
        // 要求参数是指定类型的实例，否则抛出异常，不予放行
        Assert.isInstanceOf(Person.class, entity, "实体所属类型错误");
        // 要求参数 `subType` 必须是参数 superType 的子类或实现类，否则抛出异常，不予放行
        Assert.isAssignable(Person.class, Student.class, "Student类必须是Person类的子类或实现类");
    }

    // ObjectUtils 对象、数组、集合
    @Test
    public void testObjectUtils(){
        Person entity = new Person();
        /*
        判断参数对象是否为空，判断标准为：
            Optional: Optional.empty()
               Array: length == 0
        CharSequence: length == 0
          Collection: Collection.isEmpty()
                 Map: Map.isEmpty()
         */
        boolean empty = ObjectUtils.isEmpty(entity);

    }
}
