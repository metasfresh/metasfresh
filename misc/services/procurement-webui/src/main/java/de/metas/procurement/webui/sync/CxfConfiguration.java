package de.metas.procurement.webui.sync;

import org.springframework.context.annotation.Configuration;

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

/**
 * Currently inactive, with all code commented out. This is for reference. It creates rest-http server endpoints.
 * We currently only support a jms transport and the endpoints for that are all set up in {@link SyncConfiguration}.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Configuration
public class CxfConfiguration
{
	// @formatter:off
//	private static final transient Logger logger = LoggerFactory.getLogger(CxfConfiguration.class);
//
//	@Autowired
//	private ApplicationContext context;
//
//	private static final String DEFAULT_UrlMapping = "/rest/*";
//	@Value("${cxf.urlMapping:}")
//	private String urlMapping = DEFAULT_UrlMapping;
//
//	@Bean
//	public ServletRegistrationBean cxfServletRegistrationBean()
//	{
//		if (Strings.isNullOrEmpty(urlMapping))
//		{
//			logger.info("No urlMapping defined. Using default.");
//			urlMapping = DEFAULT_UrlMapping;
//		}
//
//		logger.info("cxf.urlMapping = {}", urlMapping);
//
//		final ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CXFServlet(), urlMapping);
//		registrationBean.setAsyncSupported(true);
//		registrationBean.setLoadOnStartup(1);
//		registrationBean.setName("CXFServlet");
//		return registrationBean;
//	}
//
//	@Bean
//	public SpringBus cxf()
//	{
//		return new SpringBus();
//	}
//
//	@Bean
//	public Server jaxRsServer()
//	{
//		//
//		// Find the JAX-RS resource providers
//		final List<ResourceProvider> resourceProviders = new LinkedList<ResourceProvider>();
//		for (final String beanName : context.getBeanDefinitionNames())
//		{
//			// Consider only those which are annotated with @Path
//			if (context.findAnnotationOnBean(beanName, Path.class) == null)
//			{
//				continue;
//			}
//
//			// Skip if the bean is annotated with NoCxfServerBind
//			if (context.findAnnotationOnBean(beanName, NoCxfServerBind.class) != null)
//			{
//				logger.info("Skip {} because it's annotated with {}", beanName, NoCxfServerBind.class);
//				continue;
//			}
//
//			final SpringResourceFactory factory = new SpringResourceFactory(beanName);
//			factory.setApplicationContext(context);
//
//			// Skip if the bean instance is null
//			final Object beanInstance = factory.getInstance(null);
//			if (beanInstance == null)
//			{
//				logger.info("Skip {} because there is no instance", beanName);
//				continue;
//			}
//			// Skip if the bean instance is actually a WebClient
//			if (WebClient.client(beanInstance) != null)
//			{
//				logger.info("Skip {} because the instance it's actually a client: {}", beanName, beanInstance);
//				continue;
//			}
//			// Skip if the bean instance is annotated with NoCxfServerBind
//			if (beanInstance.getClass().getAnnotation(NoCxfServerBind.class) != null)
//			{
//				logger.info("Skip {} because the instance it's annotated with {}: {}", beanName, NoCxfServerBind.class, beanInstance);
//				continue;
//			}
//
//			//
//			// We have found a suitable server implementation.
//			// Add it to the list.
//			resourceProviders.add(factory);
//			logger.info("Found CXF resource provider: {} (instance {})", beanName, beanInstance);
//		}
//		if (resourceProviders.isEmpty())
//		{
//			logger.info("No suitable resource providers found. Skip starting the JAX-RS server.");
//			return null;
//		}
//
//		//
//		// Start the JAX-RS server
//		final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
//		factory.setBus(context.getBean(SpringBus.class));
//		factory.setResourceProviders(resourceProviders);
//		return factory.create();
//	}
	// @formatter:on
}
