package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LogDemoController {

    private final Provider<MyLogger> myLoggerProvider;
    private final LogDemoService logDemoService;

    @GetMapping("log-demo")
    public String logDemo(HttpServletRequest req) throws InterruptedException {
        MyLogger myLogger = myLoggerProvider.get();
        myLogger.setRequestURL(req.getRequestURL().toString());

        Thread.sleep(1000);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
