package de.metas.migration.cli;

import java.io.File;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * This class loads the local settings properties file and return a {@link Settings} instance.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
public class SettingsLoader
{
	private static final transient Logger logger = LoggerFactory.getLogger(SettingsLoader.class);

	private final Config config;

	private final DirectoryChecker directoryChecker;

	private final PropertiesFileLoader propertiesFileLoader;

	public Settings loadConfig()
	{
		final File settingsDir;
		if (config.getSettingsFileName() != null)
		{
			settingsDir = directoryChecker.checkDirectory("roloutDir", config.getRolloutDirName());
		}
		else
		{
			settingsDir = directoryChecker.checkDirectory("user.home", System.getProperty("user.home"));
		}
		return new Settings(setSettingsFile(settingsDir, config.getSettingsFileName()));
	}

	private Properties setSettingsFile(final File dir, final String settingsFilename)
	{
		final Properties fileProperties = propertiesFileLoader.loadFromFile(dir, settingsFilename);

		//
		// fallback: be nice to old settings files that contains "ADEMPIERE_" settings.
		final Properties additionalProps = new Properties();
		for (String key : fileProperties.stringPropertyNames())
		{
			if (!key.contains("ADEMPIERE"))
			{
				continue; // nothing to do
			}

			final String newKey = key.replaceAll("ADEMPIERE", "METASFRESH");

			logger.info("The settings file contains the old settings name " + key + ". Acting as if it was the new settings name " + newKey + ".");
			additionalProps.setProperty(newKey, fileProperties.getProperty(key));
		}
		fileProperties.putAll(additionalProps);

		logger.info("Settings file: " + settingsFilename);
		return fileProperties;
	}

}
