package de.metas.jms.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.adempiere.util.StringUtils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.slf4j.Logger;

import de.metas.jms.IJMSService;
import de.metas.jms.JmsConstants;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class JMSService implements IJMSService
{
	private static final transient Logger logger = JmsConstants.getLogger(JMSService.class);

	private BrokerService embeddedBroker = null;

	@Override
	public ConnectionFactory createConnectionFactory()
	{
		final boolean createFailOverURL = true;
		return createConnectionFactory(createFailOverURL);
	}

	private ConnectionFactory createConnectionFactory(final boolean createFailoverURL)
	{
		final String brokerURL;
		if (createFailoverURL)
		{
			// Use "failover". In this way, ActiveMQ will handle all connection issues.
			// See http://activemq.apache.org/failover-transport-reference.html
			brokerURL = "failover://" + getJmsURL(null);
		}
		else
		{
			brokerURL = getJmsURL(null);
		}

		if (CConnection.isServerEmbedded())
		{
			// when running in embedded-server-mode, then we don't need usename an password for the JMS broker.
			// also, in this case, it is case, we need the URL before we even have DB access, so we would have trouble obtaining those two.
			final ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
			return jmsConnectionFactory;
		}

		final String user = JmsConstants.getJmsUser();
		final String password = JmsConstants.getJmsPassword();
		final ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory(user, password, brokerURL);
		return jmsConnectionFactory;
	}

	@Override
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
		final boolean createFailoverURL = false;
		final ConnectionFactory connectionFactory = createConnectionFactory(createFailoverURL);
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

	private static final String connectorName = "appsServer";

	@Override
	public void updateConfiguration()
	{
		synchronized (JMSService.class)
		{
			updateConfiguration_EmbeddedBroker();
			updateConfiguration_JMSConnectionFactory();
		}
	}

	/** Updates the embedded broker configuration */
	private final void updateConfiguration_EmbeddedBroker()
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

	/** Updates configuration of released connection factories */
	private final void updateConfiguration_JMSConnectionFactory()
	{
		// TODO: implement
	}

	private final URI getJmsURI() throws URISyntaxException
	{
		final String urlStr = getJmsURL(null);
		return new URI(urlStr);
	}

	@Override
	public String getJmsURL(final CConnection cConnection)
	{

		final String appsHost;
		final int appsPort;

		if (CConnection.isServerEmbedded() || Adempiere.isUnitTestMode())
		{
			// CConnection is not yet up, when this method is first called in embedded-server-mode,
			// and we would run into a cyclic problem when we tried to invoke CConnection.get().
			// Also, in unit test mode, we don't want to get in the way of CConnection.get either.
			appsHost = CConnection.SERVER_EMBEDDED_APPSERVER_HOSTNAME;
			appsPort = CConnection.SERVER_DEFAULT_APPSERVER_PORT;
		}
		else
		{
			final CConnection cConnectionToUse = cConnection == null ? CConnection.get() : cConnection;
			appsHost = cConnectionToUse.getAppsHost();
			appsPort = cConnectionToUse.getAppsPort();
		}

		final String jmsUrl = StringUtils.formatMessage(JmsConstants.TCP_HOSTNAME_PORT, appsHost, Integer.toString(appsPort));
		logger.info("Assuming JMS-broker runs on appsHost => returning JmsURL={}", jmsUrl);
		return jmsUrl;

		// final String jmsURL = Services.get(ISysConfigBL.class).getValue(JmsConstants.SYSCONFIG_JMS_URL, ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
		// Check.assumeNotEmpty(jmsURL, "sysconfig '{}' shall not be empty", JmsConstants.SYSCONFIG_JMS_URL);
		// return jmsURL;
	}
}
