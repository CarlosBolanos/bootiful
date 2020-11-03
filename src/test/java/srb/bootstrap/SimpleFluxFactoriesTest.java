package srb.bootstrap;

import java.util.Date;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.reactivestreams.FlowAdapters;
import org.reactivestreams.Publisher;

import reactor.adapter.JdkFlowAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SimpleFluxFactoriesTest {

    @Test
    public void simple() {
        Publisher<Integer> rangeOfIntegers = Flux.range(0, 10);
        StepVerifier.create(rangeOfIntegers).expectNextCount(10).verifyComplete();

        Flux<String> letters = Flux.just("A", "B", "C");
        StepVerifier.create(letters).expectNext("A", "B", "C").verifyComplete();

        long now = System.currentTimeMillis();
        Mono<Date> greetingMono = Mono.just(new Date(now));
        StepVerifier.create(greetingMono).expectNext(new Date(now)).verifyComplete();

        Mono<Object> empty = Mono.empty();
        StepVerifier.create(empty).verifyComplete();

        Flux<Integer> fromArray = Flux.fromArray(new Integer[] { 1, 2, 3, 4, 5 });
        StepVerifier.create(fromArray).expectNext(1, 2, 3, 4, 5).verifyComplete();

        Flux<Integer> fromIterable = Flux.fromIterable(Arrays.asList(1, 2, 3));
        StepVerifier.create(fromIterable).expectNext(1, 2, 3).verifyComplete();

        AtomicInteger integer = new AtomicInteger();
        Supplier<Integer> supplier = integer::incrementAndGet;
        Flux<Integer> integerFlux = Flux.fromStream(Stream.generate(supplier));
        StepVerifier.create(integerFlux.take(3)).expectNext(1).expectNext(2).expectNext(3).verifyComplete();
    }

    @Test
    public void convert() {
        Flux<Integer> original = Flux.range(0, 10);
        Flow.Publisher<Integer> rangeOfIntegersAsFlow = FlowAdapters.toFlowPublisher(original);
        Publisher<Integer> rangeOfIntegerAsReactiveStream = FlowAdapters.toPublisher(rangeOfIntegersAsFlow);

        StepVerifier.create(original).expectNextCount(10).verifyComplete();
        StepVerifier.create(rangeOfIntegerAsReactiveStream).expectNextCount(10).verifyComplete();

        Flux<Integer> rangeOfIntegersAsReactorFlux = JdkFlowAdapter.flowPublisherToFlux(rangeOfIntegersAsFlow);
        StepVerifier.create(rangeOfIntegersAsReactorFlux).expectNextCount(10).verifyComplete();
    }
}
