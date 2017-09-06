package de.metas.migration.cli;

import java.io.File;
import java.io.IOException;

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
 * This class checks if given directories are OK.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DirectoryChecker
{
	/**
	 *
	 * @param name just for context/debuggin/loggin
	 * @param dir the directory to be checked
	 *
	 * @return the absolute directory
	 */
	public File checkDirectory(@NonNull final String name, @NonNull final String dir)
	{
		return checkDirectory(name, new File(dir));
	}

	public File checkDirectory(@NonNull final String name, @NonNull final File dir)
	{
		if (!dir.exists())
		{
			throw new IllegalArgumentException(name + " '" + dir + "' does not exist");
		}

		final File dirAbs;
		try
		{
			dirAbs = dir.getCanonicalFile();
		}
		catch (final IOException e)
		{
			throw new IllegalArgumentException(name + " '" + dir + "' is not accessible", e);
		}

		if (!dirAbs.isDirectory())
		{
			throw new IllegalArgumentException(name + " '" + dirAbs + "' is not a directory");
		}
		if (!dirAbs.canRead())
		{
			throw new IllegalArgumentException(name + " '" + dirAbs + "' is not readable");
		}

		return dirAbs;
	}
}
