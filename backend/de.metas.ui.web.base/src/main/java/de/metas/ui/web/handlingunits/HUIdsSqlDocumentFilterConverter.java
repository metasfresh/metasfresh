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

package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.compiere.util.Env;

import java.util.Objects;

public final class HUIdsSqlDocumentFilterConverter implements SqlDocumentFilterConverter
{
	// services
	private IHandlingUnitsDAO handlingUnitsDAO() { return Services.get(IHandlingUnitsDAO.class); }

	HUIdsSqlDocumentFilterConverter()
	{
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return Objects.equals(filterId, HUIdsFilterHelper.FILTER_ID);
	}

	@Override
	public FilterSql getSql(
			@NonNull final DocumentFilter filter,
			final SqlOptions sqlOpts_NOTUSED,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		final HUIdsFilterData huIdsFilter = HUIdsFilterHelper.extractFilterData(filter);
		if (huIdsFilter.isAcceptAll())
		{
			return FilterSql.ALLOW_ALL; // no restrictions were specified; pass through
		}
		else if (huIdsFilter.isAcceptNone())
		{
			return FilterSql.ALLOW_NONE;
		}
		else
		{
			final IHUQueryBuilder huQuery = toHUQuery(huIdsFilter);
			return FilterSql.ofWhereClause(toSql(huQuery));
		}
	}

	@NonNull
	private IHUQueryBuilder toHUQuery(final HUIdsFilterData huIdsFilter)
	{
		Check.assume(!huIdsFilter.isAcceptAll(), "shall not be an accept all filter: {}", huIdsFilter);
		Check.assume(!huIdsFilter.isAcceptNone(), "shall not be an accept none filter: {}", huIdsFilter);

		IHUQueryBuilder huQuery = huIdsFilter.getInitialHUQueryOrNull();
		if (huQuery == null)
		{
			huQuery = handlingUnitsDAO().createHUQueryBuilder()
					.setOnlyActiveHUs(false) // let other enforce this rule
					.setOnlyTopLevelHUs(false); // let other enforce this rule
		}
		huQuery.setContext(PlainContextAware.newOutOfTrx());

		// Only HUs
		final ImmutableSet<HuId> onlyHUIds = huIdsFilter.getFixedHUIds().orElse(null);
		if (onlyHUIds != null)
		{
			huQuery.addOnlyHUIds(HuId.toRepoIds(onlyHUIds));
		}

		// Exclude HUs
		huQuery.addHUIdsToExclude(HuId.toRepoIds(huIdsFilter.getShallNotHUIds()));

		//
		return huQuery;
	}

	private static SqlAndParams toSql(@NonNull final IHUQueryBuilder huQuery)
	{
		final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(huQuery.createQueryFilter());

		return SqlAndParams.of(
				sqlQueryFilter.getSql(),
				sqlQueryFilter.getSqlParams(Env.getCtx()));
	}

}
