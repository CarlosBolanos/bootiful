package srb.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.EmitterProcessor;

import org.junit.Test;
import junit.framework.Assert;

public class HotStreamTest1 {

    @Test
    public void hot() throws Exception {
        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        EmitterProcessor<Integer> emitter = EmitterProcessor.create(2);
        FluxSink<Integer> sink = emitter.sink();
        emitter.subscribe(collect(first));

        sink.next(1);
        sink.next(2);

        emitter.subscribe(collect(second));

        sink.next(3);
        sink.complete();

        Assert.assertTrue(first.size() > second.size());
    }

    Consumer<Integer> collect(List<Integer> collection) {
        return collection::add;
    }

}
