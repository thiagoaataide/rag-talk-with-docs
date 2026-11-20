package br.com.rag.talkwithdocs.ingestion.api;

import br.com.rag.talkwithdocs.ingestion.application.DocumentIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class IngestController {

    private final DocumentIngestionService documentIngestionService;

    @PostMapping("/ingest")
    public ResponseEntity<IngestionResponse> ingesting() {
        IngestionResponse ingest = this.documentIngestionService.ingest();

        return ResponseEntity.status(HttpStatus.OK).body(ingest);
    }
}
