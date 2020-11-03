package srb.bootstrap;

import org.junit.Test;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FlatMapTest {

    @Test
    public void flatMap() {
        Flux<Integer> data = Flux.just(new Pair(1, 300), new Pair(2, 200), new Pair(3, 100))
                .flatMap(id -> this.delayReplyFor(id.id, id.delay));
        StepVerifier.create(data).expectNext(3, 2, 1).verifyComplete();
    }

    private Flux<Integer> delayReplyFor(Integer i, long delay) {
        return Flux.just(i).delayElements(Duration.ofMillis(delay));
    }

    @AllArgsConstructor
    static class Pair {
        private int id;
        private long delay;
    }
}
