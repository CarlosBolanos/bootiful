package srb.bootstrap;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

public class ReplayProcesorTest {

    @Test
    public void replayProcesor() {
        int historySize = 2;
        boolean unbounded = false;
        ReplayProcessor<String> processor = ReplayProcessor.create(historySize, unbounded);
        produce(processor.sink());
        consume(processor);
    }

    public void produce(FluxSink<String> sink) {
        sink.next("1");
        sink.next("2");
        sink.next("3");
        sink.complete();
    }

    public void consume(Flux<String> publisher) {
        // this procesor keeps replaying the production sequence,
        // but only returns the last two events of the sequence. (weir.d..)
        StepVerifier.create(publisher).expectNext("2").expectNext("3").verifyComplete();
        StepVerifier.create(publisher).expectNext("2").expectNext("3").verifyComplete();
        StepVerifier.create(publisher).expectNext("2").expectNext("3").verifyComplete();
    }
}
