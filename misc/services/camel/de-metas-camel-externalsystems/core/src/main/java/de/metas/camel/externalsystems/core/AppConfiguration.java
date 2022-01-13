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

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static de.metas.camel.externalsystems.core.restapi.ExternalSystemRestAPIHandler.HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID;

@Configuration
public class AppConfiguration
{
	private final ApplicationContext context;
	private final CamelContext camelContext;

	public AppConfiguration(final ApplicationContext context, final CamelContext camelContext)
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
		camelContext.getManagementStrategy()
				.addEventNotifier(new AuditEventNotifier(context.getBean(ProducerTemplate.class)));
	}

	@Bean
	CamelContextConfiguration contextConfiguration()
	{
		return new CamelContextConfiguration()
		{
			@Override
			public void beforeApplicationStart(final CamelContext camelContext) {}

			@Override
			public void afterApplicationStart(final CamelContext camelContext)
			{
				context.getBean(ProducerTemplate.class)
						.sendBody("direct:" + HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID,"trigger rest api handler!");
			}
		};
	}
}
