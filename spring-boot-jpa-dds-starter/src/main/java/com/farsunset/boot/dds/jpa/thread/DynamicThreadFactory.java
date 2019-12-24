package com.farsunset.boot.dds.jpa.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public   class DynamicThreadFactory implements ThreadFactory {

    private String threadName;
    DynamicThreadFactory(String threadName){
        this.threadName = threadName;
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setName(threadName+thread.getId());
        thread.setPriority(Thread.MAX_PRIORITY);
        return thread;
    }
}
