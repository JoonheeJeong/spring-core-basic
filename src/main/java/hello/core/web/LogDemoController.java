package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @GetMapping("log-demo")
    public String logDemo(HttpServletRequest req) {
        myLogger.setRequestURL(req.getRequestURL().toString());

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
