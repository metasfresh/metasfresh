package de.metas.migration.cli.rollout_migrate;

import java.io.File;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.NonNull;

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
 * This class loads the local settings properties file and return a {@link DBConnectionSettingProperties} instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
class SettingsLoader
{
	private static final transient Logger logger = LoggerFactory.getLogger(SettingsLoader.class);

	@NonNull
	private final DirectoryChecker directoryChecker;

	@NonNull
	private final PropertiesFileLoader propertiesFileLoader;

	public DBConnectionSettingProperties loadSettings(@NonNull final RolloutMigrationConfig config)
	{
		try
		{
			final DBConnectionSettingProperties settings;
			if (config.getDataBaseSettingsFile() != null)
			{
				final File settigsFileAsSpecified = new File(config.getDataBaseSettingsFile());
				if (settigsFileAsSpecified.exists())
				{
					logger.info("Settings file {} found", settigsFileAsSpecified);
					settings = new DBConnectionSettingProperties(setSettingsFile(null, config.getDataBaseSettingsFile()));
				}
				else
				{
					final File settingsDir = directoryChecker.checkDirectory("roloutDir", config.getRolloutDirName());
					logger.info("Settings file {} not found; trying with rollout dir={}", settigsFileAsSpecified, settingsDir);

					settings = new DBConnectionSettingProperties(setSettingsFile(settingsDir, config.getDataBaseSettingsFile()));
				}
			}
			else
			{
				logger.info("No settings file specified; looking for");
				final File settingsDir = directoryChecker.checkDirectory("user.home", System.getProperty("user.home"));
				settings = new DBConnectionSettingProperties(setSettingsFile(settingsDir, RolloutMigrationConfig.DEFAULT_SETTINGS_FILENAME));
			}
			return settings;
		}
		catch (Exception e)
		{
			throw new CantLoadSettingsException(e);
		}
	}

	private Properties setSettingsFile(final File dir, @NonNull final String settingsFilename)
	{
		final Properties fileProperties;
		try
		{
			if (dir != null)
			{
				fileProperties = propertiesFileLoader.loadFromFile(dir, settingsFilename);
			}
			else
			{
				fileProperties = propertiesFileLoader.loadFromFile(new File(settingsFilename));
			}
		}
		catch (final Exception e)
		{
			throw new CantLoadSettingsException(e);
		}

		//
		// fallback: be nice to old settings files that contains "ADEMPIERE_" settings.
		final Properties additionalProps = new Properties();
		for (final String key : fileProperties.stringPropertyNames())
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

		logger.info("Loaded settings from file {}", new File(dir, settingsFilename));
		return fileProperties;
	}

	public static class CantLoadSettingsException extends RuntimeException
	{
		private static final long serialVersionUID = 7752453224987748740L;

		private CantLoadSettingsException(final Exception e)
		{
			super("Unable to load the settings file. It shall be a properties file that looks as follows:\n\n" +
					"METASFRESH_DB_SERVER=<DBMS hostname> # e.g. localhost\n" +
					"METASFRESH_DB_PORT=<DBMS hostname> # e.g. 5432\n" +
					"METASFRESH_DB_NAME=<DB name> # e.g. metasfresh\n" +
					"METASFRESH_DB_USER=<DB user name> # e.g. metasfresh\n" +
					"METASFRESH_DB_PASSWORD=<pasword>\n", e);
		}
	}

}
