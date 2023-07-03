package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void discountOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);

        assertThat(discountService).isInstanceOf(DiscountService.class);

        Member memberA = new Member(1L, "memberA", Grade.VIP);
        int priceDiscountedByFix = discountService.discount(memberA, 20000, "fixDiscountPolicy");
        assertThat(priceDiscountedByFix).isEqualTo(1000);

        Member memberB = new Member(2L, "memberB", Grade.VIP);
        int priceDiscountedByRate = discountService.discount(memberB, 30000, "rateDiscountPolicy");
        assertThat(priceDiscountedByRate).isEqualTo(3000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> discountPolicyMap;

        public DiscountService(Map<String, DiscountPolicy> discountPolicyMap) {
            this.discountPolicyMap = discountPolicyMap;
            System.out.println("discountPolicyMap = " + discountPolicyMap);
        }

        public int discount(Member member, int price, String discountPolicyCode) {
            DiscountPolicy discountPolicy = discountPolicyMap.get(discountPolicyCode);
            return discountPolicy.discount(member, price);
        }
    }
}
