package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class StatelessSingletonServiceTest {

    @Test
    @DisplayName("상태를 갖지 않는 싱글톤 서비스")
    void statefulSingletonService() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatelessSingletonService service1 = ac.getBean("statelessSingletonService", StatelessSingletonService.class);
        StatelessSingletonService service2 = ac.getBean("statelessSingletonService", StatelessSingletonService.class);

        int priceOfUserA = service1.order("userA", 10000);
        int priceOfUserB = service2.order("userB", 20000);

        assertThat(priceOfUserA).isEqualTo(10000);
        assertThat(priceOfUserB).isEqualTo(20000);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public StatelessSingletonService statelessSingletonService() {
            return new StatelessSingletonService();
        }
    }
}