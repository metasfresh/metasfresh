package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;

import com.google.common.annotations.VisibleForTesting;
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
import de.metas.ui.web.window.model.sql.SqlOptions;
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
		/**
		 * Creates a new instance with the given {@code huIds} as {@link #getInitialHUIds()}.
		 * 
		 * @param huIds may be empty, but not null. Empty means that <b>no</b> HU will be matched.
		 * @return
		 */
		public static HUIdsFilterData ofHUIds(@NonNull final Collection<Integer> huIds)
		{
			final IHUQueryBuilder initialFilter = null;
			return new HUIdsFilterData(huIds, initialFilter);
		}

		public static HUIdsFilterData ofHUQuery(@NonNull final IHUQueryBuilder initialHUQuery)
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

		/**
		 * Important: {@code null} means "no restriction" (i.e. we can select allHUs) whereas empty means that no HU matches the filter.
		 */
		private final ImmutableSet<Integer> initialHUIds;

		private final IHUQueryBuilder initialHUQuery;

		/**
		 * Empty list means "no restriction".
		 */
		private final Set<Integer> mustHUIds;
		private final Set<Integer> shallNotHUIds;

		private HUIdsFilterData(
				@Nullable final Collection<Integer> initialHUIds,
				@Nullable final IHUQueryBuilder initialHUQuery)
		{
			this.initialHUIds = initialHUIds == null ? null : ImmutableSet.copyOf(initialHUIds);
			this.initialHUQuery = initialHUQuery != null ? initialHUQuery.copy() : null;
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

	/**
	 * 
	 * @param huIds huIds may be empty, but not null. Empty means that <b>no</b> HU will be matched.
	 * 
	 * @return
	 */
	public static final DocumentFilter createFilter(@NonNull final Collection<Integer> huIds)
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

	public static final class HUIdsSqlDocumentFilterConverter implements SqlDocumentFilterConverter
	{
		@VisibleForTesting
		static final String SQL_TRUE = "1=1";

		private HUIdsSqlDocumentFilterConverter()
		{
		}

		@Override
		public String getSql(
				@NonNull final SqlParamsCollector sqlParamsOut, 
				@NonNull final DocumentFilter filter, 
				final SqlOptions sqlOpts_NOTUSED)
		{
			final HUIdsFilterData huIdsFilter = extractFilterData(filter);
			final ImmutableList<Integer> onlyHUIds;

			final boolean mustHuIdsSpecified = huIdsFilter.getMustHUIds() != null && !huIdsFilter.getMustHUIds().isEmpty();
			final boolean initialHuIdsSpecified = huIdsFilter.getInitialHUIds() != null;

			if (initialHuIdsSpecified && mustHuIdsSpecified)
			{
				onlyHUIds = ImmutableList.copyOf(Iterables.concat(huIdsFilter.getInitialHUIds(), huIdsFilter.getMustHUIds()));
			}
			else if (initialHuIdsSpecified)
			{
				onlyHUIds = ImmutableList.copyOf(huIdsFilter.getInitialHUIds()); // huIdsFilter.getMustHUIds() == null
			}
			else if (mustHuIdsSpecified)
			{
				onlyHUIds = ImmutableList.copyOf(huIdsFilter.getMustHUIds());
			}
			else
			{
				onlyHUIds = null;
			}

			if (onlyHUIds == null && !huIdsFilter.hasInitialHUQuery())
			{
				return SQL_TRUE; // no restrictions were specified; pass through
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
			if (onlyHUIds != null)
			{
				huQuery.addOnlyHUIds(onlyHUIds);
			}

			// Exclude HUs
			huQuery.addHUIdsToExclude(huIdsFilter.getShallNotHUIds());

			final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(huQuery.createQueryFilter());
			final String sql = sqlQueryFilter.getSql();

			sqlParamsOut.collectAll(sqlQueryFilter);
			return sql;
		}

	}
}
