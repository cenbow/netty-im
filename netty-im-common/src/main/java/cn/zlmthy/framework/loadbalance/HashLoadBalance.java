package cn.zlmthy.framework.loadbalance;

import java.util.ArrayList;
import java.util.List;

/**
 * 源地址hash算法
 * @author zengliming
 */
public class HashLoadBalance {

    public static <T> T get(String input, List<T> data) {
        if (input == null || "".equals(input)
                || data == null || data.size() <= 0) {
            return null;
        }
        List<T> list = new ArrayList<>(data);
        int hashCode = input.hashCode();
        return list.get(hashCode % list.size());
    }
}
