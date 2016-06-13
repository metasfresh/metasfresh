package de.metas.ui.web.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import com.vaadin.spring.boot.internal.VaadinServletConfiguration;

import de.metas.ui.web.json.JsonHelper;

/*
 * #%L
 * de.metas.ui.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
@ComponentScan
// @ServletComponentScan({ "de.metas", "org.adempiere" })
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableConfigurationProperties
// Make config here (vaadinServlet) override stuff in VaadinServletConfiguration
@Import(VaadinServletConfiguration.class)
public class VaadinClientApplication
{
	public static final String PROFILE_NAME_TESTING = "testing";

	private static ConfigurableApplicationContext context;

	public static final void main(final String[] args)
	{
		context = new SpringApplicationBuilder(VaadinClientApplication.class)
				.run(args);
	}

	public static final void autowire(final Object bean)
	{
		context.getAutowireCapableBeanFactory().autowireBean(bean);
	}

	/**
	 * Configure embedded tomcat so that is use gzip for various resources.
	 */
	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer()
	{
		return new EmbeddedServletContainerCustomizer()
		{
			@Override
			public void customize(ConfigurableEmbeddedServletContainer servletContainer)
			{
				final TomcatEmbeddedServletContainerFactory tomcatContainerFactory = (TomcatEmbeddedServletContainerFactory)servletContainer;
				tomcatContainerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer()
				{
					@Override
					public void customize(Connector connector)
					{
						final AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>)connector.getProtocolHandler();
						httpProtocol.setCompression("on");
						httpProtocol.setCompressionMinSize(256);
						String mimeTypes = httpProtocol.getCompressableMimeTypes();
						String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE + ",application/javascript";
						httpProtocol.setCompressableMimeTypes(mimeTypesWithJson);
					}
				});
			}
		};
	}

	/**
	 * Returns <code>true</code> if the testing profile is active.<br>
	 * Activate it by adding <code>spring.profiles.include=testing</code> to the application properties.
	 * <p>
	 * Thx to http://stackoverflow.com/questions/9267799/how-do-you-get-current-active-default-environment-profile-programatically-in-spr
	 *
	 * @return
	 */
	public static boolean isTesting()
	{
		final ApplicationContext context = VaadinClientApplication.context;
		if (context == null)
		{
			return false; // guard against NPE
		}
		final Environment environment = context.getEnvironment();
		if (environment == null)
		{
			return false; // guard against NPE
		}
		return environment.acceptsProfiles(PROFILE_NAME_TESTING);
	}
	
	@Bean
	public RestTemplate restTemplate()
	{
		final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new MappingJackson2HttpMessageConverter(JsonHelper.createObjectMapper()));
		final RestTemplate restTemplate = new RestTemplate(messageConverters);

		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(2000);
		requestFactory.setConnectTimeout(2000);
		restTemplate.setRequestFactory(requestFactory);

		return restTemplate;
	}
	
	@Bean
	public TaskScheduler taskScheduler()
	{
		return new ThreadPoolTaskScheduler();
	}
}
