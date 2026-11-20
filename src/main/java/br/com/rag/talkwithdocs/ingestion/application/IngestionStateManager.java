package br.com.rag.talkwithdocs.ingestion.application;


import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class IngestionStateManager {

    private static final IngestionStatus status = IngestionStatus.NOT_READY;
    private static final AtomicReference<IngestionStatus> statusRef = new AtomicReference<>(status);

    public IngestionStatus getStatus() {
        return statusRef.get();
    }
}
