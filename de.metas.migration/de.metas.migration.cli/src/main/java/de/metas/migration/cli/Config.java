package de.metas.migration.cli;

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

	@NonNull
	private final String rolloutDirName;

	@Default
	private final String settingsFileName = DEFAULT_SETTINGS_FILENAME;

	@Default
	private final String scriptFileName = null;

	@Default
	private final boolean justMarkScriptAsExecuted = false;

	@Default
	private final boolean doNotCheckVersions = false;

	@Default
	private final boolean doNotFailIfRolloutIsGreaterThanDB = false;

	@Default
	private final String templateDBName = null;

	@Default
	private final String newDBName = null;
}
