package br.com.rag.talkwithdocs.ingestion.application;


import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class IngestionStateManager {

    private final AtomicReference<IngestionStatus> statusRef = new AtomicReference<>(IngestionStatus.NOT_READY);

    public IngestionStatus getStatus() {
        return statusRef.get();
    }

    public IngestionStatus changeStatus(IngestionStatus newStatus){
        try{
            this.statusRef.compareAndSet(this.statusRef.get(), newStatus);
            return this.statusRef.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
