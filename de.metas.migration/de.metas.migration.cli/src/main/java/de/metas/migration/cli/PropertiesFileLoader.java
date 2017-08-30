package de.metas.migration.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
 * This class loads {@link Properties} from a given file.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
public class PropertiesFileLoader
{
	@NonNull
	private final DirectoryChecker directoryChecker;

	public Properties loadFromFile(@NonNull final String dir, @NonNull final String filename)
	{
		return loadFromFile(new File(dir), filename);
	}

	public Properties loadFromFile(@NonNull final File dir, @NonNull final String filename)
	{
		final File settingsFile = new File(
				directoryChecker.checkDirectory(filename, dir),
				filename);

		final Properties fileProperties = new Properties();

		try (final FileInputStream in = new FileInputStream(settingsFile);)
		{
			fileProperties.load(in);
		}
		catch (final IOException e)
		{
			throw new CantLoadPropertiesException("Cannot load " + settingsFile, e);
		}
		return fileProperties;
	}

	public static final class CantLoadPropertiesException extends RuntimeException
	{
		private static final long serialVersionUID = 4240250517349321980L;

		public CantLoadPropertiesException(@NonNull final String msg, @NonNull final Exception e)
		{
			super(msg, e);
		}
	}
}
