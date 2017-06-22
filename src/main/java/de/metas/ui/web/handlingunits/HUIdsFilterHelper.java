package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;

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

/**
 * Helper class to handle the HUIds document filter.
 */
@UtilityClass
public final class HUIdsFilterHelper
{
	@ToString
	public static final class HUIdsFilterData
	{
		public static HUIdsFilterData ofHUIds(final Collection<Integer> huIds)
		{
			final IHUQueryBuilder initialFilter = null;
			return new HUIdsFilterData(huIds, initialFilter);
		}

		public static HUIdsFilterData ofHUQuery(final IHUQueryBuilder initialHUQuery)
		{
			final Collection<Integer> huIds = null;
			return new HUIdsFilterData(huIds, initialHUQuery);
		}

		public static HUIdsFilterData newEmpty()
		{
			final Collection<Integer> huIds = null;
			final IHUQueryBuilder initialHUQuery = null;
			return new HUIdsFilterData(huIds, initialHUQuery);
		}

		private final ImmutableSet<Integer> initialHUIds;
		private final IHUQueryBuilder initialHUQuery;

		private final Set<Integer> mustHUIds;
		private final Set<Integer> shallNotHUIds;

		private HUIdsFilterData(final Collection<Integer> initialHUIds, final IHUQueryBuilder initialHUQuery)
		{
			this.initialHUIds = initialHUIds != null ? ImmutableSet.copyOf(initialHUIds) : ImmutableSet.of();
			this.initialHUQuery = initialHUQuery;
			mustHUIds = new HashSet<>();
			shallNotHUIds = new HashSet<>();
		}

		private HUIdsFilterData(final HUIdsFilterData from)
		{
			initialHUIds = from.initialHUIds;
			initialHUQuery = from.initialHUQuery != null ? from.initialHUQuery.copy() : null;

			mustHUIds = new HashSet<>(from.mustHUIds);
			shallNotHUIds = new HashSet<>(from.shallNotHUIds);
		}

		public HUIdsFilterData copy()
		{
			return new HUIdsFilterData(this);
		}

		public ImmutableSet<Integer> getInitialHUIds()
		{
			return initialHUIds;
		}

		public IHUQueryBuilder getInitialHUQueryOrNull()
		{
			return initialHUQuery != null ? initialHUQuery.copy() : null;
		}

		public Set<Integer> getMustHUIds()
		{
			return ImmutableSet.copyOf(mustHUIds);
		}

		public boolean mustHUIds(final Collection<Integer> mustHUIdsToAdd)
		{
			if (mustHUIdsToAdd.isEmpty())
			{
				return false;
			}

			final boolean added = mustHUIds.addAll(mustHUIdsToAdd);
			shallNotHUIds.removeAll(mustHUIdsToAdd);

			return added;
		}

		public Set<Integer> getShallNotHUIds()
		{
			return ImmutableSet.copyOf(shallNotHUIds);
		}

		public boolean shallNotHUIds(final Collection<Integer> shallNotHUIdsToAdd)
		{
			if (shallNotHUIdsToAdd.isEmpty())
			{
				return false;
			}

			final boolean added = shallNotHUIds.addAll(shallNotHUIdsToAdd);
			mustHUIds.removeAll(shallNotHUIdsToAdd);

			return added;
		}

		public boolean hasInitialHUQuery()
		{
			return initialHUQuery != null;
		}
	}

	public static final class MutableHUIdsList
	{
		// public static final MutableHUIdsList of(HUIdsFilterData filter, )
	}

	public static final String FILTER_ID = "huIds";
	private static final String FILTER_PARAM_Data = "$data";
	
	public static final transient HUIdsSqlDocumentFilterConverter SQL_DOCUMENT_FILTER_CONVERTER = new HUIdsSqlDocumentFilterConverter();


	public static final DocumentFilter findExistingOrNull(final Collection<DocumentFilter> filters)
	{
		if (filters == null || filters.isEmpty())
		{
			return null;
		}

		return filters.stream()
				.filter(filter -> FILTER_ID.equals(filter.getFilterId()))
				.findFirst().orElse(null);
	}

	public static final DocumentFilter createFilter(final Collection<Integer> huIds)
	{
		final HUIdsFilterData filterData = HUIdsFilterData.ofHUIds(huIds);
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	public static final DocumentFilter createFilter(final IHUQueryBuilder huQuery)
	{
		final HUIdsFilterData filterData = HUIdsFilterData.ofHUQuery(huQuery);
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	public static final DocumentFilter createFilter(@NonNull final HUIdsFilterData filterData)
	{
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	private static final HUIdsFilterData extractFilterData(@NonNull final DocumentFilter huIdsFilter)
	{
		Preconditions.checkArgument(!isNotHUIdsFilter(huIdsFilter), "Not a HUIds filter: %s", huIdsFilter);
		return (HUIdsFilterData)huIdsFilter.getParameter(FILTER_PARAM_Data).getValue();
	}

	public static final HUIdsFilterData extractFilterDataOrNull(final Collection<DocumentFilter> filters)
	{
		final DocumentFilter huIdsFilter = findExistingOrNull(filters);
		if (huIdsFilter == null)
		{
			return null;
		}
		return HUIdsFilterHelper.extractFilterData(huIdsFilter);
	}

	public static final boolean isNotHUIdsFilter(final DocumentFilter filter)
	{
		return !FILTER_ID.equals(filter.getFilterId());
	}

	public static boolean isHighVolume(final List<DocumentFilter> stickyFilters)
	{
		final HUIdsFilterData huIdsFilterData = extractFilterDataOrNull(stickyFilters);
		if (huIdsFilterData == null)
		{
			return true;
		}

		final Set<Integer> huIds = huIdsFilterData.getInitialHUIds();
		return huIds.isEmpty() || huIds.size() >= HUEditorViewBuffer_HighVolume.HIGHVOLUME_THRESHOLD;
	}

	public static final class HUIdsSqlDocumentFilterConverter implements SqlDocumentFilterConverter
	{
		private HUIdsSqlDocumentFilterConverter()
		{
		}

		@Override
		public String getSql(final SqlParamsCollector sqlParamsOut, final DocumentFilter filter)
		{
			final HUIdsFilterData huIdsFilter = extractFilterData(filter);
			final ImmutableList<Integer> onlyHUIds = ImmutableList.copyOf(Iterables.concat(huIdsFilter.getInitialHUIds(), huIdsFilter.getMustHUIds()));

			if (onlyHUIds.isEmpty() && !huIdsFilter.hasInitialHUQuery())
			{
				return "1=1"; // pass through
			}

			//
			// Create HU query
			IHUQueryBuilder huQuery = huIdsFilter.getInitialHUQueryOrNull();
			if (huQuery == null)
			{
				huQuery = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder();
			}
			huQuery.setContext(PlainContextAware.newOutOfTrx());

			// Only HUs
			if (!onlyHUIds.isEmpty())
			{
				huQuery.addOnlyHUIds(onlyHUIds);
			}

			// Exclude HUs
			huQuery.addHUIdsToExclude(huIdsFilter.getShallNotHUIds());

			final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(huQuery.createQueryFilter());

			final String sql = sqlQueryFilter.getSql();
			final List<Object> sqlParams = sqlQueryFilter.getSqlParams(Env.getCtx());
			sqlParamsOut.collectAll(sqlParams);
			return sql;
		}

	}
}
