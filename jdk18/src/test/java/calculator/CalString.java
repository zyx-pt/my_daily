package calculator;

import java.util.Stack;

public class CalString {

    // 运算符优先级比较函数
    static int co(char a, char b) {
        if (a == '+' || a == '-') {
            if (b == '+' || b == '-' || b == ')' || b == '#')
                return 1; // a>b
            return -1; // a<b
        }
        if (a == '*' || a == '/') {
            if (b == '(')
                return -1;
            return 1;
        }
        if (a == '(' || a == '#') {
            if (a == '(' && b == ')')
                return 0; // a==b
            else if (a == '(' && b == '#')
                return 2; // can't compare
            else if (a == '#' && b == ')')
                return 2;
            else if (a == '#' && b == '#')
                return 0;
            else
                return -1;
        }
        if (a == ')') {
            if (b == '(')
                return 2;
            else
                return 1;
        }
        return 0;
    }

    // 判断表达式中数字的函数
    boolean isnum(char ch) {
        if ((ch >= '0' && ch <= '9') || ch == '.')
            return true;
        return false;
    }

    // 判断表达式中运算符的函数
    static boolean issign(char ch) {
        if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '('
                || ch == ')' || ch == '#')
            return true;
        return false;
    }

    // 计算最简表达式值的函数
    // a和b是操作数，s是运算符
    static double cal(double a, char s, double b) {
        if (s == '+')
            return a + b;
        if (s == '-')
            return b - a;
        if (s == '*')
            return a * b;
        if (s == '/')
            return b / a;
        return 0;
    }

    // 计算整个表达式值的函数
    static double calculate(String s) {
        s += "#";
        int i = 0;// 表达式循环条件
        char c;// 表达式中的一个字符
        Stack<Double> num = new Stack<Double>();// 操作数栈
        Stack<Character> op = new Stack<Character>();// 操作符栈
        op.push('#');// 表达式开始的标志
        c = s.substring(i, i + 1).charAt(0);// 取出表达式中的一位
        while (c != '#' || op.peek().toString().charAt(0) != '#')// 没有取到表达式开始或结束的标志'#'，就循环计算表达式的值
        {
            if (!issign(c))
            {
                int start = i;
                int end = 0;
                while (!issign(s.substring(i, i + 1).charAt(0)))

                    end = i++;
                double m = Double.parseDouble(s.substring(start, end + 1));
                num.push(m);
                c = s.substring(i, i + 1).charAt(0);
            } else
            {
                switch (co(op.peek(), c))
                {
                    case -1:
                        op.push(c);
                        i++;
                        c = s.substring(i, i + 1).charAt(0);
                        break;
                    case 0:
                        op.pop();
                        i++;
                        c = s.substring(i, i + 1).charAt(0);
                        break;
                    case 1:

                        double a = num.pop();
                        double b = num.pop();

                        char ss = op.pop();

                        num.push(cal(a, ss, b));
                        break;
                }
            }
        }
        return num.peek();
    }

}