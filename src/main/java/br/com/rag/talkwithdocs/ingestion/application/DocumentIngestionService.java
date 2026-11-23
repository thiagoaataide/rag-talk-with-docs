package br.com.rag.talkwithdocs.ingestion.application;

import br.com.rag.talkwithdocs.ingestion.api.IngestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    private final IngestionStateManager ingestionStateManager;

    public IngestionResponse ingest() {
        if(ingestionStateManager.changeStatus(IngestionStatus.PROCESSING)){
            return new IngestionResponse("READY", 0, 0, 0);
        }else {
            return new IngestionResponse("ERROR", 0, 0, 0);
        }
    }

}
