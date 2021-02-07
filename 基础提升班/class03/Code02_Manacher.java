package 基础提升班.class03;

// Manacher算法：俗称马拉车回文子串算法
public class Code02_Manacher {

	// 字符串的处理，加上虚构的字符 # 从而将字符串转化为奇数个字符(2n+1)
	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	public static int maxLcpsLength(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] charArr = manacherString(str);
		int[] pArr = new int[charArr.length]; // 记录每个位置的回文半径数组
		int C = -1;	// 过去所记录的最大回文右边界 R-1 所对应的回文中心
		int R = -1;	// 过去所记录的最大回文右边界 的下一个字符位置，即有效最大右边界是截止到 R-1
		int max = Integer.MIN_VALUE;
		for (int i = 0; i != charArr.length; i++) {
			// 下面这句话表达：以当前位置为中心的回文子串至少可以达到的长度
			// L 是以 C为中心的过去所记录的最大回文右边界所对应的左边界
			// 如果 R <= i, 此时暴力向两侧扩展、
			// 如果 R > i, 以 C 为中心可以找到 i 位置的对称点 i'，并且可以查询到 回文半径数组中记录的i'位置的
			// 回文半径数值。则分为三种情况：
			// 第一种情况，i'的回文范围在[L,R]内，则此时i的会问范围可以在O(1)的时间复杂度内求出，并且
			// i位置的回文半径等于i’位置的回文半径；
			// 第二种情况是：i'的回文范围已经超出了[L,R]，此时i的回文半径为R-i+1，同样是在O(1)的时间复杂度内求出
			// 第三种情况是：i'的会稳定范围压线[L,R]，此时i的回文半径至少是i'的回文半径，在>=R位置处需要继续验证扩展

			// 所以在R>i时，i位置的回文半径至少是 pArr[i] = Math.min(pArr[2*C-i],R-i)
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

			while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
				if (charArr[i + pArr[i]] == charArr[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			max = Math.max(max, pArr[i]);
		}
		return max - 1;
	}

	public static void main(String[] args) {
		String str1 = "abc1234321ab";
		System.out.println(maxLcpsLength(str1));
	}

}
