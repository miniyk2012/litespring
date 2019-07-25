package interview;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Integer> list = s.doSomething(8, 504);
        System.out.println(list);
        System.out.println(s.total(list));
    }

    private int total(List<Integer> list) {
        int sum = 0;
        for (Integer num : list) {
            sum += num;
        }
        return sum;
    }

    public List<Integer> doSomething(int m, int n) {
        List<Integer> list = new ArrayList<Integer>();
        int max;
        int rest = n;
        for (int i = 0; i < m; i++) {
            max = rest - (m - i - 1);
            if (i == m - 1) {
                list.add(rest);
            } else {
                int d = rest(max);
                rest = rest - d;
                list.add(d);
            }
        }
        return list;
    }

    public int rest(int max) {
        //这里可以设置最大最小值
        int d = (int) (Math.random() * max) + 1;
        return d;
    }
}