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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.metas.printing.client.util.Util;

/**
 * Reads the config properties from a file which is determined by "config" system property.
 *
 * @author tsa
 *
 */
public class ConfigFileContext implements IContext
{
	private final Properties properties;

	public ConfigFileContext()
	{
		String configFilename = System.getProperty("config");

		// Fallback: use config.properties
		if (configFilename == null || configFilename.trim().isEmpty())
		{
			configFilename = new File(".", "config.properties").getAbsolutePath();
		}

		properties = new Properties();

		//
		// Load file
		InputStream in = null;
		try
		{
			in = new FileInputStream(configFilename);
			properties.load(in);
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Failed loading config file: " + configFilename + ", start client with -Dconfig=<filename> so specify a particualr file", e);
		}
		finally
		{
			Util.close(in);
			in = null;
		}
	}

	@Override
	public String getProperty(final String name)
	{
		final String property = properties.getProperty(name);
		if (property != null)
		{
			// we don't want things to be screwed up by trailing white spaces in our properties file, so we trim.
			// further reading: https://docs.oracle.com/cd/E23095_01/Platform.93/ATGProgGuide/html/s0204propertiesfileformat01.html
			// "The property value is generally terminated by the end of the line. White space following the property value is not ignored, and is treated as part of the property value."
			return property.trim();
		}
		return property;
	}

}
