package srb.bootstrap.bootiful;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;

public class CustomErrorController implements ErrorController {

    @Override
    @GetMapping("/error")
    public String getErrorPath() {
        return "error";
    }

}
