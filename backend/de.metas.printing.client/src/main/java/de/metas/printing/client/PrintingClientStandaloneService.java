package de.metas.printing.client;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for the printing client standaalone version
 *
 * @author tsa
 *
 */
public class PrintingClientStandaloneService
{
	private final transient Logger logger = Logger.getLogger(getClass().getName());

	public static void main(final String[] args)
	{
		new PrintingClientStandaloneService().run();
	}

	/**
	 * Thanks to http://stackoverflow.com/questions/3777055/reading-manifest-mf-file-from-jar-file-using-java
	 */
	private void logVersionInfo()
	{
		try
		{
			final Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
			while (resEnum.hasMoreElements())
			{
				try
				{
					final URL url = resEnum.nextElement();
					final InputStream is = url.openStream();
					if (is != null)
					{
						final Manifest manifest = new Manifest(is);
						final Attributes mainAttribs = manifest.getMainAttributes();
						final String version = mainAttribs.getValue("Implementation-Version");
						if (version != null)
						{
							logger.info("Resource " + url + " has version " + version);
						}
					}
				}
				catch (final Exception e)
				{
					// Silently ignore wrong manifests on classpath?
				}
			}
		}
		catch (final IOException e1)
		{
			// Silently ignore wrong manifests on classpath?
		}
	}

	private void run()
	{
		final Context context = Context.getContext();
		context.addSource(new ConfigFileContext());

		logVersionInfo();

		//
		// Start the client
		final PrintingClient client = new PrintingClient();
		client.start();
		final Thread clientThread = client.getDaemonThread();
		try
		{
			clientThread.join();
		}
		catch (final InterruptedException e)
		{
			logger.log(Level.INFO, "Interrupted. Exiting.", e);
			client.stop();
		}
	}
}
