package Generic;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class ParamTypeTest {
    @Test
    public void test1() {
        ArrayList scores = new ArrayList();
        scores.add(78);
        scores.add(95);
        scores.add("Tom");

        int sum = 0, cnt = 0;
        for (Object score: scores) {
            // 需要判断类型且强行转换
            if (score instanceof Integer) {
                sum += (Integer) score;
                cnt++;
            }
        }
        System.out.println("平均成绩：" + sum*1.0/cnt);
    }

    @Test
    public void test2() {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(78);
        scores.add(95);
        // 放入其他类型，编译会报错
        // scores.add("Tom");

        int sum = 0, cnt = 0;
        // 迭代器也是可以指定泛型的
        Iterator<Integer> iterator = scores.iterator();
        while(iterator.hasNext()) {
            sum += iterator.next();
            cnt++;
        }
        System.out.println("平均成绩：" + sum*1.0/cnt);
    }
}
