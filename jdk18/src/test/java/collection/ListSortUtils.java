package collection;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSortUtils<T> {

    public  void sort (List<T> targetList, String sortField, String sortMode) {
        Collections.sort(targetList, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {

                int retVal = 0;
                try {
                    // 获取方法名
                    String newSortField = sortField.substring(0,1).toUpperCase() + 
                        sortField.replaceFirst("\\w","");
                    String methodStr = "get" + newSortField;
                    // String methodStr = "get" + sortField.substring(0, 1).toUpperCase() + sortField.substring(1);
                    Method method1 = ((T)o1).getClass().getMethod(methodStr,null);
                    Method method2 = ((T)o2).getClass().getMethod(methodStr,null);

                    if (sortMode != null && "desc".equals(sortMode.toLowerCase())) {
                        // 倒序
                        retVal = method2.invoke(((T) o2), null).toString()
                            .compareTo(method1.invoke(((T) o1), null).toString());
                    } else {
                        // 正序
                        retVal = method1.invoke(((T) o1), null).toString()
                            .compareTo(method2.invoke(((T) o2), null).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return retVal;
            }
        });
    }
}