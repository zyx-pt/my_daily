package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.Student;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: alibaba fastjson 测试
 * @Author: zhengyongxian
 * @Date: 2024/4/28 11:36
 */
public class FastjsonTest {
    @Test
    public void test() {
        // 对象转json字符串: JSONObject.toJSONString(obj) | JSON.toJSONString(obj)
        Map<String, Object> map = new HashMap<>();
        map.put("aac001", "1");
        map.put("aac002", "123456");
        map.put("aac003", "张三");
        String mapJson = JSONObject.toJSONString(map);
        System.out.println("mapJson:" + mapJson);
        Student student = new Student(1L, "男", 18, new BigDecimal("100"));
        String studentJson = JSON.toJSONString(student);
        System.out.println("studentJson:" + studentJson);

        // json字符串转JSONObject: JSONObject.parseObject(json) | JSON.parseObject(json)
        JSONObject studentJsonObject = JSONObject.parseObject(studentJson);
//        JSONObject studentJsonObject = JSON.parseObject(studentJson);
        System.out.println("studentJsonObject:" + studentJsonObject);

        // json字符串实体对象: JSONObject.parseObject(json, obj.class) | JSON.parseObject(json, obj.class)
        Student student1 = JSON.parseObject(studentJson, Student.class);
//        Student student1 = JSON.parseObject(studentJson, Student.class);
        System.out.println(student1);

        // JSONObject转实体对象: JSON.parseObject(JSON.toJSONString(jsonObj), obj.class)
        Student student2 = JSON.parseObject(JSON.toJSONString(studentJsonObject), Student.class);
        System.out.println(student2);

        // List实体对象转json字符串: JSONObject.toJSONString(obj) | JSON.toJSONString(obj)
        List<Student> list = new ArrayList<>();
        list.add(new Student(1L,"男",18,new BigDecimal("100")));
        list.add(new Student(2L,"男",19,new BigDecimal("90.11")));
        list.add(new Student(3L,"女",20,new BigDecimal("80.12")));
        String listJson = JSON.toJSONString(list);
        System.out.println("listJson: " + listJson);

        // json字符串转JSONArray: JSONArray.parseArray(listJson) | JSON.parseArray(listJson);
        JSONArray jsonArray = JSONArray.parseArray(listJson);
//        JSONArray jsonArray = JSON.parseArray(listJson);
        System.out.println("jsonArray: " + jsonArray);

        // JSONArray转List实体对象: JSONArray.parseArray(listJson, obj.class) | JSON.parseArray(listJson, obj.class);
        List<Student> students = JSONArray.parseArray(listJson, Student.class);
//        List<Student> students = JSON.parseArray(listJson, Student.class);
        System.out.println("students: " + students);

    }
}
