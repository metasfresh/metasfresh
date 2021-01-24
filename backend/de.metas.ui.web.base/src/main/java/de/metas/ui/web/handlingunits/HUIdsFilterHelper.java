package de.metas.ui.web.handlingunits;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.PlainContextAware;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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
		public static HUIdsFilterData ofHUIds(@NonNull final Collection<HuId> huIds)
		{
			final IHUQueryBuilder initialFilter = null;
			return new HUIdsFilterData(huIds, initialFilter);
		}

		public static HUIdsFilterData ofHUQuery(@NonNull final IHUQueryBuilder initialHUQuery)
		{
			final Collection<HuId> huIds = null;
			return new HUIdsFilterData(huIds, initialHUQuery);
		}

		public static HUIdsFilterData newEmpty()
		{
			final Collection<HuId> huIds = null;
			final IHUQueryBuilder initialHUQuery = null;
			return new HUIdsFilterData(huIds, initialHUQuery);
		}

		/**
		 * Important: {@code null} means "no restriction" (i.e. we can select allHUs) whereas empty means that no HU matches the filter.
		 */
		@Nullable
		private final ImmutableSet<HuId> initialHUIds;

		private final IHUQueryBuilder initialHUQuery;

		/**
		 * Empty list means "no restriction".
		 */
		private final HashSet<HuId> mustHUIds;
		private final HashSet<HuId> shallNotHUIds;

		private HUIdsFilterData(
				@Nullable final Collection<HuId> initialHUIds,
				@Nullable final IHUQueryBuilder initialHUQuery)
		{
			this.initialHUIds = initialHUIds == null ? null : ImmutableSet.copyOf(initialHUIds);
			this.initialHUQuery = initialHUQuery != null ? initialHUQuery.copy() : null;
			mustHUIds = new HashSet<>();
			shallNotHUIds = new HashSet<>();
		}

		private HUIdsFilterData(@NonNull final HUIdsFilterData from)
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

		@Nullable
		public ImmutableSet<HuId> getInitialHUIds()
		{
			return initialHUIds;
		}

		public IHUQueryBuilder getInitialHUQueryOrNull()
		{
			return initialHUQuery != null ? initialHUQuery.copy() : null;
		}

		public ImmutableSet<HuId> getMustHUIds()
		{
			return ImmutableSet.copyOf(mustHUIds);
		}

		public boolean mustHUIds(final Collection<HuId> mustHUIdsToAdd)
		{
			if (mustHUIdsToAdd.isEmpty())
			{
				return false;
			}

			final boolean added = mustHUIds.addAll(mustHUIdsToAdd);
			shallNotHUIds.removeAll(mustHUIdsToAdd);

			return added;
		}

		public ImmutableSet<HuId> getShallNotHUIds()
		{
			return ImmutableSet.copyOf(shallNotHUIds);
		}

		public boolean shallNotHUIds(final Collection<HuId> shallNotHUIdsToAdd)
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

	private static final String FILTER_ID = "huIds";
	private static final String FILTER_PARAM_Data = "$data";

	public static final transient HUIdsSqlDocumentFilterConverter SQL_DOCUMENT_FILTER_CONVERTER = new HUIdsSqlDocumentFilterConverter();

	public static DocumentFilter findExistingOrNull(final DocumentFilterList filters)
	{
		if (filters == null || filters.isEmpty())
		{
			return null;
		}

		return filters.getFilterById(FILTER_ID).orElse(null);
	}

	/**
	 * @param huIds huIds may be empty, but not null. Empty means that <b>no</b> HU will be matched.
	 * @return
	 */
	public static DocumentFilter createFilter(@NonNull final Collection<HuId> huIds)
	{
		final HUIdsFilterData filterData = HUIdsFilterData.ofHUIds(huIds);
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	public static DocumentFilter createFilter(final IHUQueryBuilder huQuery)
	{
		final HUIdsFilterData filterData = HUIdsFilterData.ofHUQuery(huQuery);
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	public static DocumentFilter createFilter(@NonNull final HUIdsFilterData filterData)
	{
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	private static HUIdsFilterData extractFilterData(@NonNull final DocumentFilter huIdsFilter)
	{
		Preconditions.checkArgument(!isNotHUIdsFilter(huIdsFilter), "Not a HUIds filter: %s", huIdsFilter);
		return (HUIdsFilterData)huIdsFilter.getParameter(FILTER_PARAM_Data).getValue();
	}

	public static HUIdsFilterData extractFilterDataOrNull(final DocumentFilterList filters)
	{
		final DocumentFilter huIdsFilter = findExistingOrNull(filters);
		if (huIdsFilter == null)
		{
			return null;
		}
		return HUIdsFilterHelper.extractFilterData(huIdsFilter);
	}

	public static boolean isNotHUIdsFilter(final DocumentFilter filter)
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
		public boolean canConvert(final String filterId)
		{
			return Objects.equals(filterId, FILTER_ID);
		}

		@Override
		public String getSql(
				@NonNull final SqlParamsCollector sqlParamsOut,
				@NonNull final DocumentFilter filter,
				final SqlOptions sqlOpts_NOTUSED,
				@NonNull final SqlDocumentFilterConverterContext context)
		{
			final HUIdsFilterData huIdsFilter = extractFilterData(filter);
			final ImmutableList<HuId> onlyHUIds;

			final ImmutableSet<HuId> mustHUIds = huIdsFilter.getMustHUIds();
			final ImmutableSet<HuId> shallNotHUIds = huIdsFilter.getShallNotHUIds();
			final boolean mustHuIdsSpecified = !mustHUIds.isEmpty();
			final boolean initialHuIdsSpecified = huIdsFilter.getInitialHUIds() != null;

			if (initialHuIdsSpecified && mustHuIdsSpecified)
			{
				onlyHUIds = ImmutableList.copyOf(Iterables.concat(huIdsFilter.getInitialHUIds(), mustHUIds));
			}
			else if (initialHuIdsSpecified)
			{
				onlyHUIds = ImmutableList.copyOf(huIdsFilter.getInitialHUIds()); // huIdsFilter.getMustHUIds() == null
			}
			else if (mustHuIdsSpecified)
			{
				onlyHUIds = ImmutableList.copyOf(mustHUIds);
			}
			else
			{
				onlyHUIds = null;
			}

			if (onlyHUIds == null
					&& !huIdsFilter.hasInitialHUQuery()
					&& shallNotHUIds.isEmpty())
			{
				return SQL_TRUE; // no restrictions were specified; pass through
			}

			//
			// Create HU query
			IHUQueryBuilder huQuery = huIdsFilter.getInitialHUQueryOrNull();
			if (huQuery == null)
			{
				final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
				huQuery = handlingUnitsDAO.createHUQueryBuilder()
						.setOnlyActiveHUs(false) // let other enforce this rule
						.setOnlyTopLevelHUs(false); // let other enforce this rule
			}
			huQuery.setContext(PlainContextAware.newOutOfTrx());

			// Only HUs
			if (onlyHUIds != null)
			{
				huQuery.addOnlyHUIds(HuId.toRepoIds(onlyHUIds));
			}

			// Exclude HUs
			huQuery.addHUIdsToExclude(HuId.toRepoIds(shallNotHUIds));

			final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(huQuery.createQueryFilter());
			final String sql = sqlQueryFilter.getSql();

			sqlParamsOut.collectAll(sqlQueryFilter);
			return sql;
		}

	}
}
