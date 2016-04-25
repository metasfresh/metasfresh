package org.adempiere.util.test;

/*
 * #%L
 * de.metas.document.archive.esb.ait
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.net.URI;

import org.adempiere.exceptions.AdempiereException;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

/**
 * Simple JMS (ActiveMQ) server implementation
 * 
 * @author tsa
 * 
 */
public class JMSServer
{
	private static JMSServer instance = new JMSServer();

	public static JMSServer getInstance()
	{
		return instance;
	}

	private BrokerService broker;

	public JMSServer()
	{
		broker = new BrokerService();

		try
		{
			// Don't store the queue on disk, we are in test mode
			broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot setup broker " + broker, e);
		}

	}

	public void start(final String serverUrl)
	{
		try
		{
			TransportConnector connector = broker.getTransportConnectorByName(serverUrl);
			if (connector == null)
			{
				connector = createTransportConnector(serverUrl);
				broker.addConnector(connector);
			}

			if (!broker.isStarted())
			{
				broker.start();
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot start connector for " + serverUrl, e);
		}
	}

	public void stop(final String serverUrl)
	{
		TransportConnector connector = broker.getTransportConnectorByName(serverUrl);
		if (connector == null)
		{
			return;
		}

		try
		{
			connector.stop();
			broker.removeConnector(connector);
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot stop connector for " + serverUrl, e);
		}
	}

	private TransportConnector createTransportConnector(final String serverUrl)
	{
		try
		{
			final TransportConnector connector = new TransportConnector();
			connector.setName(serverUrl);
			connector.setUri(new URI(serverUrl));
			return connector;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot create transport connector for " + serverUrl, e);
		}
	}
}
