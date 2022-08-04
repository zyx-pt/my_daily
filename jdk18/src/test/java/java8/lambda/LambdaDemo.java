package java8.lambda;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: lambda简单demo
 *
 * @Author: zhengyongxian
 * @Date: 2020/8/17 22:05
 */
public class LambdaDemo {

   public static void main(String args[]){
      LambdaDemo tester = new LambdaDemo();
        
      // 类型声明
      MathOperation addition = (int a, int b) -> a + b;
        
      // 不用类型声明
      MathOperation subtraction = (a, b) -> a - b;
        
      // 大括号中的返回语句
      MathOperation multiplication = (int a, int b) -> { return a * b; };
        
      // 没有大括号及返回语句
      MathOperation division = (int a, int b) -> a / b;
        
      System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
      System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
      System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
      System.out.println("10 / 5 = " + tester.operate(10, 5, division));
        
      // 不用括号
      GreetingService greetService1 = message -> System.out.println("Hello " + message);
        
      // 用括号
      GreetingService greetService2 = (message) -> {System.out.println("Hello " + message);};


      // 引用外部变量
      int age = 18;
      String name = "zyx";
      AtomicReference<String> atomicStr = new AtomicReference<>("Hello ");
      AtomicInteger i = new AtomicInteger(18);

      GreetingService greetService3 = message ->{
         // 编译报错 不能在 lambda 内部修改定义在域外的局部变量
         // age++;
         // 不允许修改引用，name隐式的被声明为final
         // name = "xxx";
         i.set(25);
         atomicStr.set("hehe");
         System.out.println(atomicStr +" and " + message + " age: " + i);
      };

      greetService1.sayMessage("Runoob");
      greetService2.sayMessage("Google");
      greetService3.sayMessage("zyx");
   }
    
   interface MathOperation {
      int operation(int a, int b);
   }
    
   interface GreetingService {
      void sayMessage(String message);
   }
    
   private int operate(int a, int b, MathOperation mathOperation){
      return mathOperation.operation(a, b);
   }
}