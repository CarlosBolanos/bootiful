package srb.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import junit.framework.Assert;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Log4j2
public class HotStreamTest3 {
    private List<Integer> one = new ArrayList<Integer>();
    private List<Integer> two = new ArrayList<Integer>();
    private List<Integer> three = new ArrayList<Integer>();

    private Consumer<Integer> subscribe(List<Integer> list) {
        return list::add;
    }

    @Test
    public void publish() throws Exception {
        Flux<Integer> pileOn = Flux.just(1, 2, 3).publish().autoConnect(3).subscribeOn(Schedulers.immediate());
        pileOn.subscribe(subscribe(one));
        Assert.assertEquals(this.one.size(), 0);

        pileOn.subscribe(subscribe(two));
        Assert.assertEquals(this.two.size(), 0);

        pileOn.subscribe(subscribe(three));

        Assert.assertEquals(this.one.size(), 3);
        Assert.assertEquals(this.two.size(), 3);
        Assert.assertEquals(this.three.size(), 3);
    }

    @Test
    public void publish2() throws Exception {
        Flux<Integer> pileOn = Flux.just(1, 2, 3).publish().autoConnect(2).subscribeOn(Schedulers.immediate());
        pileOn.subscribe(subscribe(one));
        Assert.assertEquals(this.one.size(), 0);

        pileOn.subscribe(subscribe(two));
        Assert.assertEquals(this.one.size(), 3);
        Assert.assertEquals(this.two.size(), 3);

    }
}
