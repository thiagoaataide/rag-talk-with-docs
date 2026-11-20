package br.com.rag.talkwithdocs.ingestion.application;

import java.util.Arrays;
import java.util.List;

public enum IngestionStatus {
    NOT_READY,
    PROCESSING(NOT_READY),
    READY(PROCESSING),
    FAILED(PROCESSING);

    IngestionStatus(IngestionStatus... previousStatus) {
        this.previousStatus = Arrays.asList(previousStatus);
    }

    private final List<IngestionStatus> previousStatus;

    public boolean canChangeTo(IngestionStatus status) {
        return previousStatus.contains(status);
    }

}
