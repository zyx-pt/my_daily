package array;

public class ArrayBinary {
    public static void main (String args[]) {
        //System.out.println(Integer.toBinaryString(60)); //java中用于转换为二进制
        toBin(60);
        toBa(60);
        toHex(60);
     }

/*
通过数组来完成
    0 1 2 3 4 5 6 7 8 9 A  B  C  D  E  F   -->十六进制中的元素
    0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
*/

//十进制-->二进制
    public static void toBin(int num){
        trans(num, 1, 1);
    }

//十进制-->八进制
    public static void toBa(int num){
      trans(num, 7, 3);
     }

//十进制-->十六进制
    public static void toHex(int num){
        trans(num, 15, 4);
    }

//将公共部分封装成一个函数
    public static void trans(int num, int base, int offset){
        //如果这个数为0，输出0
        if(num == 0){
            System.out.println(0);
            return;
        }

       char[] chs = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

       char[] arr = new char[32];

       int pos = arr.length;

       while(num != 0){
           int temp = num & base;
            arr[--pos] = chs[temp];
            num = num >>> offset;
       }

       for(int i = pos; i < arr.length; i++){
            System.out.print(arr[i]);
       }
       System.out.println();
    }


/*
//十进制-->二进制
    public static void toBin(int num)
    {
      //定义一个二进制的表
      char [] chs = {'0','1'};
      
      //定义一个临时存储容器
       char[] arr = new char[32];

      //定义一个操作组的指针,每次从最后面开始存储
      int pos = arr.length;

      while(num != 0)
      {
        int temp = num & 1;
        arr[--pos] = chs[temp];
        num = num >>> 1;
       }  
	   //存储数据的arr数组的遍历
        for(int i = pos; i < arr.length; i++) //pos,记录着数组中左边有具体数值的位置，->从有具体数值开始打印
        {
          System.out.print(arr[i]);
         }
        System.out.println();
     }
 

//十进制-->十六进制    
     public static void toHex(int num)
    {
      //定义一个十六进制的表
      char [] chs = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
      
      //定义一个临时存储容器
       char[] arr = new char[8];

      //定义一个操作组的指针,每次从最后面开始存储
      int pos = arr.length;

      while(num != 0)
      {
        int temp = num & 15;
        arr[--pos] = chs[temp];
        num = num >>> 4;        
       }     
	   //存储数据的arr数组的遍历
        for(int i = pos; i < arr.length; i++)
        {
          System.out.print(arr[i]);
         }
        System.out.println();
     }
*/


/*

//通过StringBuffer容器，使用reverse
//十进制-->二进制（只能转换大于0的数）
    public static void toBin(int num)
    {
      StringBuffer sb =new StringBuffer();  //存储数据的容器
      
      while(num > 0)
      {  
        sb.append(num%2);                  //将数据添加进去
        num = num/2;
       }

       System.out.prin(sb);
       System.out.print(sb.reverse());  //reverse(将存储内容反转）
     }

//十进制-->十六进制
    public static void toHex(int num)
    {
      StringBuffer sb =new StringBuffer();
      
      for(int x = 0; x < 8; x++)
      {
        int temp = num & 15;
        if(temp > 9) 
          sb.append((char)(temp - 10 + 'A'));
        else  
          sb.append(temp);
        num = num >>> 4; 
       }
       System.out.print(sb.reverse());
     }

*/

}