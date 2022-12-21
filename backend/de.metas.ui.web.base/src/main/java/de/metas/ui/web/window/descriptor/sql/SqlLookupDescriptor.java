/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.window.descriptor.sql;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.model.lookup.GenericSqlLookupDataSourceFetcher;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.api.TableName;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
public final class SqlLookupDescriptor implements ISqlLookupDescriptor
{
	public static SqlLookupDescriptor cast(final LookupDescriptor descriptor)
	{
		return (SqlLookupDescriptor)descriptor;
	}

	@NonNull private final CompositeSqlLookupFilter filters;
	@Getter private final boolean highVolume;
	@Getter @Nullable private final LookupSource lookupSourceType;
	@Getter @NonNull private final GenericSqlLookupDataSourceFetcher lookupDataSourceFetcher;

	//
	// Required for toBuilder():
	@NonNull final SqlForFetchingLookupById sqlForFetchingLookupByIdExpression;
	@NonNull final SqlForFetchingLookups sqlForFetchingExpression;
	@NonNull final TableName lookupTableName;
	@NonNull final TooltipType tooltipType;
	@Nullable final WindowId zoomIntoWindowId;

	@Nullable final Integer pageLength;

	@Builder(toBuilder = true)
	private SqlLookupDescriptor(
			@NonNull final CompositeSqlLookupFilter filters,
			final boolean highVolume,
			@Nullable final LookupSource lookupSourceType,
			@NonNull final TableName lookupTableName,
			@NonNull final SqlForFetchingLookupById sqlForFetchingLookupByIdExpression,
			@NonNull final SqlForFetchingLookups sqlForFetchingExpression,
			@NonNull final TooltipType tooltipType,
			@Nullable final WindowId zoomIntoWindowId,
			@Nullable final Integer pageLength)
	{
		this.filters = filters;
		this.highVolume = highVolume;
		this.lookupSourceType = lookupSourceType;

		this.sqlForFetchingLookupByIdExpression = sqlForFetchingLookupByIdExpression;
		this.sqlForFetchingExpression = sqlForFetchingExpression;
		this.lookupTableName = lookupTableName;
		this.tooltipType = tooltipType;
		this.zoomIntoWindowId = zoomIntoWindowId;
		this.pageLength = pageLength;

		this.lookupDataSourceFetcher = GenericSqlLookupDataSourceFetcher.builder()
				.lookupTableName(lookupTableName)
				.sqlForFetchingLookups(sqlForFetchingExpression.withSqlWhere_ValidationRules(this.filters.getSqlWhereClause()))
				.sqlForFetchingLookupById(sqlForFetchingLookupByIdExpression)
				.postQueryPredicate(this.filters.getPostQueryPredicate())
				.zoomIntoWindowId(Optional.ofNullable(zoomIntoWindowId))
				.tooltipType(tooltipType)
				.pageLength(pageLength)
				.build();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", getTableName().orElse(null))
				.add("highVolume", highVolume ? true : null)
				.add("sqlForFetching", lookupDataSourceFetcher.getSqlForFetchingLookups().toOneLineString())
				.add("filters", filters)
				.toString();
	}

	@Override
	public Optional<String> getTableName() {return getLookupDataSourceFetcher().getLookupTableName();}

	public boolean isNumericKey() {return getLookupDataSourceFetcher().isNumericKey();}

	@Override
	public boolean hasParameters()
	{
		return !lookupDataSourceFetcher.getSqlForFetchingLookups().getParameters().isEmpty()
				|| (!filters.getDependsOnFieldNames().isEmpty());
	}

	public ImmutableSet<String> getDependsOnFieldNames()
	{
		return filters.getDependsOnFieldNames();
	}

	public ImmutableSet<String> getDependsOnTableNames()
	{
		return filters.getDependsOnTableNames();
	}

	public SqlForFetchingLookupById getSqlForFetchingLookupByIdExpression()
	{
		return lookupDataSourceFetcher.getSqlForFetchingLookupById();
	}

	public SqlLookupDescriptor withScope(@Nullable LookupDescriptorProvider.LookupScope scope)
	{
		return withFilters(this.filters.withOnlyScope(scope));
	}

	public SqlLookupDescriptor withOnlyForAvailableParameterNames(@Nullable Set<String> onlyForAvailableParameterNames)
	{
		return withFilters(this.filters.withOnlyForAvailableParameterNames(onlyForAvailableParameterNames));
	}

	private SqlLookupDescriptor withFilters(@NonNull final CompositeSqlLookupFilter filters)
	{
		return !Objects.equals(this.filters, filters)
				? toBuilder().filters(filters).build()
				: this;
	}

}
