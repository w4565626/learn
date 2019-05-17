package com.learn.engineer;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * 进阶:
 * <p>
 * 你能不将整数转为字符串来解决这个问题吗？
 */
public class isPalindrome {
    public static void main(String[] args) {
        boolean b = isPalindrome(123321);
        System.out.println(b);
    }

    public static boolean isPalindrome(int x) {
        boolean result = false;
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int fist = 0;
        while (x > fist) {
            //如果判断反转数字的位数已经达到原始数字位数的一半？
            fist = fist * 10 + x % 10;
            x = x / 10;
        }
        if (fist == x || fist / 10 == x) {
            result = true;
        }
        return result;
    }

    public static boolean isPalindromeByString(int x) {
        boolean result = false;
        if (x < 0) {
            return false;
        }
        String s = String.valueOf(x);
        for (int i = 0; i < s.length() / 2; i++) {
            char head = s.toCharArray()[i];
            char tail = s.toCharArray()[s.length() - i - 1];
            result = head == tail;
        }
        return result;
    }
}
