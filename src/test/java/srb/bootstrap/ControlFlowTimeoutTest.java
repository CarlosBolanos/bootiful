package srb.bootstrap;

import java.time.Duration;

import org.junit.Test;
import org.junit.Assert;

import io.netty.handler.timeout.TimeoutException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ControlFlowTimeoutTest {

    @Test
    public void timeout() throws Exception {
        Flux<Integer> ids = Flux.just(1, 2, 3).delayElements(Duration.ofMillis(1)).timeout(Duration.ofMillis(500))
                .onErrorResume(this::given);
        StepVerifier.create(ids).expectNext(0).verifyComplete();
    }

    private Flux<Integer> given(Throwable t) {
        Assert.assertTrue("this exception should be a " + TimeoutException.class.getName(),
                t instanceof TimeoutException);
        return Flux.just(0);
    }
}
