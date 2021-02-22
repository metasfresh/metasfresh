package de.metas.adempiere.addon.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import org.compiere.Adempiere;
import org.slf4j.Logger;

import de.metas.adempiere.addon.IAddOn;
import de.metas.adempiere.addon.IAddonStarter;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

public final class AddonStarter implements IAddonStarter
{
	public static final String PROPS_RESOURCE = "/addons.properties";

	private static final Logger logger = LogManager.getLogger(AddonStarter.class);

	/**
	 * Shall we log a WARNING if the {@link #PROPS_RESOURCE} is missing.
	 */
	public static boolean warnIfPropertiesFileMissing = true;

	private final Properties props;

	public AddonStarter()
	{
		props = new Properties();
		try
		{
			//
			// Try to get the URL first (more for friendly error reporting, in case we don't find it)
			final URL url = Adempiere.class.getResource(PROPS_RESOURCE);
			if (url == null)
			{
				if (warnIfPropertiesFileMissing)
				{
					logger.error("No properties file was found for " + PROPS_RESOURCE);
				}
				else
				{
					logger.info("No properties file was found for " + PROPS_RESOURCE);
				}
				return;
			}
			logger.info("Loading addons from " + url);

			//
			// Actually load the resource from stream
			final InputStream propsIn = Adempiere.class.getResourceAsStream(PROPS_RESOURCE);
			if (propsIn != null)
			{
				props.load(propsIn);
				return;
			}
		}
		catch (IOException e)
		{
			MetasfreshLastError.saveError(logger, "Tried to load addon props from resource " + PROPS_RESOURCE, e);
		}
		logger.info("Resource file '" + PROPS_RESOURCE + "' couldn't not be loaded. Addons won't be started.");
	};

	AddonStarter(final Properties props)
	{
		this.props = props;
	}

	@Override
	public void startAddons()
	{
		final ArrayList<String> keys = new ArrayList<>();
		for (final Object key : props.keySet())
		{
			keys.add((String)key);
		}
		Collections.sort(keys);

		for (final Object addonName : keys)
		{
			final String addonClass = (String)props.get(addonName);

			logger.info("Starting addon " + addonName + " with  class " + addonClass);
			startAddon(addonClass);
		}
	}

	@Override
	public Properties getAddonProperties()
	{
		return props;
	}

	private static void startAddon(final String className)
	{
		try
		{
			final Class<?> clazz = Class.forName(className);

			final Class<? extends IAddOn> clazzVC = clazz
					.asSubclass(IAddOn.class);

			final IAddOn instance = clazzVC.newInstance();
			instance.beforeConnection();

		}
		catch (ClassNotFoundException e)
		{
			MetasfreshLastError.saveError(logger, "Addon not available: " + className, e);
		}
		catch (ClassCastException e)
		{
			MetasfreshLastError.saveError(logger, "Addon class " + className + " doesn't implement " + IAddOn.class.getName(), e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

}
