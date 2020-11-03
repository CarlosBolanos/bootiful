package srb.bootstrap.bootiful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ReactorDebugAgent.init();
        System.setProperty("spring.profiles.active", "prod");
        SpringApplication.run(Application.class, args);
    }
}
