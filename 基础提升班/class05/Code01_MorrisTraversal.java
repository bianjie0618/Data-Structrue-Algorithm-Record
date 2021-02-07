package 基础提升班.class05;

public class Code01_MorrisTraversal {
	
	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// Morris遍历的空间复杂度是O(1)，因为利用了原树中大量空闲指针的方式
	// 如果一个节点没有左子树，那么Morris遍历的方式只能到达该节点一次，如果有左子树，那么可达到该节点的2次
	// 对比递归栈的方式，可以发现，利用递归的方式，无论是前序、中序、还是后序遍历，到达该节点的次数都是 3次

	/**
	 * Morris遍历细节
	 * 假设来到当前节点cur，开始时cur来到头节点位置
	 * 1）如果cur没有左孩子，cur向右移动(cur = cur.right)
	 * 2）如果cur有左孩子，找到左子树上最右的节点mostRight：
	 * 		a.如果 mostRight的右指针指向空，让其指向cur， 然后cur向左移动(cur = cur.left)
	 * 		b.如果 mostRight的右指针指向cur，让其指向null， 然后cur向右移动(cur = cur.right)
	 * 3）cur为空时遍历停止
	 * 可以发现，如果某一节点有左子树，则会到达该节点两次，否则，只会到达该节点一次
	 * @param head
	 */
	public static void morris(Node head){
		if(head == null)
			return;
		Node cur = head;
		Node mostRight = null;
		while(cur != null){
			mostRight = cur.left;	// mostRight是cur左孩子
			if(mostRight != null){	// 有左子树
				while(mostRight.right != null && mostRight.right != cur){
					mostRight = mostRight.right;
				}
				// mostRight变成了cur左子树上最右的节点
				if(mostRight.right == null){ // 表明这是第一次来到cur
					mostRight.right = cur;
					cur = cur.left;
					continue;
				}else{	// mostRight.right == cur
					mostRight.right = null;
				}
			}
			cur = cur.right;
		}
	}

	public static void morrisIn(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head;
		Node cur2 = null;
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				} else {
					cur2.right = null;
				}
			}
			System.out.print(cur1.value + " ");
			cur1 = cur1.right;
		}
		System.out.println();
	}

	public static void morrisPre(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head;
		Node cur2 = null;
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				if (cur2.right == null) {
					cur2.right = cur1;
					System.out.print(cur1.value + " ");
					cur1 = cur1.left;
					continue;
				} else {
					cur2.right = null;
				}
			} else {
				System.out.print(cur1.value + " ");
			}
			cur1 = cur1.right;
		}
		System.out.println();
	}

	public static void morrisPos(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head;
		Node cur2 = null;
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				} else {
					cur2.right = null;
					printEdge(cur1.left);
				}
			}
			cur1 = cur1.right;
		}
		printEdge(head);
		System.out.println();
	}

	/**
	 * 利用 Morris进行【后续遍历】，方法是对于只经过一次的节点，什么也不做；对于经过两次的节点，第一次经过不操作，第二次逆序操纵(打印)
	 * 左子树的右边界，注意：不包括操作(打印)第二次经过的节点本身
	 * 最后，打印"整棵树"的右边界
	 * @param head
	 */
	public static void printEdge(Node head) {
		Node tail = reverseEdge(head);
		Node cur = tail;
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		reverseEdge(tail);
	}

	public static Node reverseEdge(Node from) {
		Node pre = null;
		Node next = null;
		while (from != null) {
			next = from.right;
			from.right = pre;
			pre = from;
			from = next;
		}
		return pre;
	}

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		Node head = new Node(4);
		head.left = new Node(2);
		head.right = new Node(6);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.right.left = new Node(5);
		head.right.right = new Node(7);
		printTree(head);
		morrisIn(head);
		morrisPre(head);
		morrisPos(head);
		printTree(head);

	}

}
