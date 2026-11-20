package br.com.rag.talkwithdocs.ingestion.application;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IngestionStateManagerTest {


    private IngestionStateManager ingestionStateManager;


    @BeforeEach
    void setup(){
        this.ingestionStateManager = new IngestionStateManager();
    }

    @Test
    void given_notReadyStatus_canChangeToProcessing(){
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.PROCESSING);
    }

    @Test
    void given_notReadyStatus_canNotChangeToReady(){
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.READY);
        Assertions.assertThat(statusChanged).isFalse();
    }

    @Test
    void given_notReadyStatus_canNotChangeToFaield(){
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.FAILED);
        Assertions.assertThat(statusChanged).isFalse();
    }

    @Test
    void given_processingStatus_canChangeToReady(){
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Boolean isChangedTodReady = ingestionStateManager.changeStatus(IngestionStatus.READY);
        Assertions.assertThat(isChangedTodReady).isTrue();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.READY);
    }

    @Test
    void given_processingStatus_canChangeToFailed(){
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Boolean isChangedToFailed = ingestionStateManager.changeStatus(IngestionStatus.FAILED);
        Assertions.assertThat(isChangedToFailed).isTrue();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.FAILED);
    }

    @Test
    void given_processingStatus_canNotChangeToNotReady(){
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Boolean isChangedTodReady = ingestionStateManager.changeStatus(IngestionStatus.NOT_READY);
        Assertions.assertThat(isChangedTodReady).isFalse();
    }

}