package com.lucene.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;


public class CaffeineTest {

    // 基于固定的到期策略进行退出
   /* LoadingCache<Key, Graph> graphs = Caffeine.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(key -> createExpensiveGraph(key));
    LoadingCache<Key, Graph> graphs = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(key -> createExpensiveGraph(key));
*/

    // 要初始化自定义策略，我们需要实现 Expiry 接口
    public static LoadingCache<String, Object> graphs = Caffeine.newBuilder()
            .expireAfter(new Expiry<String, Object>() {
                @Override
                public long expireAfterCreate(String key, Object value, long currentTime) {
                    return 3000000000L*20;//一分钟
                }

                @Override
                public long expireAfterUpdate(String key, Object value, long currentTime, long currentDuration) {
                    return currentDuration;//修改后就应该减少时间
                }

                @Override
                public long expireAfterRead(String key, Object value, long currentTime, long currentDuration) {
                    return currentDuration;
                }
            }).build(key -> new AtomicIntegerArray(2));

    public static void main(String[] args) throws Exception {
        String name = "1231241";
        String key = "name";
        graphs.put(key, name);
        System.out.println("最开始的时间"+System.currentTimeMillis());
        Thread.sleep(1000*30);
        graphs.put(key, name);
        Thread.sleep(1000);
        graphs.put(key, name);
        System.out.println("最后续的时间"+System.currentTimeMillis());
        int a = 0;
        for (; ; ) {
            Object value = graphs.getIfPresent("name");
            System.out.println("第" + (a + 1) + "秒缓存中的值为:" + Optional.ofNullable(value).orElse("null").toString()+"===="+System.currentTimeMillis());
            Thread.sleep(1000);//延迟一秒
            a++;
            if (a == 60) break;
        }
    }
}
