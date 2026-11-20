package br.com.rag.talkwithdocs.ingestion.application;

public class IngestionStateManager {

    private IngestionStatus status = IngestionStatus.NOT_READY;

    public IngestionStatus getStatus() {
        return status;
    }

}
