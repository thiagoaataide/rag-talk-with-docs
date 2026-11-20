package br.com.rag.talkwithdocs.ingestion.application;


import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class IngestionStateManagerTest {

    private final IngestionStateManager ingestionStateManager;

    @Test
    void given_notReadyStatus_shouldChangeToProcessingOnly(){
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);



    }

}