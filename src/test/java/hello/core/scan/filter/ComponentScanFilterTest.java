package hello.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ComponentScanFilterTest {

    @Test
    void componentScanWithFilter() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        assertThatThrownBy(() -> ac.getBean(BeanA.class)).isInstanceOf(NoSuchBeanDefinitionException.class);
        assertThatThrownBy(() -> ac.getBean(BeanB.class)).isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = {
                    @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class),
                    @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class)
            })
    static class TestConfig {
    }
}
