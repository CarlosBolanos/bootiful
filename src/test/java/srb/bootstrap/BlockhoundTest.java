package srb.bootstrap;

import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@Log4j2
public class BlockhoundTest {
    private final static AtomicBoolean BLOCKHOUND = new AtomicBoolean();

    @Before
    public void before() {
        BLOCKHOUND.set(true);
        var integrations = new ArrayList<BlockHoundIntegration>();
        var services = ServiceLoader.load(BlockHoundIntegration.class);
        services.forEach(integrations::add);

        integrations.add(builder -> builder.blockingMethodCallback(blockingMethod -> {
            if (BLOCKHOUND.get()) {
                throw new BlockingCallError(blockingMethod.toString());
            }
        }));
        BlockHound.install(integrations.toArray(new BlockHoundIntegration[0]));
    }

    private static class BlockingCallError extends Error {
        BlockingCallError(String message) {
            super(message);
        }
    }

    @After
    public void after() {
        BLOCKHOUND.set(false);
    }

    public void notOk() {
        StepVerifier.create(this.buildBlockingMono().subscribeOn(Schedulers.parallel()))
                .expectErrorMatches(e -> e instanceof BlockingCallError).verify();
    }

    public void ok() {
        StepVerifier.create(this.buildBlockingMono().subscribeOn(Schedulers.elastic())).expectNext(1L).verifyComplete();
    }

    Mono<Long> buildBlockingMono() {
        return Mono.just(1L).doOnNext(it -> block());
    }

    @SneakyThrows
    void block() {
        Thread.sleep(1000);
    }
}
