package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeScopeTest {

    @Test
    void prototypeScopeTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeScope.class);
        PrototypeScope prototypeScope1 = ac.getBean(PrototypeScope.class);
        System.out.println("prototypeScope1 = " + prototypeScope1);
        PrototypeScope prototypeScope2 = ac.getBean(PrototypeScope.class);
        System.out.println("prototypeScope2 = " + prototypeScope2);

        assertThat(prototypeScope1).isNotSameAs(prototypeScope2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeScope {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeScope.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("PrototypeScope.close");
        }
    }
}
