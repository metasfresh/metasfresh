/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.ad.dao.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Value
public class DisplayNameQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	@NonNull
	POInfo poInfo;
	@NonNull
	String searchTerm;

	@NonNull
	public static <T> DisplayNameQueryFilter<T> of(@NonNull final Class<T> modelClass, @NonNull final String searchTerm)
	{
		return new DisplayNameQueryFilter<>(modelClass, searchTerm);
	}

	private DisplayNameQueryFilter(@NonNull final Class<T> modelClass, @NonNull final String searchTerm)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);

		this.poInfo = Optional.ofNullable(POInfo.getPOInfo(tableName))
				.orElseThrow(() -> new AdempiereException("Missing POInfo for tableName = " + tableName));
		this.searchTerm = searchTerm;
	}

	@Override
	public boolean accept(final T model)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSql()
	{
		return buildDisplayNameQuerySql();
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return ImmutableList.of();
	}

	@NonNull
	private String buildDisplayNameQuerySql()
	{
		final String displayNameArgument = poInfo.streamColumns(POInfoColumn::isIdentifier)
				.map(column -> column.isVirtualColumn() ? column.getColumnSQL() : column.getColumnName())
				.collect(Collectors.joining(",", "CONCAT(", ")"));

		return displayNameArgument + " ILIKE ('%" + searchTerm + "%')";
	}
}
