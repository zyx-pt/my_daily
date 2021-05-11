package array;

/**
这是一个可以对数组进行操作的工具类，该类中提供了获取最值，排序等功能。
*/

public class ArrayTool //帮助文档必须是public类，公有，暴露出去
{
    private ArrayTool(){}

    /**
    用于打印数组中的元素。打印形式：[element1, element2...]
    */
    public static void printArray(int[] arr) 
    {

      System.out.print("[");
      for(int i = 0; i < arr.length; i++)
      {
        if(i != arr.length-1)
          System.out.print(arr[i]+", ");
        else
          System.out.println(arr[i]+"]");
       }
     }

    /**
    获取一个整形数组中的最大值。
    @param arr 接收一个int类型的数组。
    @return 返回一个数组中的最大值。
    */
    public static int getMax(int[] arr) 
    {
      int max = 0;
      for(int i = 1; i < arr.length; i++) 
      {
        if(arr[i]>arr[max])
          max = i;
       }
      return arr[max];
     } 

    /**
    获取一个整形数组中的最小值。
    @param arr 接收一个int类型的数组。
    @return 返回一个数组中的最小值。
    */
    public static int getMin(int[] arr)
    {
      int min = 0;
      for(int i = 1; i < arr.length; i++) 
      {
        if(arr[i] < arr[min])
          min = i;
       }
      return arr[min];
     }

    /**
    对数组中某一元素进行查找。
    @param arr 接收一个int类型的数组。
    @param key 数组中某一元素。
    @return 返回数组中某一元素在数组中的位置，如果返回-1，表示不存在。
    */
    public static int getIndex(int[] arr, int key)
    {
      for(int i = 0; i < arr.length; i++)
      {
       if(arr[i] == key)
         return i;
       }
       return -1;
      }
/*
    public static int halfSearch(int[] arr, int key)
    {
      int min = 0;
      int max = arr.length - 1;
      int mid = (min + max)/2;
      while(arr[mid] != key)
      {
        if(key > arr[mid])
          min = mid + 1;
        else if(key < arr[mid])
          max = mid - 1; 
        if(min > max)
          return -1; 
        mid = (min + max)/2;   
       }
       return mid;
     }
*/

    /**
    对数组中某一元素进行折半查找，适用于数组元素较多且数组是有序的。
    @param arr 接收一个int类型的数组。
    @param key 数组中某一元素。
    @return 返回数组中某一元素在数组中的位置，如果返回-1，表示不存在。
    */
     public static int halfSearch(int[] arr, int key)
     {
      int min = 0;
      int max = arr.length - 1;
      int mid;
      while(min <= max)
      {
        mid = (min + max) >> 1;

        if(key > arr[mid])
          min = mid + 1;
        else if(key < arr[mid])
          max = mid - 1; 
        else 
          return mid;
       }
        return -1;
      }

    /**
    给数组进行选择排序。
    @param arr 接收一个int类型的数组。
    */
    public static void selectSort(int[] arr)
    {
      for(int i= 0; i < arr.length-1; i++)
      {
        for(int j = i+1; j < arr.length; j++)
        {
          if(arr[i] > arr[j])  //">"改为"<"变成从大到小
          {
            /*int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;*/
            swap(arr, i, j);
           } 
         }
       }
     }

/*
//减少数组元素之间的调换
    public static void selectSort(int[] arr)
    { 
      for(int i = 0; i < arr.length - 1; i++)
      { 
        int k = i; //记录最小值的角标
        for(int j = i+1; j < arr.length; j++)
        { 
          if(arr[j] < arr[k])
            k = j;  
         }
         if(i != k)
         swap(arr, i, k); 
       }
     }
*/

    /**
    给数组进行冒泡排序。
    @param arr 接收一个int类型的数组。
    */
    public static void bubbleSort(int[] arr)
    {
      for(int i = 0; i < arr.length; i++)
      {
        for(int j = 0; j < arr.length -i-1; j++) // -i:让每一次比较次数减少；-1：避免角标越界
          if(arr[j] > arr[j+1])  //">"改为"<"变成从大到小
          {
            /*int temp = arr[j];
            arr[j] = arr[j+1];
            arr[j+1] = temp;*/
            swap(arr, j, j+1);
           }
       }
     }

    /**
    给数组中元素进行位置的置换。
    @param arr 接收一个int类型的数组。
    @param a 要置换的位置。
    @param b 要置换的位置。
    */
    public static void swap(int[] arr, int a, int b )
    {
      int temp;
      temp = arr[a];
      arr[a] = arr[b];
      arr[b] = temp;
     }

}

