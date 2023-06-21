package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext aac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("슈퍼 타입으로 조회하면 자손이 둘 이상 존재할 경우 예외 발생")
    void findByParentTypeBeanDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> aac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("특정 서브 타입의 이름과 슈퍼 타입으로 조회")
    void findByNameAndSuperTypeBean() {
        DiscountPolicy rateDiscountPolicy = aac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);

        DiscountPolicy fixDiscountPolicy = aac.getBean("fixDiscountPolicy", DiscountPolicy.class);
        assertThat(fixDiscountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 서브 타입으로 조회")
    void findBySubType() {
        DiscountPolicy rateDiscountPolicy = aac.getBean(RateDiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);

        DiscountPolicy fixDiscountPolicy = aac.getBean(FixDiscountPolicy.class);
        assertThat(fixDiscountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 슈퍼 타입으로 모두 조회")
    void findAllBeansBySuperType() {
        Map<String, DiscountPolicy> beansOfType = aac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType).size().isEqualTo(2);
        for (String key : beansOfType.keySet())
            System.out.printf("name=%s, bean=%s\n", key, beansOfType.get(key));
        System.out.println("beansOfType: " + beansOfType);
    }

    @Test
    @DisplayName("Object 타입으로 모두 조회")
    void findAllBeansByObject() {
        Map<String, Object> beansOfType = aac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet())
            System.out.printf("name=%s, bean=%s\n", key, beansOfType.get(key));
        System.out.println("beansOfType: " + beansOfType);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}