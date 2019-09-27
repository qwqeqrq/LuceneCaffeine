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
            .expireAfterWrite(3, TimeUnit.SECONDS)//十秒
            .maximumSize(100)
            .build();

    private static void initCache() {
        String name = "卢本伟牛逼";
        String key = "name";
        manualCache.put(key, name);
    }

    public static void main(String[] args) throws Exception {
        initCache();//初始化缓存
        int a = 0;
        for (; ; ) {
            Object value = manualCache.getIfPresent("name");
            System.out.println("第" + (a + 1) + "秒缓存中的值为:" + Optional.ofNullable(value).orElse("null").toString());
            Thread.sleep(1000);//延迟一秒
            a++;
            if (a == 5) break;
        }
    }
}
