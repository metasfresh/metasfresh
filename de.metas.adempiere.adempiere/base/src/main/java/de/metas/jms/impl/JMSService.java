package de.metas.jms.impl;

import java.net.URI;
import java.util.logging.Level;

import javax.jms.ConnectionFactory;

import org.adempiere.util.StringUtils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.util.CLogger;

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
	private BrokerService embeddedBroker = null;

	@Override
	public ConnectionFactory createConnectionFactory()
	{
		// Use "failover". In this way, ActiveMQ will handle all connection issues.
		// See http://activemq.apache.org/failover-transport-reference.html
		final String brokerURL = "failover://" + getJmsURL(null);

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
	public void startEmbeddedBrocker()
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
		final CLogger logger = JmsConstants.getLogger();
		try
		{
			embeddedBroker = new BrokerService();

			final String urlStr = getJmsURL(null);
			final TransportConnector connector = new TransportConnector();

			connector.setUri(new URI(urlStr));
			embeddedBroker.addConnector(connector);
			embeddedBroker.start();
			logger.log(Level.CONFIG, "Embedded JMS broker started on URL " + urlStr);
		}
		catch (final Exception e)
		{
			logger.log(Level.SEVERE, "Failed starting JMS broker", e);
		}
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
			final CConnection cConnectionToUse =
					cConnection == null ? CConnection.get() : cConnection;
			appsHost = cConnectionToUse.getAppsHost();
			appsPort = cConnectionToUse.getAppsPort();
		}

		final String jmsUrl = StringUtils.formatMessage(JmsConstants.TCP_HOSTNAME_61616, appsHost, Integer.toString(appsPort));
		JmsConstants.getLogger().log(Level.INFO, "Assuming JMS-broker runs on appsHost => returning JmsURL={0}", jmsUrl);
		return jmsUrl;

		// final String jmsURL = Services.get(ISysConfigBL.class).getValue(JmsConstants.SYSCONFIG_JMS_URL, ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
		// Check.assumeNotEmpty(jmsURL, "sysconfig '{0}' shall not be empty", JmsConstants.SYSCONFIG_JMS_URL);
		// return jmsURL;
	}
}
