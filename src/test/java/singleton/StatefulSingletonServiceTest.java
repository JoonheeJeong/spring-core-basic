package singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class StatefulSingletonServiceTest {

    @Test
    @DisplayName("상태를 갖는 싱글톤 서비스")
    void statefulSingletonService() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulSingletonService service1 = ac.getBean("statefulSingletonService", StatefulSingletonService.class);
        StatefulSingletonService service2 = ac.getBean("statefulSingletonService", StatefulSingletonService.class);

        service1.order("userA", 10000);
        service2.order("userB", 20000);

        int price = service1.getPrice();
        assertThat(price).isNotEqualTo(10000);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public StatefulSingletonService statefulSingletonService() {
            return new StatefulSingletonService();
        }
    }
}
