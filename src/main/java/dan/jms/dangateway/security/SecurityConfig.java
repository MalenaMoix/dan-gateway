package dan.jms.dangateway.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()//.csrf().disable();
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**")
                    .hasAnyAuthority("admin")
                .antMatchers(HttpMethod.POST, "/**")
                    .hasAnyAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/**")
					.hasAnyAuthority("admin")
                .antMatchers(HttpMethod.DELETE, "/**")
                    .hasAnyAuthority("admin")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter());
    }
}
