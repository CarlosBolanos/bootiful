package srb.bootstrap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

@Log4j2
public class AsyncApiIntegrationTest {
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void async() {
        Flux<Integer> integers = Flux.create(emitter -> this.launch(emitter, 5));
        StepVerifier.create(integers.doFinally(signalType -> this.executorService.shutdown())).expectNextCount(5)
                .verifyComplete();
    }

    public void launch(FluxSink<Integer> integerFluxSink, int count) {
        log.info("launch {}", integerFluxSink, count);

        this.executorService.submit(() -> {
            log.info("submit");
            var integer = new AtomicInteger();
            log.info("integer", integer);

            while (integer.get() < count) {
                double random = Math.random();
                log.info("random {}", random);
                integerFluxSink.next(integer.incrementAndGet());
                this.sleep((long) random * 1_000);
            }
        });
    }

    public void sleep(long s) {
        try {
            Thread.sleep(s);
        } catch (Exception ex) {
            log.error(ex);
        }
    }
}