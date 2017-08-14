package de.metas.migration.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.zafarkhaja.semver.Version;

import lombok.Builder;
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
 * Compares the DB's version from {@code AD_Systemm.DBVersion} with the rollout package's version from {@code build-info.properties}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
public class VersionChecker
{
	private static final transient Logger logger = LoggerFactory.getLogger(VersionChecker.class);

	final boolean failIfRolloutIsGreaterThanDB;

	@NonNull
	private final String dbVersionStr;

	@NonNull
	private final String rolloutVersionStr;

	/**
	 *
	 * @return {@code true} if there is <b>nothing</b> to do.
	 */
	public boolean dbNeedsMigration()
	{
		final Version dbVersion = Version.valueOf(dbVersionStr);

		logger.info("AD_System.DBVersion is {}", dbVersion);

		final Version rolloutVersion = Version.valueOf(rolloutVersionStr);
		logger.info("Our own version is {}", rolloutVersion);

		final int comp = dbVersion.compareTo(rolloutVersion);
		if (comp == 0)
		{
			logger.info("AD_System.DBVersion is equal to our version. Nothing to do.");
			return false;
		}
		else if (comp < 0)
		{
			// dbVersion is lower than rolloutVersion
			// => we need to do the migration to "elevate" it
			logger.info("The DB version is lower than our own version. Going to migrate");
			return true;
		}

		// dbVersion higher....uh-ooh
		final String msg = "The code has version " + rolloutVersionStr + " but the DB already has version " + dbVersionStr;

		if (!failIfRolloutIsGreaterThanDB)
		{
			// let's ignore the problem
			logger.info(msg + ". *Not* throwning exception because of the +" + CommandlineParams.OPTION_DoNotFailIfRolloutIsGreaterThanDB + " parameter; but not going to attempt migration either.");
			return false;
		}

		throw new InconsistentVersionsException(msg);
	}

	public static final class InconsistentVersionsException extends RuntimeException
	{
		private static final long serialVersionUID = -5089487300354591676L;

		private InconsistentVersionsException(final String msg)
		{
			super(msg);
		}
	}
}
