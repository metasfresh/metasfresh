/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.core;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.core.authorizationmf.MetasfreshAuthorizationTokenNotifier;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static de.metas.camel.externalsystems.core.authorizationmf.FromMFAuthorizationRouteBuilder.CUSTOM_FROM_MF_ROUTE_ID;
import static de.metas.camel.externalsystems.core.authorizationmf.ToMFAuthorizationRouteBuilder.CUSTOM_TO_MF_ROUTE_ID;

@Configuration
public class AppConfiguration
{
	private final ApplicationContext context;
	private final CamelContext camelContext;

	public AppConfiguration(
			final ApplicationContext context,
			final CamelContext camelContext)
	{
		this.context = context;
		this.camelContext = camelContext;
	}

	@Bean
	public ProducerTemplate producerTemplate()
	{
		return camelContext.createProducerTemplate();
	}

	@PostConstruct
	public void auditEventNotifier()
	{
		// note that calling producerTemplate() here does *not* mean wh create an additional instance. See https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans for details
		camelContext.getManagementStrategy()
				.addEventNotifier(new AuditEventNotifier(producerTemplate()));
	}

	@PostConstruct
	public void authorizationTokenNotifier()
	{
		final String defaultAuthToken = context.getEnvironment().getProperty(ExternalSystemCamelConstants.MF_API_AUTHORIZATION_TOKEN_PROPERTY);
		// note that calling producerTemp8late() here does *not* mean wh create an additional instance. See https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans for details
		camelContext.getManagementStrategy()
				.addEventNotifier(new MetasfreshAuthorizationTokenNotifier(context, producerTemplate(), defaultAuthToken));
	}

	@Bean
	CamelContextConfiguration contextConfiguration()
	{
		return new CamelContextConfiguration()
		{
			@Override
			public void beforeApplicationStart(final CamelContext camelContext)
			{
				camelContext.setAutoStartup(false);
			}

			@Override
			public void afterApplicationStart(final CamelContext camelContext)
			{
				try
				{
					camelContext.getRouteController().startRoute(CUSTOM_TO_MF_ROUTE_ID);
					camelContext.getRouteController().startRoute(CUSTOM_FROM_MF_ROUTE_ID);

					context.getBean(ProducerTemplate.class)
							.sendBody("direct:" + CUSTOM_TO_MF_ROUTE_ID, "trigger external system authentication for metasfresh!");

					// context.getBean(ProducerTemplate.class)
					// 		.sendBody("direct:" + HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID, "trigger rest api handler!");
				}
				catch (final Exception e)
				{
					throw new RuntimeException("Failed to start custom authorization routes!", e);
				}
			}
		};
	}
}
