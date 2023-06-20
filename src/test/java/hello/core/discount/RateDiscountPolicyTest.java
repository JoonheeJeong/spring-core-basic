package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인 적용")
    void vip_discount() {
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when
        int discountPrice = rateDiscountPolicy.discount(member, 10000);

        // then
        assertEquals(1000, discountPrice);
    }

    @Test
    @DisplayName("NORMAL은 할인 미적용")
    void normal_discount() {
        // given
        Member member = new Member(1L, "memberA", Grade.NORMAL);

        // when
        int discountPrice = rateDiscountPolicy.discount(member, 10000);

        // then
        assertEquals(0, discountPrice);
    }

}