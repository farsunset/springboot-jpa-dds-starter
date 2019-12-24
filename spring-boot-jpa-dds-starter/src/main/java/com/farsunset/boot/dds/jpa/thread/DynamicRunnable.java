package com.farsunset.boot.dds.jpa.thread;

public  class DynamicRunnable implements Runnable {

    private Runnable command;
    private String sourceKey;
    DynamicRunnable(Runnable command,String sourceKey){
        this.sourceKey = sourceKey;
        this.command = command;
    }

    @Override
    public void run() {
        SourceThreadLocal.setSourceKey(sourceKey);

        Thread thread = Thread.currentThread();
        thread.setName(thread.getName() + "-" + sourceKey);

        try {
            command.run();
        } finally {
            SourceThreadLocal.clearSourceKey();
        }
    }

}
