package de.metas.migration.cli;

import java.util.Properties;

import de.metas.migration.cli.PropertiesFileLoader.CantLoadPropertiesException;
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
 * This class gets the "rollout package"'s version string out of the "build.version" file.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
public class RolloutVersionLoader
{
	public static final String BUILD_INFO_FILENAME = "build-info.properties";

	public static final String PROP_VERSION = "build.version";

	@NonNull
	private final PropertiesFileLoader propertiesFileLoader;

	/**
	 * Invokes our {@link PropertiesFileLoader} to load the {@link #BUILD_INFO_FILENAME} from the given {@code dirName} and returns it.
	 *
	 * @param dirName
	 * @return
	 *
	 * @throws CantGetRolloutVersionStringException
	 */
	public String loadRolloutVersionString(@NonNull final String dirName)
	{
		try
		{
			final Properties buildInfo = propertiesFileLoader.loadFromFile(dirName, BUILD_INFO_FILENAME);
			final String rolloutVersionStr = buildInfo.getProperty(PROP_VERSION);
			return rolloutVersionStr;
		}
		catch (final CantLoadPropertiesException e)
		{
			throw new CantGetRolloutVersionStringException(e);
		}
	}

	public static final class CantGetRolloutVersionStringException extends RuntimeException
	{
		private static final long serialVersionUID = -7869876695610886103L;

		private CantGetRolloutVersionStringException(final CantLoadPropertiesException e)
		{
			super("Unable to get our own version. Hint: provide the build.version file or disable both version-check and the version-update at the start and end of the tool", e);
		}
	}

}
