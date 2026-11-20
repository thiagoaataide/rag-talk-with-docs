package br.com.rag.talkwithdocs.ingestion.application;

import br.com.rag.talkwithdocs.ingestion.api.IngestionResponse;
import org.springframework.stereotype.Service;

@Service
public class DocumentIngestionService {

    public IngestionResponse ingest() {
        return new IngestionResponse("READY", 0, 0, 0);
    }

}
