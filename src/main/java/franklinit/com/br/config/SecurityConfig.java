package franklinit.com.br.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
       // super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
         log.info(passwordEncoder.encode("test"));
         auth.inMemoryAuthentication(). //user em memoria
                 withUser("teste")
                 .password(passwordEncoder.encode("teste")).roles("USER","ADMIN")
                 .and().withUser("dev").password(passwordEncoder.encode("teste")).roles("USER");
         //super.configure(auth);
    }
}
