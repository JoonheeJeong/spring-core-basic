package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonScopeTest {

    @Test
    void singletonScopeTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(SingletonScope.class);
        SingletonScope singletonScope1 = ac.getBean(SingletonScope.class);
        System.out.println("singletonScope1 = " + singletonScope1);
        SingletonScope singletonScope2 = ac.getBean(SingletonScope.class);
        System.out.println("singletonScope2 = " + singletonScope2);

        assertThat(singletonScope1).isSameAs(singletonScope2);

        ac.close();
    }

    @Scope("singleton")
    static class SingletonScope {

        @PostConstruct
        public void init() {
            System.out.println("SingletonScope.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("SingletonScope.close");
        }
    }
}
