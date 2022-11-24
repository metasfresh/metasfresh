/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.migration.logger;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@EqualsAndHashCode
final class Sql
{
	private final String sql;

	public static Sql ofSql(@NonNull final String sql)
	{
		return ofSql(sql, null);
	}

	public static Sql ofSql(
			@NonNull final String sql,
			@Nullable final String comments)
	{
		final StringBuilder finalSql = new StringBuilder();

		// Add provided comments
		final String sqlComment = toSqlComment(comments);
		if (sqlComment != null)
		{
			finalSql.append("\n").append(sqlComment);
		}

		finalSql.append("-- ").append(Instant.now()).append("\n");
		finalSql.append(sql).append("\n;\n\n");

		return new Sql(finalSql.toString());
	}

	public static Sql ofComment(@NonNull final String comment)
	{
		final String sqlComment = toSqlComment(comment);
		return sqlComment != null ? new Sql(sqlComment) : new Sql("-- ");
	}

	@Nullable
	private static String toSqlComment(@Nullable final String comment)
	{
		final String commentNorm = StringUtils.trimBlankToNull(comment);
		if (commentNorm == null)
		{
			return null;
		}
		return "-- "
				+ commentNorm
				.replace("\r\n", "\n")
				.replace("\n", "\n-- ")
				+ "\n";
	}

	Sql(final String sql)
	{
		this.sql = sql;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return toSql();
	}

	public String toSql()
	{
		return sql;
	}
}
