package de.metas.ui.web.window.model.lookup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

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

	/** Labels table name (e.g. C_BPartner_Attribute) */
	@Getter
	private final String labelsTableName;
	/** Labels reference List column name (e.g. C_BPartner_Attribute's Attribute) */
	@Getter
	private final String labelsValueColumnName;
	private final LookupDataSource labelsValuesLookupDataSource;
	@Getter
	private final boolean labelsValuesUseNumericKey;
	/** Labels tableName's link column name (e.g. C_BPartner_Attribute's C_BPartner_ID) */
	@Getter
	private final String labelsLinkColumnName;
	/** Table name (e.g. C_BPartner) */
	private final String tableName;
	/** Table's link column name (e.g. C_BPartner's C_BPartner_ID) */
	@Getter
	private final String linkColumnName;

	private final Set<CtxName> parameters;

	@Builder
	private LabelsLookup(
			@NonNull final String tableName,
			@NonNull final String linkColumnName,
			@NonNull final String labelsTableName,
			@NonNull final String labelsValueColumnName,
			@NonNull final String labelsLinkColumnName,
			@NonNull final LookupDescriptor labelsValuesLookupDescriptor)
	{
		this.labelsTableName = labelsTableName;
		this.labelsValueColumnName = labelsValueColumnName;
		this.labelsValuesLookupDataSource = LookupDataSourceFactory.instance.getLookupDataSource(labelsValuesLookupDescriptor);
		this.labelsValuesUseNumericKey = labelsValuesLookupDescriptor.isNumericKey();
		this.labelsLinkColumnName = labelsLinkColumnName;
		this.tableName = tableName;
		this.linkColumnName = linkColumnName;

		parameters = ImmutableSet.of(CtxNames.parse(linkColumnName));
	}

	@Override
	public Class<?> getValueClass()
	{
		return DocumentFieldWidgetType.Labels.getValueClass();
	}

	public LookupValuesList retrieveExistingValues(final int linkId)
	{
		if (linkId <= 0)
		{
			return LookupValuesList.EMPTY;
		}

		final List<String> existingItems = retrieveExistingValuesRecordQuery(linkId)
				.create()
				.listDistinct(labelsValueColumnName, String.class);
		if (existingItems.isEmpty())
		{
			return LookupValuesList.EMPTY;
		}

		return labelsValuesLookupDataSource.findByIds(existingItems);
	}

	public IQueryBuilder<Object> retrieveExistingValuesRecordQuery(final int linkId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(labelsTableName, PlainContextAware.newWithThreadInheritedTrx())
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
		return LookupDataSourceContext.builder(tableName).putFilterById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final String id = evalCtx.getIdToFilterAsString();
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
				.requiresAD_Language();
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final String filter = evalCtx.getFilter();
		return labelsValuesLookupDataSource.findEntities(evalCtx, filter);
	}
}
