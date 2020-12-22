package de.metas.migration.cli.rollout_migrate;

import com.google.common.collect.ImmutableSet;

import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.applier.impl.NullScriptsApplierListener;
import de.metas.migration.scanner.IFileRef;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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
 * This class holds the config from the command line tool's parameters
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
@Value
public
class RolloutMigrationConfig
{
	public static final String DEFAULT_SETTINGS_FILENAME = "local_settings.properties";

	@Default
	boolean canRun = false;

	@NonNull
	@Default
	String rolloutDirName = CommandlineParams.DEFAULT_RolloutDirectory;

	/**
	 * If specified, the tools shall load the {@link #dbConnectionSettings} from this file.
	 */
	@Default
	String dataBaseSettingsFile = null;

	/**
	 * If specified, the tool shall ignore all files and use these settings.
	 */
	@Default
	DBConnectionSettings dbConnectionSettings = null;

	@Default
	String scriptFileName = null;

	@Default
	IScriptsApplierListener scriptsApplierListener = NullScriptsApplierListener.instance;

	@Default
	boolean justMarkScriptAsExecuted = false;

	/**
	 * By default we will check the versions.
	 */
	@Default
	boolean checkVersions = true;

	/**
	 * By default we will store our won version in the DB after a successful update.
	 */
	@Default
	boolean storeVersion = true;

	/**
	 * If the DB version is already ahead of our local rollout package usually means that something is wrong, so by default the rollout shall fail.
	 */
	@Default
	boolean failIfRolloutIsGreaterThanDB = true;

	@Default
	String templateDBName = null;

	@Default
	String newDBName = null;

	@Default
	@NonNull
	ImmutableSet<IFileRef> additionalSqlDirs = ImmutableSet.of();
}
