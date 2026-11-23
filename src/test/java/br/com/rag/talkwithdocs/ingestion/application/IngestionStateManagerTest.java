package br.com.rag.talkwithdocs.ingestion.application;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

class IngestionStateManagerTest {


    private IngestionStateManager ingestionStateManager;


    @BeforeEach
    void setup() {
        this.ingestionStateManager = new IngestionStateManager();
    }

    @Test
    void given_notReadyStatus_canChangeToProcessing() {
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.PROCESSING);
    }

    @Test
    void given_notReadyStatus_canNotChangeToReady() {
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.READY);
        Assertions.assertThat(statusChanged).isFalse();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.NOT_READY);
    }

    @Test
    void given_notReadyStatus_canNotChangeToFaield() {
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.FAILED);
        Assertions.assertThat(statusChanged).isFalse();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.NOT_READY);
    }

    @Test
    void given_processingStatus_canChangeToReady() {
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Boolean isChangedTodReady = ingestionStateManager.changeStatus(IngestionStatus.READY);
        Assertions.assertThat(isChangedTodReady).isTrue();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.READY);
    }

    @Test
    void given_processingStatus_canChangeToFailed() {
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Boolean isChangedToFailed = ingestionStateManager.changeStatus(IngestionStatus.FAILED);
        Assertions.assertThat(isChangedToFailed).isTrue();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.FAILED);
    }

    @Test
    void given_processingStatus_canNotChangeToNotReady() {
        IngestionStatus statusAtual = ingestionStateManager.getStatus();
        Assertions.assertThat(statusAtual).isEqualTo(IngestionStatus.NOT_READY);
        Boolean statusChanged = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
        Assertions.assertThat(statusChanged).isTrue();
        Boolean isChangedTodReady = ingestionStateManager.changeStatus(IngestionStatus.NOT_READY);
        Assertions.assertThat(isChangedTodReady).isFalse();
        Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.PROCESSING);
    }

    @Test
    void shouldRunTwoThreads() {
        boolean testeExecutados = false;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch readyLatch = new CountDownLatch(2);
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Boolean> threadSubmmited1 = es.submit(() -> {
                    readyLatch.countDown();
                    startLatch.await();
                    System.out.println("Thread 1 {}: " + ingestionStateManager.getStatus());
                    Boolean isStatusAlterado = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
                    System.out.println("Thread 1 {}: " + ingestionStateManager.getStatus() + " Status: " + isStatusAlterado);
                    return isStatusAlterado;
                }

        );

        Future<Boolean> threadSubmmited2 = es.submit(() -> {
                    readyLatch.countDown();
                    startLatch.await();
                    System.out.println("Thread 2 {}: " + ingestionStateManager.getStatus());
                    Boolean isStatusAlterado = ingestionStateManager.changeStatus(IngestionStatus.PROCESSING);
                    System.out.println("Thread 2 {}: " + ingestionStateManager.getStatus() + " Status: " + isStatusAlterado);
                    return isStatusAlterado;
                }
        );


        try {
            if (readyLatch.await(3, TimeUnit.SECONDS)) {
                startLatch.countDown();
            } else {
                throw new TimeoutException("Timeout waiting for readyLatch");
            }


            List<Boolean> results = List.of(threadSubmmited1.get(8, TimeUnit.SECONDS), threadSubmmited2.get(8, TimeUnit.SECONDS));

            Assertions.assertThat(results).containsExactlyInAnyOrder(false, true);
            Assertions.assertThat(ingestionStateManager.getStatus()).isEqualTo(IngestionStatus.PROCESSING);

            testeExecutados = true;

        } catch (ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        finally {

            if (testeExecutados) {
                es.shutdown();
            } else {
                es.shutdownNow();
            }
        }

    }

}