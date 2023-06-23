package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AutoAppConfigTest {

    @Test
    void basicComponentScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        assertThatThrownBy(() -> ac.getBean("memberService", MemberService.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Test
    void findAllBeans() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        System.out.println("\n\n===========================\n\n");

        System.out.println("beanDefinitionCount(): " + ac.getBeanDefinitionCount());
        Arrays.stream(ac.getBeanDefinitionNames())
                .forEach(beanDefinitionName
                        -> System.out.println("beanDefinitionName: " + beanDefinitionName));

        System.out.println("\n\n===========================\n\n");

        assertThat(ac.getBean("memoryMemberRepository")).isNotNull();
        assertThatThrownBy(() -> ac.getBean("memberRepository"))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

}
