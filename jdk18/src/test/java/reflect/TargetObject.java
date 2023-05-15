package reflect;

import java.util.List;

/**
 * 创建一个我们要使用反射操作的类
 * @Author yxzheng
 * @Date 2020/5/7
 */
@MyClassAnno(name = "class", value = "TargetObject")
public class TargetObject extends SuperClass implements IService{

    private String value;

    public String publicValue;

    @MyFieldAnno(name = "variable", value = "xxxx")
    private String variable;

    private List<String> stringList;

    public TargetObject() {
        value = "JavaGuide";
    }

    public TargetObject(String value){
        this.value = value;
    }

    private TargetObject(String value, String publicValue){
        this.value = value;
        this.publicValue = publicValue;
    }

    public void publicMethod(String s) {
        System.out.println("I love " + s);
    }

    private void privateMethod() {
        System.out.println("I am " + value);
    }

    @MyMethodAnno(name = "method", value = "testMethodAnno")
    public void testMethodAnno(){

    }

    public void testParameterAnno(@MyParameterAnno(value = "parameter",  name = "value") String value){

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPublicValue() {
        return publicValue;
    }

    public void setPublicValue(String publicValue) {
        this.publicValue = publicValue;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
