package com.example.javademo.m4_juc.Immutable;

import java.util.Arrays;

/**
 * 2022/3/22
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = {0,1,1,2};
        System.out.println(longestConsecutive(arr));
    }

    public static int longestConsecutive(int[] nums) {
        if(nums.length == 0){
            return 0;
        }

        // 先排序
        Arrays.sort(nums);

        int max = 1,curLen = 1;

        for(int i = 1;i < nums.length;i++){
            if (nums[i] == nums[i-1]) {
                continue;
            }
            if(nums[i] - nums[i-1] == 1){
                curLen++;
            }else{
                max = Math.max(max,curLen);
                curLen = 1;
            }

        }
        return Math.max(max,curLen);
    }
}
