package leetcode;

import algo.array.Array;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author zhengyongxian
 * @Date 2020/7/19 19:58
 */
public class Solution1_10 {

    @Test
    public void testTwoSum(){
        int[] ints = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println("twoSun:"+Arrays.toString(ints));


    }

    /**
     * @Description: 给定一个整数数组 nums 和一个目标值 target，
     * 在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
     *
     * @Author: zhengyongxina
     * @Date: 2020/7/19 20:12
     * @param nums
     * @param target
     * @return: int[]
     */
    public int[] twoSum(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{ map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

}
