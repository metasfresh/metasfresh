package org.compiere.db;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;

import javax.annotation.Nullable;

/**
 * {@link CConnection}'s attributes.
 *
 * @author tsa
 *
 */
@Builder
@Data
public final class CConnectionAttributes
{
	public static CConnectionAttributes empty()
	{
		return CConnectionAttributes.builder().build();
	}

	/**
	 * Creates connection attributes object based on given attributes string.
	 *
	 * NOTE: it is assumed that the connection attributes string was produced by {@link #toString()} method.
	 */
	public static CConnectionAttributes of(@NonNull final String attributesStr)
	{
		// NOTE: keep in sync with toString()
		final String dbPortString = attributesStr.substring(attributesStr.indexOf("DBport=") + 7, attributesStr.indexOf(",DBname="));

		return CConnectionAttributes.builder()
				.dbHost(attributesStr.substring(attributesStr.indexOf("DBhost=") + 7, attributesStr.indexOf(",DBport=")))
				.dbPort(parseDbPort(dbPortString))
				.dbName(getSubString(attributesStr, "DBname=", ","))
				.dbUid(attributesStr.substring(attributesStr.indexOf("UID=") + 4, attributesStr.indexOf(",PWD=")))
				.dbPwd(attributesStr.substring(attributesStr.indexOf("PWD=") + 4, attributesStr.indexOf("]")))
				.build();
	}

	@Nullable
	private static String getSubString(@NonNull final String attributesStr, @NonNull final String before, @NonNull final String after)
	{
		final int indexOfAppsPasswordStart = attributesStr.indexOf(before);
		final int indexOfAppsPasswordEnd = attributesStr.indexOf(after, indexOfAppsPasswordStart);
		if (indexOfAppsPasswordStart >= 0 && indexOfAppsPasswordEnd >= 0)
		{
			return attributesStr.substring(indexOfAppsPasswordStart + before.length(), indexOfAppsPasswordEnd);
		}
		return null;
	}

	private static final transient Logger logger = LogManager.getLogger(CConnectionAttributes.class);

	@Builder.Default
	private String dbHost = "localhost";

	@Builder.Default
	private int dbPort = 5432;

	@Builder.Default
	private String dbName = "metasfresh";
	private String dbUid;
	private String dbPwd;

	/**
	 * Builds connection attributes string representation.
	 *
	 * This string can be parsed back by using {@link #of(String)}.
	 *
	 * @return connection attributes string representation
	 */
	@Override
	public String toString()
	{
		// NOTE: keep in sync with the parser!!!

		final StringBuilder sb = new StringBuilder("CConnection[");
		sb
				.append("DBhost=").append(dbHost)
				.append(",DBport=").append(dbPort)
				.append(",DBname=").append(dbName)
				.append(",UID=").append(dbUid)
				.append(",PWD=").append(dbPwd);
		sb.append("]");
		return sb.toString();
	}	// toStringLong


	private static int parseDbPort(final String dbPortString)
	{
		try
		{
			if (Check.isBlank(dbPortString))
			{
				return -1;
			}
			else
			{
				return Integer.parseInt(dbPortString);
			}
		}
		catch (final Exception e)
		{
			logger.error("Error parsing db port: " + dbPortString, e);
			return -1;
		}
	} 	// setDbPort
}
