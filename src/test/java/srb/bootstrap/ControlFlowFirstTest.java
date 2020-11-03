package srb.bootstrap;

import java.time.Duration;

import org.junit.Test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Log4j2
public class ControlFlowFirstTest {

    @Test
    public void first() {
        Flux<Integer> slow = Flux.just(1, 2, 3).delayElements(Duration.ofMillis(10));
        Flux<Integer> fast = Flux.just(4, 5, 6, 7).delayElements(Duration.ofMillis(2));
        Flux<Integer> first = Flux.first(slow, fast);
        StepVerifier.create(first).expectNext(4, 5, 6, 7).verifyComplete();
    }
}
