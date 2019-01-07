package cn.zlmthy.framework.loadbalance;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 最小连接数
 * @author zengliming
 */
public class LessConnectionsLoadBalance {

    private static ConcurrentHashMap<String, Long> count = new ConcurrentHashMap<>();

    public static String get(List<String> ips){
        Optional<String> s = count.keySet().stream().sorted().findFirst();
        if (s.isPresent()){
            Long counts = count.get(s.get());
            count.put(s.get(), counts);
            return s.get();
        }
        return null;
    }
}
