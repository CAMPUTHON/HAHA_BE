package dgu.camputhon.global.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm healthy!";
    }

    @GetMapping("/hi")
    public ApiResponse<?> hi() {
        return ApiResponse.onSuccess("hi");
    }
}
