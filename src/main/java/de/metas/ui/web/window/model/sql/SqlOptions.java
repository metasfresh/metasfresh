package de.metas.ui.web.window.model.sql;

import lombok.Builder;
import lombok.Value;

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
@Value
@Builder
public class SqlOptions
{
	public static final SqlOptions defaults()
	{
		// NOTE: by default we use table alias because most of the previous code is assuming that
		return USE_TABLE_ALIAS;
	}

	/** advice the SQL code generators to use table alias (e.g. "master") instead of fully qualified table name */
	public static final SqlOptions usingTableAlias()
	{
		return USE_TABLE_ALIAS;
	}

	/** advice the SQL code generators to use fully qualified table name instead of table alias (e.g. "master") */
	public static final SqlOptions usingTableName()
	{
		return USE_TABLE_NAME;
	}

	private static final SqlOptions USE_TABLE_ALIAS = SqlOptions.builder().useTableAlias(true).build();
	private static final SqlOptions USE_TABLE_NAME = SqlOptions.builder().useTableAlias(false).build();

	private final boolean useTableAlias;
}
