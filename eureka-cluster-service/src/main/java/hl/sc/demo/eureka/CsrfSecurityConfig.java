package hl.sc.demo.eureka;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: hl
 * @created: 2019-08-24 14:54
 **/
@EnableWebSecurity
public class CsrfSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        super.configure(http); // 这一句必须要加上的，否则直接关闭密码验证服务了
    }
}