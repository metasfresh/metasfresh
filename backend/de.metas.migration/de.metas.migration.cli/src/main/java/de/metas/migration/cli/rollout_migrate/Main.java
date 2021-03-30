package de.metas.migration.cli.rollout_migrate;

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

import lombok.NonNull;

/**
 * The "main" class that sets up and calls {@link RolloutMigrate} to do the actual work.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class Main
{
	public static void main(final String[] args)
	{
		final CommandlineParams commandlineParams = new CommandlineParams();
		final RolloutMigrationConfig config = commandlineParams.init(args);

		main(config);
	}

	/**
	 * Alternative main method to be called directly from other code, rather than the command-line.
	 * Allows to run the toll without the presence of a settings-file
	 */
	public static void main(@NonNull final RolloutMigrationConfig config)
	{
		final DirectoryChecker directoryChecker = new DirectoryChecker();
		final PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader(directoryChecker);
		final SettingsLoader settingsLoader = new SettingsLoader(directoryChecker, propertiesFileLoader);

		final RolloutVersionLoader rolloutVersionLoader = new RolloutVersionLoader(propertiesFileLoader);

		final DBConnectionMaker dbConnectionMaker = new DBConnectionMaker();

		final DBVersionGetter dbVersionGetter = new DBVersionGetter(dbConnectionMaker);

		final MigrationScriptApplier migrationScriptApplier = new MigrationScriptApplier(directoryChecker, dbConnectionMaker);

		final RolloutMigrate rolloutMigrate = new RolloutMigrate(
				directoryChecker,
				propertiesFileLoader,
				settingsLoader,
				rolloutVersionLoader,
				dbConnectionMaker,
				dbVersionGetter,
				migrationScriptApplier);

		if (config.isCanRun())
		{
			rolloutMigrate.run(config);
		}
	}
}
