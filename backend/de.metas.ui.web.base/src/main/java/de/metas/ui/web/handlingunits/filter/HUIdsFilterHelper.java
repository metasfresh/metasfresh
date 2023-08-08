/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.handlingunits.filter;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

/**
 * Helper class to handle the HUIds document filter.
 */
@UtilityClass
public final class HUIdsFilterHelper
{
	static final String FILTER_ID = "huIds";
	private static final String FILTER_PARAM_Data = "$data";

	public static final transient HUIdsSqlDocumentFilterConverter SQL_DOCUMENT_FILTER_CONVERTER = new HUIdsSqlDocumentFilterConverter();

	public static Optional<DocumentFilter> findExisting(@Nullable final DocumentFilterList filters)
	{
		return filters != null && !filters.isEmpty() ? filters.getFilterById(FILTER_ID) : Optional.empty();
	}

	/**
	 * @param huIds huIds may be empty, but not null. Empty means that <b>no</b> HU will be matched.
	 */
	public static DocumentFilter createFilter(@NonNull final Collection<HuId> huIds)
	{
		final HUIdsFilterData filterData = HUIdsFilterData.ofHUIds(huIds);
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	public static DocumentFilter createFilter(@NonNull final IHUQueryBuilder huQuery)
	{
		final HUIdsFilterData filterData = HUIdsFilterData.ofHUQuery(huQuery);
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	public static DocumentFilter createFilter(@NonNull final HUIdsFilterData filterData)
	{
		return DocumentFilter.singleParameterFilter(FILTER_ID, FILTER_PARAM_Data, Operator.EQUAL, filterData);
	}

	static HUIdsFilterData extractFilterData(@NonNull final DocumentFilter huIdsFilter)
	{
		if (isNotHUIdsFilter(huIdsFilter))
		{
			throw new AdempiereException("Not an HUIds filter: " + huIdsFilter);
		}

		final HUIdsFilterData huIdsFilterData = (HUIdsFilterData)huIdsFilter.getParameter(FILTER_PARAM_Data).getValue();
		if (huIdsFilterData == null)
		{
			throw new AdempiereException("No " + HUIdsFilterData.class + " found for " + huIdsFilter);
		}
		return huIdsFilterData;
	}

	public static Optional<HUIdsFilterData> extractFilterData(@Nullable final DocumentFilterList filters)
	{
		return findExisting(filters).map(HUIdsFilterHelper::extractFilterData);
	}

	public static boolean isNotHUIdsFilter(final DocumentFilter filter)
	{
		return !FILTER_ID.equals(filter.getFilterId());
	}
}
