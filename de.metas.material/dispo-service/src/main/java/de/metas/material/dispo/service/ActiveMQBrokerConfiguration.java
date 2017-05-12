package de.metas.material.dispo.service;

import java.net.URI;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Embedded ActiveMQ broker configuration
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Configuration
public class ActiveMQBrokerConfiguration
{
	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${mfprocurement.activemq.runEmbeddedBroker:false}")
	private boolean runEmbeddedBroker;

	@Value("${mfprocurement.activemq.embeddedBroker-name:procurement.webui}")
	private String embeddedBrokerName;

	/**
	 * URL of the JMS broker, needed whether we run our own embedded broker or not.<br>
	 * But note that in this class, it is only used if we do start an embedded broker.
	 */
	@Value("${mfprocurement.jms.broker-url:}")
	private String brokerUrl;

	/**
	 * @return embedded ActiveMQ broker or <code>null</code>
	 */
	@Bean
	public BrokerService brokerService() throws Exception
	{
		if (!runEmbeddedBroker)
		{
			logger.info("Skip creating an ActiveMQ broker service");
			return null;
		}

		final BrokerService brokerService = new BrokerService();
		//
		// "client" Connector
		{
			final TransportConnector connector = new TransportConnector();
			connector.setUri(new URI(brokerUrl.trim()));
			brokerService.addConnector(connector);
		}

		brokerService.setBrokerName(embeddedBrokerName);
		brokerService.start();
		logger.info("Embedded JMS broker started on URL " + brokerUrl);
		return brokerService;
	}
}
