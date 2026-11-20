package br.com.rag.talkwithdocs.ingestion.application;


import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class IngestionStateManager {

    private IngestionStateManager() {
    }

    private final IngestionStatus status = IngestionStatus.NOT_READY;

    public static AtomicReference<IngestionStateManager> GET_INSTANCE () {
        return new AtomicReference<>(new IngestionStateManager());
    }

}
