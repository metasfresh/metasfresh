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
import de.metas.camel.externalsystems.core.authorization.MetasfreshAuthorizationTokenNotifier;
import de.metas.camel.externalsystems.core.authorization.provider.MetasfreshAuthProvider;
import de.metas.common.util.Check;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static de.metas.camel.externalsystems.core.authorization.CustomMessageToMFRouteBuilder.CUSTOM_TO_MF_ROUTE_ID;

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

	@Bean
	public CustomRouteController customRouteController()
	{
		return new CustomRouteController(this.camelContext);
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
		final String metasfreshAPIBaseURL = context.getEnvironment().getProperty(ExternalSystemCamelConstants.MF_API_BASE_URL_PROPERTY);

		if (Check.isBlank(metasfreshAPIBaseURL))
		{
			throw new RuntimeException("Missing mandatory property! property = " + ExternalSystemCamelConstants.MF_API_BASE_URL_PROPERTY);
		}

		final MetasfreshAuthProvider metasfreshAuthProvider = context.getBean(MetasfreshAuthProvider.class);
		final CustomRouteController customRouteController = customRouteController();
		final ProducerTemplate producerTemplate = producerTemplate();

		camelContext.getManagementStrategy()
				.addEventNotifier(new MetasfreshAuthorizationTokenNotifier(metasfreshAuthProvider, metasfreshAPIBaseURL, customRouteController, producerTemplate));
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
				customRouteController().startAlwaysRunningRoutes();

				producerTemplate()
						.sendBody("direct:" + CUSTOM_TO_MF_ROUTE_ID, "Trigger external system authentication!");
			}
		};
	}
}
