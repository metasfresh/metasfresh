package de.metas.printing.test.integration;

/*
 * #%L
 * de.metas.printing.esb.camel
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.net.URI;
import java.util.Arrays;

import org.adempiere.process.rpl.imp.IMP_Processors_StandaloneLauncher;
import org.adempiere.util.Check;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Camel+ActiveMQ server - Standalone Launcher
 * 
 * @author tsa
 *
 */
public class StandaloneLauncher
{
	private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
	private static final String[] CAMEL_CONTEXTS = new String[]
		{
				"META-INF/spring/properties-standalone.xml",
				"META-INF/spring/beans.xml"
		};
	private static final String ADEMPIERE_PropertyFile = "c:/workspaces//de.metas.endcustomer./Adempiere.properties_tsa";

	private boolean startADempiereServer = false;

	public static void main(final String[] args)
	{
		final StandaloneLauncher launcher = new StandaloneLauncher();
		launcher.start();
	}

	private StandaloneLauncher()
	{
		super();
	}

	public void start()
	{
		System.out.println("ActiveMQ URL: " + ACTIVEMQ_URL);
		System.out.println("Camel Contexts: " + Arrays.asList(CAMEL_CONTEXTS));

		startActiveMQServer();
		startCamel();
		startAdempiereServer();

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

	private void startAdempiereServer()
	{
		if (!startADempiereServer)
		{
			System.out.println("SKIP Starting ADempiere Import Processors.");
			return;
		}
		System.out.println("Starting ADempiere Import Processors...");

		//
		// Config: PropertyFile
		String propertyFile = System.getProperty("PropertyFile");
		if (Check.isEmpty(propertyFile, true))
		{
			propertyFile = ADEMPIERE_PropertyFile;
			System.setProperty("PropertyFile", propertyFile);
		}
		System.out.println("PropertyFile: " + propertyFile);

		//
		// Start ADempiere
		Env.getSingleAdempiereInstance(null).startup(RunMode.BACKEND);

		//
		// Start Import Processors
		final IMP_Processors_StandaloneLauncher impProcessorsLauncher = new IMP_Processors_StandaloneLauncher();
		impProcessorsLauncher.start();
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

			System.out.println("ActiveMQ started");
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot start ActiveMQ server", e);
		}
	}

	private void startCamel()
	{
		try (final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(CAMEL_CONTEXTS);)
		{
			System.out.println("Application context: " + applicationContext);
			System.out.println("Camel started");
		}
	}
}
