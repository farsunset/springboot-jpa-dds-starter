package com.farsunset.boot.dds.jpa.thread;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DynamicThreadPoolExecutor extends ThreadPoolExecutor {

    public DynamicThreadPoolExecutor(int coreSize,int maxSize,String threadName){
        super(coreSize,
                maxSize,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new DynamicThreadFactory(threadName));
    }

    @Override
    public void execute(Runnable command) {
        super.execute(new DynamicRunnable(command,SourceThreadLocal.getSourceKey()));
    }

    public void execute(Runnable command,String sourceKey) {
        super.execute(new DynamicRunnable(command,sourceKey));
    }

    @Override
    public Future<?> submit(Runnable command) {
       return super.submit(new DynamicRunnable(command,SourceThreadLocal.getSourceKey()));
    }

    public Future<?> submit(Runnable command,String sourceKey) {
        return super.submit(new DynamicRunnable(command,sourceKey));
    }


}
