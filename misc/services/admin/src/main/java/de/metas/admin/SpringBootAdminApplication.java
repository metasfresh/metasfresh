package de.metas.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import io.micrometer.core.lang.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class SpringBootAdminApplication
{
	public static void main(final String[] args)
	{
		SpringApplication.run(SpringBootAdminApplication.class, args);
	}

	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter
	{
		@Override
		protected void configure(@NonNull final HttpSecurity http) throws Exception
		{
			http.authorizeRequests().anyRequest().permitAll()
					.and().csrf().disable();
		}
	}
}