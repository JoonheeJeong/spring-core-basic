package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext aac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("빈 타입 조회할 때 같은 타입에 해당되는 빈이 2개 이상이면 예외 발생")
    void findBeanByTypeDuplicate_Fail() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> aac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("빈 타입 조회할 때 같은 타입에 해당되는 빈이 2개 이상이더라도 이름을 인자로 함께 넘기면 정상 조회")
    void findBeanByNameAndTypeDuplicate_Success() {
        MemberRepository memberRepository = aac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemoryMemberRepository.class);
    }


    @Test
    @DisplayName("특정 타입 모두 조회")
    void findAllBeansByType() {
        Map<String, MemberRepository> beansOfType = aac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet())
            System.out.printf("name=%s, bean=%s\n", key, beansOfType.get(key));
        System.out.println("beansOfType: " + beansOfType);
        assertThat(beansOfType).size().isEqualTo(2);
    }

    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

}
