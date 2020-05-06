package de.metas.procurement.webui;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.vaadin.spring.i18n.MessageProvider;
import org.vaadin.spring.i18n.ResourceBundleMessageProvider;

import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.boot.internal.VaadinServletConfiguration;

/*
 * #%L
 * de.metas.procurement.webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories
// Make config here (vaadinServlet) override stuff in VaadinServletConfiguration
@Import(VaadinServletConfiguration.class)
public class Application
{
	public static final void main(final String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private ApplicationContext context;

	private static Application instance;

	public Application()
	{
		super();

		instance = this;
	}

	public static final ApplicationContext getContext()
	{
		return instance.context;
	}

	public static final void autowire(final Object bean)
	{
		getContext().getAutowireCapableBeanFactory().autowireBean(bean);
	}

	@Bean
	public VaadinServlet vaadinServlet()
	{
		return new SpringAwareTouchKitServlet();
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
			public void customize(final ConfigurableEmbeddedServletContainer servletContainer)
			{
				final TomcatEmbeddedServletContainerFactory tomcatContainerFactory = (TomcatEmbeddedServletContainerFactory)servletContainer;
				tomcatContainerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer()
				{
					@Override
					public void customize(final Connector connector)
					{
						final AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>)connector.getProtocolHandler();
						httpProtocol.setCompression("on");
						httpProtocol.setCompressionMinSize(256);
						final StringBuffer mimeTypes = new StringBuffer(httpProtocol.getCompressableMimeType());
						if(!mimeTypes.toString().contains("MediaType.APPLICATION_JSON_VALUE"))
						{
							mimeTypes.append(","+MediaType.APPLICATION_JSON_VALUE);
						}
						if(!mimeTypes.toString().contains("application/javascript"))
						{
							mimeTypes.append(",application/javascript");
						}
						httpProtocol.setCompressableMimeType(mimeTypes.toString());
					}
				});
			}
		};
	}

	@Bean
	MessageProvider messageProvider()
	{
		return new ResourceBundleMessageProvider("messages");
	}

	/** @return default task executor used by {@link Async} calls */
	@Bean
	public TaskExecutor taskExecutor()
	{
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(100);
		return executor;
	}
}
