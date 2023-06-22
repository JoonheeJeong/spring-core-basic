package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext aac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름과 타입으로 조회")
    void findBeanByNameAndType() {
        MemberService memberService = aac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }

    @Test
    @DisplayName("빈 이름만으로 조회")
    void findBeanByName() {
        Object bean = aac.getBean("memberService");
        assertThat(bean).isInstanceOf(MemberService.class);
    }

    @Test
    @DisplayName("빈 이름 없이 타입만으로 조회")
    void findBeanByType() {
        MemberService memberService = aac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }

    @Test
    @DisplayName("빈 이름과 구현 객체 타입으로 조회")
    void findBeanByNameAndImplType() {
        MemberService memberService = aac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("해당되는 이름을 가지는 빈이 없을 때 빈 이름으로 조회 실패")
    void findBeanByName_Fail() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> aac.getBean("xxxx"));
    }


}
