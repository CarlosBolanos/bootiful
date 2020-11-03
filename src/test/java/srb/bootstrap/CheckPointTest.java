package srb.bootstrap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Log4j2
public class CheckPointTest {
    @Test
    public void checkpoint() {
        var stackTrace = new AtomicReference<String>();
        var checkout = Flux.error(new IllegalArgumentException("Oops!")).checkpoint()
                .delayElements(Duration.ofMillis(1));

        StepVerifier.create(checkout).expectErrorMatches(ex -> {
            stackTrace.set(stackTraceToString(ex));
            return ex instanceof IllegalArgumentException;
        }).verify();

        Assert.assertTrue(stackTrace.get().contains("Error has been observed at the following site(s):"));
    }

    private String stackTraceToString(Throwable throwable) {
        try (var sw = new StringWriter(); var pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        } catch (Exception ioEx) {
            throw new RuntimeException(ioEx);
        }
    }
}
