package 基础入门班.class01;

public class Code07_EvenTimesOddTimes {

	public static void printOddTimesNum1(int[] arr) {
		int eO = 0;
		for (int cur : arr) {
			eO ^= cur;
		}
		System.out.println(eO);
	}

	// 有两个数出现了基数次
	public static void printOddTimesNum2(int[] arr) {
		int eO = 0, eOhasOne = 0;
		for (int curNum : arr) {
			eO ^= curNum;
		}
		// 提取最右侧的 1，本质上是补码运算，正数和它的相反数相加，结果为零，按位与的结果是正数最右侧的非零位
		int rightOne = eO & (~eO + 1);
		for (int cur : arr) {
			if ((cur & rightOne) != 0) {
				eOhasOne ^= cur;
			}
		}
		System.out.println(eOhasOne + " " + (eO ^ eOhasOne));
	}

	public static void main(String[] args) {
		int a = 5;
		int b = 7;

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println(a);
		System.out.println(b);

		int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
		printOddTimesNum1(arr1);

		int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
		printOddTimesNum2(arr2);

	}

}
