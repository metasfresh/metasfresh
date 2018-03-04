package de.metas.ui.web.window.model.sql;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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

/** Various instructions to SQL code generators */
@EqualsAndHashCode
@ToString
public class SqlOptions
{
	/** advice the SQL code generators to use table alias (e.g. "master") instead of fully qualified table name */
	public static final SqlOptions usingTableAlias(@NonNull final String sqlTableAlias)
	{
		if (USE_TABLE_ALIAS_MASTER.tableAlias.equals(sqlTableAlias))
		{
			return USE_TABLE_ALIAS_MASTER;
		}

		return builder()
				.useTableAlias(true)
				.tableAlias(sqlTableAlias)
				.build();
	}

	/** advice the SQL code generators to use fully qualified table name instead of table alias */
	public static final SqlOptions usingTableName(final String tableName)
	{
		return SqlOptions.builder()
				.useTableAlias(false)
				.tableName(tableName)
				.build();
	}

	private static final SqlOptions USE_TABLE_ALIAS_MASTER = SqlOptions.builder().useTableAlias(true).tableAlias("master").build();

	private final boolean useTableAlias;
	private final String tableAlias;
	private final String tableName;

	@Builder
	private SqlOptions(
			final boolean useTableAlias,
			final String tableAlias,
			final String tableName)
	{
		this.useTableAlias = useTableAlias;
		if (useTableAlias)
		{
			Check.assumeNotEmpty(tableAlias, "tableAlias is not empty");
			this.tableAlias = tableAlias;
			this.tableName = null;
		}
		else
		{
			Check.assumeNotEmpty(tableName, "tableName is not empty");
			this.tableAlias = null;
			this.tableName = tableName;
		}
	}

	public boolean isUseTableAlias()
	{
		return useTableAlias;
	}

	public String getTableAlias()
	{
		if (!useTableAlias)
		{
			throw new AdempiereException("tableAlias is not available for " + this);
		}
		return tableAlias;
	}

	public String getTableNameOrAlias()
	{
		return useTableAlias ? tableAlias : tableName;
	}
}
