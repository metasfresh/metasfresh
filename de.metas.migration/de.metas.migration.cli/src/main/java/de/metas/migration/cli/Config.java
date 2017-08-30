package de.metas.migration.cli;

import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.applier.impl.NullScriptsApplierListener;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
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
 * This class holds the config from the command line tool's parameters
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
@Data
public class Config
{
	public static final String DEFAULT_SETTINGS_FILENAME = "local_settings.properties";

	@Default
	private final boolean canRun = false;

	@NonNull
	private final String rolloutDirName;

	@Default
	private final String settingsFileName = null;

	@Default
	private final String scriptFileName = null;

	@Default
	private final IScriptsApplierListener scriptsApplierListener = NullScriptsApplierListener.instance;

	@Default
	private final boolean justMarkScriptAsExecuted = false;

	/**
	 * By default we will check the versions.
	 */
	@Default
	private final boolean checkVersions = true;

	/**
	 * By default we will store our won version in the DB after a successful update.
	 */
	@Default
	private final boolean storeVersion = true;

	/**
	 * If the DB version is already ahead of our local rollout package usually means that something is wrong, so by default the rollout shall fail.
	 */
	@Default
	private final boolean failIfRolloutIsGreaterThanDB = true;

	@Default
	private final String templateDBName = null;

	@Default
	private final String newDBName = null;
}
