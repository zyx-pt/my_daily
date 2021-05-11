package java8.default_;

import org.junit.Test;

/**
 * @Description: 默认方法
 *
 * 默认方法就是接口可以有实现方法，而且不需要实现类去实现其方法。
 *
 * 我们只需在方法名前面加个 default 关键字即可实现默认方法。
 *
 * @Author zhengyongxian
 * @Date 2020/8/19 9:15
 */
public class DefaultMethodTest {

    /** 1.默认方法的继承
     *      a.不覆写默认方法，直接从父接口中获取方法的默认实现。
     *      b.覆写默认方法，这跟类与类之间的覆写规则相类似。
     *      c.覆写默认方法并将它重新声明为抽象方法，这样新接口的子类必须再次覆写并实现这个抽象方法。
     *
     * */
    @Test
    public void testExtends(){
        new InterfaceB1() {}.foo(); // 打印：“InterfaceA foo”
        new InterfaceC1() {}.foo(); // 打印：“InterfaceC foo”
        new InterfaceD1() {
            @Override
            public void foo() {
                System.out.println("InterfaceD foo");
            }
        }.foo(); // 打印：“InterfaceD foo”

        // 或者使用 lambda 表达式
        ((InterfaceD1) () -> System.out.println("InterfaceD foo")).foo();
    }

    interface InterfaceA1 {
        default void foo() {
            System.out.println("InterfaceA foo");
        }
    }

    // 不覆写默认方法，直接从父接口中获取方法的默认实现
    interface InterfaceB1 extends InterfaceA1 {
    }

    // 覆写默认方法，这跟类与类之间的覆写规则相类似
    interface InterfaceC1 extends InterfaceA1 {
        @Override
        default void foo() {
            System.out.println("InterfaceC foo");
        }
    }

    // 覆写默认方法并将它重新声明为抽象方法，这样新接口的子类必须再次覆写并实现这个抽象方法
    interface InterfaceD1 extends InterfaceA1 {
        @Override
        void foo();
    }

    /** 2.默认方法多继承 */
    @Test
    public void testMultiExtends(){
        ClassA2 classA2 = new ClassA2();
        classA2.bar(); // InterfaceB bar
        classA2.foo(); // InterfaceA foo
        System.out.println("----------");
        ClassB2 classB2 = new ClassB2();
        classB2.bar();
        classB2.foo(); // InterfaceC foo
    }

    interface InterfaceA2 {
        default void foo() {
            System.out.println("InterfaceA foo");
        }
    }

    interface InterfaceB2 {
        default void bar() {
            System.out.println("InterfaceB bar");
        }
    }

    interface InterfaceC2 {
        default void foo() {
            System.out.println("InterfaceC foo");
        }

        default void bar() {
            System.out.println("InterfaceC bar");
        }
    }

    class ClassA2 implements InterfaceA2, InterfaceB2 {
    }

    // 错误
    //class ClassB2 implements InterfaceB2, InterfaceC2 {
    //}

    class ClassB2 implements InterfaceB2, InterfaceC2 {
        //  InterfaceB2和InterfaceC2中都存在相同签名的 bar 方法，需要手动解决冲突。覆写存在歧义的方法，
        //  并可以使用 InterfaceName.super.methodName(); 的方式手动调用需要的接口默认方法
        @Override
        public void bar() {
            InterfaceB2.super.bar(); // 调用 InterfaceB 的 bar 方法
            InterfaceC2.super.bar(); // 调用 InterfaceC 的 bar 方法
            System.out.println("ClassB bar"); // 做其他的事
        }
    }

    /** 3.接口继承发生冲突的解决 */
    @Test
    public void testConflict(){
        ClassA3 classA3 = new ClassA3();
        classA3.foo();
        System.out.println("------");
        ClassB3 classB3 = new ClassB3();
        classB3.foo();
        System.out.println("-------");
        ClassD3 classD3 = new ClassD3();
        classD3.foo();
    }
    interface InterfaceA3 {
        default void foo() {
            System.out.println("InterfaceA foo");
        }
    }

    interface InterfaceB3 extends InterfaceA3 {
        @Override
        default void foo() {
            System.out.println("InterfaceB foo");
        }
    }

    // 正确
    class ClassA3 implements InterfaceA3, InterfaceB3 {
    }

    class ClassB3 implements InterfaceA3, InterfaceB3 {
        @Override
        public void foo() {
            // InterfaceB3继承了InterfaceA3，那么 InterfaceB3 接口一定包含了所有 InterfaceA3 接口中的字段方法
            // InterfaceA3.super.foo(); // 错误
            InterfaceB3.super.foo();
            System.out.println("ClassB3 foo");
        }
    }

    // 如果想要调用 InterfaceA3 接口中的 foo 方法，
    // 只能通过自定义一个新的接口同样继承 InterfaceA3 接口并显示地覆写 foo方法
    interface InterfaceC3 extends InterfaceA3 {
        @Override
        default void foo() {
            InterfaceA3.super.foo();
        }
    }

    class ClassD3 implements InterfaceC3, InterfaceB3 {
        @Override
        public void foo() {
            InterfaceC3.super.foo();
            InterfaceB3.super.foo();
            System.out.println("ClassD3 foo");
        }
    }

    /** 4.接口和抽象类
     * 当接口继承行为发生冲突时的另一个规则是，类的方法声明优先于接口默认方法，无论该方法是具体的还是抽象的。
     * */
    @Test
    public void testAbstract(){
        ClassA4 classA4 = new ClassA4();
        classA4.foo(); // 打印：“InterfaceA foo”
        classA4.bar(); // 打印：“AbstractClassA bar”
    }
    interface InterfaceA4 {
        default void foo() {
            System.out.println("InterfaceA foo");
        }

        default void bar() {
            System.out.println("InterfaceA bar");
        }
    }

    abstract class AbstractClassA4 {
        public abstract void foo();

        public void bar() {
            System.out.println("AbstractClassA bar");
        }
    }

    class ClassA4 extends AbstractClassA4 implements InterfaceA4 {
        @Override
        public void foo() {
            InterfaceA4.super.foo();
        }
    }


    /** 5.接口静态方法
     * Java 8 还在允许在接口中定义静态方法
     * */
    @Test
    public void testStatic() {
        InterfaceA5.printHelloWorld(); // 打印：“hello, world”
    }
    interface InterfaceA5 {
        default void foo() {
            printHelloWorld();
        }

        static void printHelloWorld() {
            System.out.println("hello, world");
        }
    }
}

