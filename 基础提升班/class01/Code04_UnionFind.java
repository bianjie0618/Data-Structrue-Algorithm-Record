package 基础提升班.class01;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code04_UnionFind {

	public static class Element<V> {
		public V value;

		public Element(V value) {
			this.value = value;
		}

	}

	// 并查集要求一开始就给出链表的数据结构，然后进行转换
	public static class UnionFindSet<V> {
		public HashMap<V, Element<V>> elementMap;
		public HashMap<Element<V>, Element<V>> fatherMap;
		public HashMap<Element<V>, Integer> rankMap;

		public UnionFindSet(List<V> list) {
			elementMap = new HashMap<>();
			fatherMap = new HashMap<>();
			rankMap = new HashMap<>(); // 记录 代表节点 下挂载了多少子节点（包括自己），非代表节点不做记录
			for (V value : list) {
				Element<V> element = new Element<V>(value);
				elementMap.put(value, element);
				fatherMap.put(element, element);
				rankMap.put(element, 1);
			}
		}

		private Element<V> findHead(Element<V> element) {
			// 下面的代码附有优化并查集的过程，目的是使链条不要过长，从而实现平均的查找的时间复杂度是O(1)
			// 功在当代，利在千秋
			Stack<Element<V>> path = new Stack<>();
			while (element != fatherMap.get(element)) {
				path.push(element);
				element = fatherMap.get(element);
			}
			while (!path.isEmpty()) {
				fatherMap.put(path.pop(), element);
			}
			return element;
		}

		public boolean isSameSet(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
			}
			return false;
		}

		public void union(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				Element<V> aF = findHead(elementMap.get(a));
				Element<V> bF = findHead(elementMap.get(b));
				if (aF != bF) {
					Element<V> big = rankMap.get(aF) >= rankMap.get(bF) ? aF : bF;
					Element<V> small = big == aF ? bF : aF;
					fatherMap.put(small, big); // 把小的挂到大的上，其实也更新了小数目的头节点的value值，之前是<small,small>
					rankMap.put(big, rankMap.get(aF) + rankMap.get(bF));
					rankMap.remove(small);
				}
			}
		}
	}
}
