package br.com.rag.talkwithdocs.ingestion.application;


import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class IngestionStateManager {

    private IngestionStateManager() {
    }

    private static final IngestionStatus status = IngestionStatus.NOT_READY;

    public static AtomicReference<IngestionStatus> getStatus() {
        return new AtomicReference<>(status);
    }

}
