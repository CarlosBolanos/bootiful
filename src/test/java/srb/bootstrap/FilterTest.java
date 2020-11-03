package srb.bootstrap;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FilterTest {

    @Test
    public void take() {
        Flux<Integer> range = Flux.range(0, 1000).take(5);
        Flux<Integer> filter = range.filter(i -> i % 2 == 0);
        StepVerifier.create(filter).expectNext(0, 2, 4).verifyComplete();
    }

}
