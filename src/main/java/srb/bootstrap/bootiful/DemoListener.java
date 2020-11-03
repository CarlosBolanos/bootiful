package srb.bootstrap.bootiful;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import srb.bootstrap.CustomerService;
import srb.bootstrap.Demo;

@Component
@Profile("dev")
public class DemoListener {
    private final CustomerService customerService;

    DemoListener(CustomerService customerService) {
        this.customerService = customerService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void excercise() {
        Demo.workWithCustomerService(getClass(), this.customerService);
    }
}
