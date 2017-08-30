package de.metas.migration.cli;

import de.metas.migration.IDatabase;
import de.metas.migration.IScript;
import de.metas.migration.IScriptsRegistry;
import de.metas.migration.impl.SQLDatabase;
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
 * This class creates {@link IDatabase} instances.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@AllArgsConstructor
public class DBConnectionMaker
{
	public IDatabase createDb(
			@NonNull final Settings settings,
			@NonNull final String dbName)
	{
		final IDatabase db = new SQLDatabase(
				settings.getDbType(),
				settings.getDbHostname(),
				settings.getDbPort(),
				dbName,
				settings.getDbUser(),
				settings.getDbPassword());
		return db;
	}

	/**
	 * Creates an {@link IDatabase} instance we don't intend to run migration scripts against.
	 * However, connecting to that DB is possible.
	 *
	 * @param dbName
	 * @return
	 */
	public IDatabase createDummyDatabase(
			@NonNull final Settings settings,
			@NonNull final String dbName)
	{
		// return a database that does not check whether our script was applied or not
		return new SQLDatabase(
				settings.getDbType(),
				settings.getDbHostname(),
				settings.getDbPort(),
				dbName,
				settings.getDbUser(),
				settings.getDbPassword())
		{
			// @formatter:off
			@Override
			public IScriptsRegistry getScriptsRegistry()
			{
				return new IScriptsRegistry()
				{
					@Override public void markIgnored(final IScript script) { }
					@Override public void markApplied(final IScript script) { }
					@Override public boolean isApplied(final IScript script) { return false; }
				};
			};
			// @formatter:on
		};
	}

}
