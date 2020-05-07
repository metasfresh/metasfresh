package de.metas.ui.web.view.descriptor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
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

@Builder
@ToString
public final class SqlViewGroupingBinding
{
	@Singular("groupBy")
	private final ImmutableSet<String> groupByFieldNames;
	@Singular("columnSql")
	private final ImmutableMap<String, String> columnSqlByFieldName;

	@NonNull
	@Default
	@Getter
	private final SqlViewRowIdsConverter rowIdsConverter = SqlViewRowIdsConverters.TO_INT_STRICT;

	public ImmutableSet<String> getGroupByFieldNames()
	{
		return groupByFieldNames;
	}

	public boolean isGroupBy(final String fieldName)
	{
		return groupByFieldNames.contains(fieldName);
	}

	public String getColumnSqlByFieldName(final String fieldName)
	{
		return columnSqlByFieldName.get(fieldName);
	}

	public boolean isAggregated(final String fieldName)
	{
		return columnSqlByFieldName.containsKey(fieldName);
	}

}
