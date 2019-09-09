package com.lucene.analyzer;

import org.apache.lucene.store.RAMDirectory;

public class SignalRamDirectory {

    /**
     * 创建一个单例的RAM对象，否则读取的时候会读取不到内存的索引文件
     */
    private static RAMDirectory ramDirectory = new RAMDirectory();
    private SignalRamDirectory(){}

    public static  RAMDirectory getSignalRamDirectory(){
        return ramDirectory;
    }

}
