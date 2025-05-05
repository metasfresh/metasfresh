package de.metas.ui.web.window.model.lookup;

import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ReferenceId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

public class LabelsLookup implements LookupDescriptor, LookupDataSourceFetcher
{
	public static LabelsLookup cast(final LookupDescriptor lookupDescriptor)
	{
		return (LabelsLookup)lookupDescriptor;
	}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Getter
	@NonNull
	private final String fieldName;

	/**
	 * Labels table name (e.g. C_BPartner_Attribute)
	 */
	@Getter
	@NonNull
	private final String labelsTableName;

	/**
	 * Labels reference List column name (e.g. C_BPartner_Attribute's Attribute)
	 */
	@Getter
	@NonNull
	private final String labelsValueColumnName;
	@NonNull
	private final LookupDataSource labelsValuesLookupDataSource;
	@Getter
	private final boolean labelsValuesUseNumericKey;

	/**
	 * Labels tableName's link column name (e.g. C_BPartner_Attribute's C_BPartner_ID)
	 */
	@Getter
	@NonNull
	private final String labelsLinkColumnName;

	@Getter
	@Nullable
	private final ReferenceId labelsValueReferenceId;

	/**
	 * Table name (e.g. C_BPartner)
	 */
	@NonNull
	private final String tableName;

	/**
	 * Table's link column name (e.g. C_BPartner's C_BPartner_ID)
	 */
	@Getter
	@NonNull
	private final String linkColumnName;

	@NonNull
	private final ImmutableSet<CtxName> parameters;

	@Builder
	private LabelsLookup(
			@NonNull final String fieldName,
			@NonNull final String tableName,
			@NonNull final String linkColumnName,
			@NonNull final String labelsTableName,
			@NonNull final String labelsValueColumnName,
			@NonNull final String labelsLinkColumnName,
			boolean labelsValuesUseNumericKey,
			@NonNull final LookupDataSource labelsValuesLookupDataSource,
			@Nullable final ReferenceId labelsValueReferenceId)
	{
		this.fieldName = fieldName;
		this.labelsTableName = labelsTableName;
		this.labelsValueColumnName = labelsValueColumnName;
		this.labelsValueReferenceId = labelsValueReferenceId;
		this.labelsValuesLookupDataSource = labelsValuesLookupDataSource;
		this.labelsValuesUseNumericKey = labelsValuesUseNumericKey;
		this.labelsLinkColumnName = labelsLinkColumnName;
		this.tableName = tableName;
		this.linkColumnName = linkColumnName;

		parameters = ImmutableSet.of(CtxNames.parse(linkColumnName+"/-1"));
	}

	@Override
	public Class<?> getValueClass()
	{
		return DocumentFieldWidgetType.Labels.getValueClass();
	}

	public LookupValuesList retrieveExistingValuesByLinkId(final int linkId)
	{
		if (linkId <= 0)
		{
			return LookupValuesList.EMPTY;
		}

		final List<String> existingItems = queryValueRecordsByLinkId(linkId)
				.create()
				.listDistinct(labelsValueColumnName, String.class);
		if (existingItems.isEmpty())
		{
			return LookupValuesList.EMPTY;
		}

		return retrieveExistingValuesByIds(existingItems);
	}

	public LookupValuesList retrieveExistingValuesByIds(@NonNull final Collection<?> ids)
	{
		return !ids.isEmpty() ? labelsValuesLookupDataSource.findByIdsOrdered(ids) : LookupValuesList.EMPTY;
	}

	public IQueryBuilder<Object> queryValueRecordsByLinkId(final int linkId)
	{
		return queryBL
				.createQueryBuilder(labelsTableName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(getLabelsLinkColumnName(), linkId); // parent link
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return Optional.of(labelsTableName);
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	@Nullable
	public String getCachePrefix()
	{
		return null; // not important because isCached() returns false
	}

	@Override
	public void cacheInvalidate()
	{
		labelsValuesLookupDataSource.cacheInvalidate();
	}

	@Override
	public boolean isHighVolume()
	{
		return false;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.lookup;
	}

	@Override
	public boolean hasParameters()
	{
		return true;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return CtxNames.toNames(parameters);
	}

	@Override
	public boolean isNumericKey()
	{
		return false;
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(tableName)
				.putFilterById(IdsToFilter.ofSingleValue(id));
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		final Object id = evalCtx.getSingleIdToFilterAsObject();
		if (id == null)
		{
			throw new IllegalStateException("No ID provided in " + evalCtx);
		}

		return labelsValuesLookupDataSource.findById(id);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(tableName)
				.setRequiredParameters(parameters)
				.requiresAD_Language()
				.requiresUserRolePermissionsKey();
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final String filter = evalCtx.getFilter();
		return labelsValuesLookupDataSource.findEntities(evalCtx, filter);
	}

	public Set<Object> normalizeStringIds(final Set<String> stringIds)
	{
		if (stringIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		if (isLabelsValuesUseNumericKey())
		{
			return stringIds.stream()
					.map(LabelsLookup::convertToInt)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			return ImmutableSet.copyOf(stringIds);
		}
	}

	private static int convertToInt(final String stringId)
	{
		try
		{
			return Integer.parseInt(stringId);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting `" + stringId + "` to int.", ex);
		}
	}

	public ColumnSql getSqlForFetchingValueIdsByLinkId(@NonNull final String tableNameOrAlias)
	{
		final String sql = "SELECT array_agg(" + labelsValueColumnName + ")"
				+ " FROM " + labelsTableName
				+ " WHERE " + labelsLinkColumnName + "=" + tableNameOrAlias + "." + linkColumnName
				+ " AND IsActive='Y'";

		return ColumnSql.ofSql(sql, tableNameOrAlias);
	}
}
