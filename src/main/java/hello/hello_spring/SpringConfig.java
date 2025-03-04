package hello.hello_spring;

import hello.hello_spring.repository.*;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em){
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // memberRepository는 인터페이스이고, MemoryMemberRepository가 구현체이므로
        //return new MemoryMemberRepository();

        //jdbc
        //return new JdbcMemberRepository(dataSource);

        //jdbcTemplate
        //return new JdbcTemplateMemberRepository(dataSource);

        //jpa
        return new JpaMemberRepository(em);
    }
}
