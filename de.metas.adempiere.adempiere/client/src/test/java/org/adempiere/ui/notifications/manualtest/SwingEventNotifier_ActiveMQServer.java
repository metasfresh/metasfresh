package org.adempiere.ui.notifications.manualtest;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.junit.Ignore;

@Ignore
public class SwingEventNotifier_ActiveMQServer
{
	//private static final String url = "tcp://localhost:61616";
	private static final String url = "tcp://0.0.0.0:61616";

	public static void main(String[] args)
	{
		//
		// Start ActiveMQ server
		try
		{
			final BrokerService broker = new BrokerService();

			final TransportConnector connector = new TransportConnector();
			connector.setUri(new URI(url));
			broker.addConnector(connector);
			broker.start();

			System.out.println("ActiveMQ started on " + connector.getUri());
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot start ActiveMQ server", e);
		}
	}
}
