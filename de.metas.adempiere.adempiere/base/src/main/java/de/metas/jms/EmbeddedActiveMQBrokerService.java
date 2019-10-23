package de.metas.jms;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.slf4j.Logger;

import de.metas.jms.impl.JMSService;
import de.metas.logging.LogManager;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public final class EmbeddedActiveMQBrokerService
{
	public static final EmbeddedActiveMQBrokerService INSTANCE = new EmbeddedActiveMQBrokerService();

	private static final String connectorName = "appsServer";

	private static final Logger logger = LogManager.getLogger(EmbeddedActiveMQBrokerService.class);

	private BrokerService embeddedBroker = null;

	private EmbeddedActiveMQBrokerService()
	{
	}

	/**
	 * Start a local JMS broker. Used when a client is run in "embedded-server-mode" or when a metasfresh-server is not accompanied by an ESR server.<br>
	 * Check if there is already a local JMS broker to which we can connect, and don't attempt creating another one if that is the case.
	 * <p>
	 * Note: this method is thread-safe. Only the first invocation will start a broker. Consecutive invocations will have no effect.
	 */
	public void startEmbeddedBroker()
	{
		synchronized (JMSService.class)
		{
			if (embeddedBroker != null)
			{
				return;
			}
			startEmbeddedBroker0();
		}
	}

	private void startEmbeddedBroker0()
	{
		// try if there is already a broker running, by making a "fail-fast" attempt to connect to it.
		final ConnectionFactory connectionFactory = createFactoryForEmbeddedBroker();
		try
		{
			final Connection conn = connectionFactory.createConnection();
			conn.close();
			logger.warn("Found an embedded JMS broker to which we can connect. Assuming that attempting to create another one would not work. Returning.");
			return;
		}
		catch (JMSException e)
		{
			// a broker is not yet running on this machine. Go on.
		}

		// now actually create the embedded broker
		try
		{
			embeddedBroker = new BrokerService();
			createEmbeddedBrokerConnector();
			embeddedBroker.start();
			logger.info("Embedded JMS broker started");
		}
		catch (final Exception e)
		{
			logger.error("Failed starting JMS broker", e);
		}
	}

	private void createEmbeddedBrokerConnector() throws Exception
	{
		final URI uri = getJmsURI();

		//
		// Remove the old connector if exists and if is not up2date
		final TransportConnector connectorOld = embeddedBroker.getConnectorByName(connectorName);
		if (connectorOld == null)
		{
			// nothing
		}
		else if (Objects.equals(connectorOld.getUri(), uri))
		{
			logger.info("JMS connector has not changed. Skip updating.");
			return;
		}
		else
		{
			logger.info("JMS connector URL has changed. Removing old connector: {}", connectorOld);
			embeddedBroker.removeConnector(connectorOld);
		}

		//
		// Adding the new connector
		final TransportConnector connector = new TransportConnector();
		connector.setName(connectorName);
		connector.setUri(uri);
		embeddedBroker.addConnector(connector);
		logger.info("JMS connector mapped to URL: " + uri);
	}

	private final URI getJmsURI() throws URISyntaxException
	{
		final String urlStr = Services.get(IJMSService.class).getJmsURL(null);
		return new URI(urlStr);
	}

	/** Updates the embedded broker configuration */
	public final void updateConfiguration_EmbeddedBroker()
	{
		if (embeddedBroker == null)
		{
			logger.info("JMS broker not started yet. Doing nothing.");
			return;
		}

		try
		{
			createEmbeddedBrokerConnector();
		}
		catch (Exception e)
		{
			logger.error("Failed updating the JMS connector", e);
		}
	}

	public ConnectionFactory createFactoryForEmbeddedBroker()
	{
		final String brokerURL = Services.get(IJMSService.class).getJmsURL(null);

		// when running in embedded-server-mode, then we don't need usename an password for the JMS broker.
		// also, in this case, it is case, we need the URL before we even have DB access, so we would have trouble obtaining those two.
		final ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
		return jmsConnectionFactory;
	}
}
