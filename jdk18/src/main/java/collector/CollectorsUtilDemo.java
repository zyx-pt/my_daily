package collector;

import entity.Student;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * 描述：TODO
 * </pre>
 *
 * @Author yxzheng
 * @Date 2020/10/15 17:09
 * @Description: TODO
 */

public class CollectorsUtilDemo {

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1L,"男",18,new BigDecimal("100")));
        list.add(new Student(2L,"男",19,new BigDecimal("90.11")));
        list.add(new Student(3L,"女",20,new BigDecimal("80.123")));
        list.add(new Student(4L,"女",20,new BigDecimal(70)));
        list.add(new Student(5L,"女",20,null));

        //单条件筛选
        //按照性别分组求分数总和
        Map<String, BigDecimal> scoreCount = list.stream()
                .filter(t -> t.getScore() != null)
                .collect(Collectors.groupingBy(Student::getSex, CollectorsUtil.summingBigDecimal(Student::getScore)));
        System.out.println("----按照性别分组求分数总和----");
        scoreCount.forEach((k,v) -> System.out.println("key: " + k + " , " + "value: " + v));
        //按照性别求分数总和
        Map<String, BigDecimal> scoreSum = list.stream()
                .filter(t -> t.getScore() != null)
                .collect(Collectors.groupingBy(Student::getSex, CollectorsUtil.summingBigDecimal(Student::getScore, 2, BigDecimal.ROUND_HALF_UP)));
        System.out.println("----按照性别求分数总和----");
        scoreSum.forEach((k,v) -> System.out.println("key: " + k + " , " + "value: " + v));

        //按照性别求分数平均值
        Map<String, BigDecimal> scoreAvg = list.stream()
                .filter(t -> t.getScore() != null)
                .collect(Collectors.groupingBy(Student::getSex, CollectorsUtil.averagingBigDecimal(Student::getScore,2, 2)));
        System.out.println("----按照性别求分数平均值----");
        scoreAvg.forEach((k,v) -> System.out.println("key: " + k + " , " + "value: " + v));

        //按照性别年龄分组求分数总和
        Map<String, BigDecimal> ageScoreCount = list.stream()
                .filter(t -> t.getScore() != null)
                .collect(Collectors.groupingBy(p -> fetchGroupKey(p), CollectorsUtil.summingBigDecimal(Student::getScore)));
        System.out.println("----按照性别年龄分组求分数总和----");
        ageScoreCount.forEach((k,v) -> System.out.println("key: " + k + " , " + "value: " + v));

        //按照性别年龄分组求分数平均值
        Map<String, BigDecimal> ageScoreAvg = list.stream()
                .filter(t -> t.getScore() != null)
                .collect(Collectors.groupingBy(p -> fetchGroupKey(p), CollectorsUtil.averagingBigDecimal(Student::getScore, 2, 2)));
        System.out.println("----按照性别年龄分组求分数平均值----");
        ageScoreAvg.forEach((k,v) -> System.out.println("key: " + k + " , " + "value: " + v));

    }

    //多条件筛选
    //多条件筛选分组属性
    private static String fetchGroupKey(Student student) {
        return student.getAge() + "#" + student.getSex();
    }

}
