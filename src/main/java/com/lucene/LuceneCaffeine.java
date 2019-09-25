package com.lucene;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LuceneCaffeine implements CommandLineRunner {

    /**
     * 项目入口启动类
     */
    public static void main(String[] args) {
        SpringApplication.run(LuceneCaffeine.class, args);
        System.out.println("项目启动成功");

    }

    @Override
    public void run(String... args) throws Exception {
        for (; ; ) {
            synchronized (this) {
                wait(100);
                System.out.println("能自动释放锁嘛");
                System.out.println("===========");
            }
        }
    }
}
