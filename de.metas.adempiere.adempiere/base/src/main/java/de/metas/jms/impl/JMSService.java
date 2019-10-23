package de.metas.jms.impl;

import javax.annotation.Nullable;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.db.CConnectionAttributes;
import org.slf4j.Logger;

import de.metas.jms.EmbeddedActiveMQBrokerService;
import de.metas.jms.IJMSService;
import de.metas.jms.JmsConstants;
import de.metas.util.StringUtils;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
public class JMSService implements IJMSService
{
	private static final transient Logger logger = JmsConstants.getLogger(JMSService.class);

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

		final String user = JmsConstants.getJmsUser();
		final String password = JmsConstants.getJmsPassword();
		final ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory(user, password, brokerURL);
		return jmsConnectionFactory;
	}

	@Override
	public void updateConfiguration()
	{
		synchronized (JMSService.class)
		{
			EmbeddedActiveMQBrokerService.INSTANCE.updateConfiguration_EmbeddedBroker();
			updateConfiguration_JMSConnectionFactory();
		}
	}

	/** Updates configuration of released connection factories */
	private final void updateConfiguration_JMSConnectionFactory()
	{
		// TODO: implement
	}

	@Override
	public String getJmsURL(@Nullable final CConnection cConnection)
	{
		final String appsHost;
		final int appsPort;

		if (Adempiere.isUnitTestMode())
		{
			// CConnection is not yet up, when this method is first called in embedded-server-mode,
			// and we would run into a cyclic problem when we tried to invoke CConnection.get().
			// Also, in unit test mode, we don't want to get in the way of CConnection.get either.
			appsHost = "localhost";
			appsPort = CConnection.SERVER_DEFAULT_APPSERVER_PORT;
		}
		else
		{
			final CConnection cConnectionToUse = cConnection == null ? CConnection.get() : cConnection;
			if (CConnectionAttributes.APPS_HOST_None.equals(cConnectionToUse.getAppsHost()))
			{
				// case: app is running in a local dev-environment, thus the "MyAppsServer" appsHost value
				appsHost = "localhost"; // https://github.com/metasfresh/metasfresh/issues/4436
			}
			else
			{
				appsHost = cConnectionToUse.getAppsHost();
			}
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
