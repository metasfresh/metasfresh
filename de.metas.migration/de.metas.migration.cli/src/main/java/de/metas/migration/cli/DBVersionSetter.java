package de.metas.migration.cli;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.zafarkhaja.semver.Version;
import com.google.common.annotations.VisibleForTesting;

import de.metas.migration.IDatabase;
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
 * this class updates the DB's {@code AD_System.DBVersion} column.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
public class DBVersionSetter
{
	private static final transient Logger logger = LoggerFactory.getLogger(DBVersionSetter.class);

	@NonNull
	private final IDatabase db;

	@NonNull
	private final String newVersion;

	@NonNull
	private final String additionalMetaDataSuffix;

	public void setVersion()
	{
		final String versionStr = createVersionWithOptionalSuffix(newVersion, additionalMetaDataSuffix);
		logger.info("Setting AD_System.DBVersion to {}", versionStr);

		final String updateSql = "UPDATE public.AD_System SET DBVersion='" + versionStr + "'";
		try (final Connection connection = db.getConnection();
				final Statement stmt = connection.createStatement())
		{
			stmt.executeUpdate(updateSql);
		}
		catch (final SQLException e)
		{
			throw new RuntimeException("Could not set version; updateSql=" + updateSql, e);
		}
	}

	@VisibleForTesting
	static String createVersionWithOptionalSuffix(
			@NonNull final String versionStr,
			final String suffix)
	{
		final Version version;
		if (suffix == null || "".equals(suffix))
		{
			version = Version.valueOf(versionStr);
		}
		else
		{
			final Version tmp = Version.valueOf(versionStr);
			version = tmp.setBuildMetadata(tmp.getBuildMetadata() + "x" + suffix);
		}

		return version.toString();
	}
}
