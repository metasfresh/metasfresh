package de.metas.document.archive.esb.camel.test;

/*
 * #%L
 * de.metas.document.archive.esb.camel
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
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StandaloneLauncher
{
	public StandaloneLauncher()
	{
		super();
	}

	public void start()
	{
		startActiveMQServer();
		startCamel();

		// Wait forever
		while (true)
		{
			try
			{
				Thread.sleep(Long.MAX_VALUE);
			}
			catch (InterruptedException e)
			{
				System.out.println("Interrupted!");
				break;
			}
		}

	}

	private void startActiveMQServer()
	{
		try
		{
			final BrokerService broker = new BrokerService();
			// broker.getPersistenceAdapter().setDirectory(arg0);

			final TransportConnector connector = new TransportConnector();
			connector.setUri(new URI("tcp://localhost:61616"));
			broker.addConnector(connector);
			broker.start();

			System.out.println("ActiveMQ started");
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot start ActiveMQ server", e);
		}
	}

	private void startCamel()
	{
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF/spring/beans.xml",
				"esb/standalone/props-locations-standalone.xml"
				);
		System.out.println("Application context: " + applicationContext);
		System.out.println("Camel started");
	}

	public static void main(String[] args)
	{
		final StandaloneLauncher launcher = new StandaloneLauncher();
		launcher.start();
	}

}
