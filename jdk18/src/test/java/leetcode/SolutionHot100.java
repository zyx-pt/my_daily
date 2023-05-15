package leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author yxzheng
 * @Date 2020/7/19 19:58
 */
public class SolutionHot100 {

    @Test
    public void test() {
        int[] ints = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println("1.twoSun:" + Arrays.toString(ints));

        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode listNode = addTwoNumbers(l1, l2);
        System.out.println("2.addTwoNumbers:" + listNode.toString());
    }

    /**
     * @Description: 给定一个整数数组 nums 和一个目标值 target，在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
     * nums: [2, 7, 11, 15]; target: 9
     * @Author: yxzheng
     * @Date 2022/4/1 11:29
     * @param nums
     * @param target
     * @return int[]
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * @Description:
     * @Author: yxzheng
     * @Date 2022/4/1 11:29
     * @param l1
     * @param l2
     * @return leetcode.SolutionHot100.ListNode
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        return result;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }
}
