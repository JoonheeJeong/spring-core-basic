package singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberRepository memberRepository1 = ac.getBean("memberRepository", MemberRepository.class);
        System.out.println("memberRepository -> memberRepository1 : " + memberRepository1);

        MemberServiceImpl memberService = (MemberServiceImpl) ac.getBean("memberService", MemberService.class);
        MemberRepository memberRepository2 = memberService.getMemberRepository();
        System.out.println("memberService -> memberRepository2 : " + memberRepository2);

        OrderServiceImpl orderService = (OrderServiceImpl) ac.getBean("orderService", OrderService.class);
        MemberRepository memberRepository3 = orderService.getMemberRepository();
        System.out.println("orderService -> memberRepository3 : " + memberRepository3);

        assertThat(memberRepository1).isSameAs(memberRepository2);
        assertThat(memberRepository2).isSameAs(memberRepository3);
    }
}
