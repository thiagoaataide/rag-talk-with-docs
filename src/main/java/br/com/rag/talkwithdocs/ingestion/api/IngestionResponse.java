package br.com.rag.talkwithdocs.ingestion.api;

public record IngestionResponse(
        String status,
        int documentsProcessed,
        int chunksCreated,
        long durationMs
) {
}
