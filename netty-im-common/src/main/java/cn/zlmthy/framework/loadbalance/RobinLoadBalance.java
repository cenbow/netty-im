package cn.zlmthy.framework.loadbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 轮训算法
 *
 * @author zengliming
 */
public class RobinLoadBalance {

    private static Integer roundPos = 0;

    private static Integer weightPos = 0;

    private static Lock roundLock = new ReentrantLock();

    private static Lock weightLock = new ReentrantLock();

    /**
     * 随机轮训
     *
     * @param list 轮训的元数据
     * @param <T>  轮训的数据类型
     * @return 轮训得到的数据
     */
    public <T> T roundRobin(List<T> list) {

        if (list == null || list.size() <= 0) {
            return null;
        }
        try {
            roundLock.lock();
            List<T> arrayList = new ArrayList<>(list);
            T result = null;
            if (roundPos >= arrayList.size()) {
                roundPos = 0;
            }
            //轮询+1
            roundPos++;
            return arrayList.get(roundPos);
        } finally {
            roundLock.unlock();
        }
    }


    /**
     * 权重轮训
     *
     * @param maps 权重和元数据
     * @param <T>  轮训的数据类型
     * @return 轮训得到的数据
     */
    public <T> T weightRobin(Map<T, Integer> maps) {

        if (maps == null || maps.keySet().size() <= 0) {
            return null;
        }
        try {
            weightLock.lock();
            List<T> arrayList = new ArrayList<>();
            maps.forEach((k, v) -> {
                for (int i = 0; i < v; i++) {
                    arrayList.add(k);
                }
            });
            if (weightPos >= arrayList.size()) {
                weightPos = 0;
            }
            //轮询+1
            weightPos++;
            return arrayList.get(weightPos);
        } finally {
            weightLock.unlock();
        }
    }

    /**
     * 随机权重轮训
     *
     * @param maps 权重和元数据
     * @param <T>  轮训的数据类型
     * @return 轮训得到的数据
     */
    public <T> T roundWeightRobin(Map<T, Integer> maps) {

        if (maps == null || maps.keySet().size() <= 0) {
            return null;
        }
        List<T> arrayList = new ArrayList<>();
        maps.forEach((k, v) -> {
            for (int i = 0; i < v; i++) {
                arrayList.add(k);
            }
        });
        return arrayList.get(ThreadLocalRandom.current().nextInt(arrayList.size()));
    }
}
