package com.farsunset.boot.dds.jpa.holder;

import com.farsunset.boot.dds.jpa.text.Text;
import com.farsunset.boot.dds.jpa.thread.SourceThreadLocal;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.util.TxUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class DynamicTransactionManager implements PlatformTransactionManager{

    private ApplicationContext applicationContext;

    public DynamicTransactionManager(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    private PlatformTransactionManager get(){
        String sourceKey = SourceThreadLocal.getSourceKey();
        if (sourceKey == null){
            return applicationContext.getBean(TxUtils.DEFAULT_TRANSACTION_MANAGER,PlatformTransactionManager.class);
        }else {
            return applicationContext.getBean(Text.getTransactionManager(sourceKey),PlatformTransactionManager.class);
        }
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        return get().getTransaction(definition);
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        get().commit(status);
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        get().rollback(status);
    }
}
