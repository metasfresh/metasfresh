package de.metas.edi.esb.tools;

/*
 * #%L
 * de.metas.edi.esb
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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.junit.Ignore;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.metas.edi.esb.route.imports.XLSOLCandRoute;

/**
 * Starts EDI ESB standalone server.
 * 
 * To be used in development environment. Can be executed directly, without a custom configured eclipse launcher.
 * 
 * @author tsa
 *
 */
@Ignore
public class EDI_StandaloneLauncher
{
	private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
	private static final String[] CAMEL_ContextConfigLocations = new String[] {
			"META-INF/spring/properties-standalone.xml",
			"META-INF/spring/beans.xml"
	};

	public static void main(String[] args)
	{
		final EDI_StandaloneLauncher launcher = new EDI_StandaloneLauncher();
		launcher.start();
	}

	public void start()
	{
		startActiveMQServer();
		startCamel();

		System.out.println("DONE! Waiting for requests...");

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
			connector.setUri(new URI(ACTIVEMQ_URL));
			broker.addConnector(connector);
			broker.start();

			System.out.println("ActiveMQ started and listens to " + ACTIVEMQ_URL);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot start ActiveMQ server", e);
		}
	}

	private void startCamel()
	{
		// NOTE: we assume we use SLF4J-JUL adapter
		Logger.getLogger(XLSOLCandRoute.ROUTE_ID).setLevel(Level.INFO);

		System.out.println("Camel Context Config Locations: " + Arrays.asList(CAMEL_ContextConfigLocations));
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(CAMEL_ContextConfigLocations);

		System.out.println("Camel Application context: " + applicationContext);
		System.out.println("Camel started");
	}
}
