package de.metas.ui.web.handlingunits;

import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import lombok.NonNull;
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

@UtilityClass
public class HUEditorRowFilters
{
	public static Predicate<HUEditorRow> toPredicate(@NonNull final HUEditorRowFilter filter)
	{
		Predicate<HUEditorRow> predicate = Predicates.alwaysTrue();
		if (filter == HUEditorRowFilter.ALL)
		{
			return predicate;
		}

		// Filter by row type
		final Select rowType = filter.getSelect();
		if (rowType == Select.ALL)
		{
			// nothing
		}
		else if (rowType == Select.ONLY_TOPLEVEL)
		{
			predicate = predicate.and(HUEditorRow::isTopLevel);
		}
		else if (rowType == Select.LU)
		{
			predicate = predicate.and(HUEditorRow::isLU);
		}
		else if (rowType == Select.TU)
		{
			predicate = predicate.and(HUEditorRow::isTU);
		}
		else if (rowType == Select.CU)
		{
			predicate = predicate.and(HUEditorRow::isCU);
		}
		else
		{
			throw new AdempiereException("Unknown: " + rowType);
		}

		// Filter by string filter
		final String stringFilter = filter.getUserInputFilter();
		if (!Check.isEmpty(stringFilter, true))
		{
			predicate = predicate.and(row -> row.matchesStringFilter(stringFilter));
		}

		// Exclude M_HU_IDs
		final ImmutableSet<Integer> excludeHUIds = filter.getExcludeHUIds();
		if (!excludeHUIds.isEmpty())
		{
			predicate = predicate.and(row -> !excludeHUIds.contains(row.getM_HU_ID()));
		}

		// Include HUStatuses
		final ImmutableSet<String> onlyHUStatuses = filter.getOnlyHUStatuses();
		if (!onlyHUStatuses.isEmpty())
		{
			predicate = predicate.and(row -> onlyHUStatuses.contains(row.getHUStatusKey()));
		}

		// Exclude HUStatuses
		final ImmutableSet<String> excludeHUStatuses = filter.getExcludeHUStatuses();
		if (!excludeHUStatuses.isEmpty())
		{
			predicate = predicate.and(row -> !excludeHUStatuses.contains(row.getHUStatusKey()));
		}

		return predicate;
	}

	/** Convert given filter to {@link IHUQueryBuilder}. All filter options which cannot be converted to {@link IHUQueryBuilder} are ignored. */
	public static IHUQueryBuilder toHUQueryBuilderPart(final HUEditorRowFilter filter)
	{
		final IHUQueryBuilder huQueryBuilder = Services.get(IHandlingUnitsDAO.class).createHUQueryBuilder();

		//
		// Filter by row type
		// IMPORTANT: don't filter out TUs/CUs because it might be that we are searching for included rows too
		// and in case we are filtering them out here the included TUs/CUs won't be found later...
		final Select rowType = filter.getSelect();
		if (rowType == Select.ALL)
		{
			// nothing
		}
		else if (rowType == Select.ONLY_TOPLEVEL)
		{
			huQueryBuilder.setOnlyTopLevelHUs(true);
		}
		// else if (rowType == Select.LU)
		// else if (rowType == Select.TU)
		else
		{
			// throw new AdempiereException("Not supported: " + rowType);
		}

		// Filter by string filter
		// final String stringFilter = filter.getUserInputFilter();
		// if (!Check.isEmpty(stringFilter, true))
		// {
		// throw new AdempiereException("String filter not supported: " + stringFilter);
		// }

		// Exclude M_HU_IDs
		huQueryBuilder.addHUIdsToExclude(filter.getExcludeHUIds());

		// Include HUStatuses
		huQueryBuilder.addHUStatusesToInclude(filter.getOnlyHUStatuses());

		// Exclude HUStatuses
		huQueryBuilder.addHUStatusesToExclude(filter.getExcludeHUStatuses());

		return huQueryBuilder;
	}

}
