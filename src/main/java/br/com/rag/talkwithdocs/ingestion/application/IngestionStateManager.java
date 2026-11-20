package br.com.rag.talkwithdocs.ingestion.application;


import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class IngestionStateManager {

    private final AtomicReference<IngestionStatus> statusRef = new AtomicReference<>(IngestionStatus.NOT_READY);

    public IngestionStatus getStatus() {
        return statusRef.get();
    }

    public Boolean changeStatus(IngestionStatus newStatus) {
        try {
            IngestionStatus statusAtual = this.statusRef.get();

            if (statusAtual.equals(IngestionStatus.NOT_READY) && newStatus.canChangeTo(statusAtual)) {
                return change(newStatus, statusAtual);
            }

            if(newStatus.canChangeTo(statusAtual)){
                return change(newStatus, statusAtual);
            }

            return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private boolean change(IngestionStatus newStatus, IngestionStatus statusAtual) {
        return this.statusRef.compareAndSet(statusAtual, newStatus);
    }
}
