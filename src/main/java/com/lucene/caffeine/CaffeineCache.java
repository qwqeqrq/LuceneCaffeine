package com.lucene.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 咖啡因缓存类  手动写入缓存
 */
public class CaffeineCache {

    private static Cache<String, Object> manualCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)//十秒
            .maximumSize(100)
            .build();

    private static void initCache() {
        String name = "卢本伟牛逼";
        String key = "name";
        manualCache.put(key, name);
    }

    public static void main(String[] args) throws Exception {
        String name = "卢本伟牛逼";
        String key = "name";
        System.out.println("最开始的时间"+System.currentTimeMillis());
        CaffeineTest.graphs.put(key, name);
        Thread.sleep(1000*30);
        CaffeineTest.graphs.put(key, name);
        System.out.println("最后续的时间"+System.currentTimeMillis());
        int a = 0;
        for (; ; ) {
            Object value = CaffeineTest.graphs.getIfPresent("name");
            System.out.println("第" + (a + 1) + "秒缓存中的值为:" + Optional.ofNullable(value).orElse("null").toString()+"===="+System.currentTimeMillis());
            Thread.sleep(1000);//延迟一秒
            a++;
            if (a == 60) break;
        }
    }
}
