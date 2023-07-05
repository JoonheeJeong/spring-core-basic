package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest {

    @Test
    void findPrototypeBean() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsingPrototype() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        assertThat(clientBean1.logic()).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        assertThat(clientBean2.logic()).isNotEqualTo(1);
    }

    @Test
    void singletonClientUsingPrototypeProvider() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBeanWithProvider.class);
        ClientBeanWithProvider clientBean1 = ac.getBean(ClientBeanWithProvider.class);
        assertThat(clientBean1.logic()).isEqualTo(1);

        ClientBeanWithProvider clientBean2 = ac.getBean(ClientBeanWithProvider.class);
        assertThat(clientBean2.logic()).isEqualTo(1);
    }

    @Scope("prototype")
    private static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            this.count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

    @RequiredArgsConstructor
    private static class ClientBean {

        private final PrototypeBean prototypeBean;

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @RequiredArgsConstructor
    private static class ClientBeanWithProvider {

//        private final ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;
        private final Provider<PrototypeBean> prototypeBeanProvider;


        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
}
