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
import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlFilter;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.NonNull;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public final class HUIdsSqlDocumentFilterConverter implements SqlDocumentFilterConverter
{
	HUIdsSqlDocumentFilterConverter()
	{
	}

	@Override
	public boolean canConvert(final String filterId)
	{
		return Objects.equals(filterId, HUIdsFilterHelper.FILTER_ID);
	}

	@Override
	public SqlFilter getSql(
			@NonNull final DocumentFilter filter,
			final SqlOptions sqlOpts_NOTUSED,
			@NonNull final SqlDocumentFilterConverterContext context)
	{
		final HUIdsFilterData huIdsFilter = HUIdsFilterHelper.extractFilterData(filter);

		if (huIdsFilter.isAcceptAll())
		{
			return SqlFilter.ACCEPT_ALL; // no restrictions were specified; pass through
		}
		else if (huIdsFilter.isAcceptNone())
		{
			return SqlFilter.ACCEPT_NONE;
		}
		else
		{
			final SqlFilter.SqlFilterBuilder sqlFilterBuilder = SqlFilter.builder();

			//
			// Filter clause
			final IHUQueryBuilder initialHUQuery = huIdsFilter.getInitialHUQueryOrNull();
			final ImmutableSet<HuId> initialHUIds = huIdsFilter.getInitialHUIdsOrNull();
			if (initialHUQuery != null)
			{
				sqlFilterBuilder.filterClause(toSqlAndParams(initialHUQuery));
			}
			else if (initialHUIds != null)
			{
				if (initialHUIds.isEmpty())
				{
					sqlFilterBuilder.filterClause(SqlAndParams.ACCEPT_NONE);
				}
				else
				{
					sqlFilterBuilder.filterClause(toSqlInArray(I_M_HU.COLUMNNAME_M_HU_ID, initialHUIds));
				}
			}

			//
			// Include clause
			final ImmutableSet<HuId> mustHUIds = huIdsFilter.getMustHUIds();
			if (!mustHUIds.isEmpty())
			{
				sqlFilterBuilder.includeClause(toSqlInArray(I_M_HU.COLUMNNAME_M_HU_ID, mustHUIds));
			}

			//
			// Exclude clause
			final ImmutableSet<HuId> shallNotHUIds = huIdsFilter.getShallNotHUIds();
			if (!shallNotHUIds.isEmpty())
			{
				sqlFilterBuilder.excludeClause(toSqlInArray(I_M_HU.COLUMNNAME_M_HU_ID, shallNotHUIds));
			}

			return sqlFilterBuilder.build();
		}
	}

	@SuppressWarnings("SameParameterValue")
	private static SqlAndParams toSqlInArray(@NonNull final String columnName, @NonNull final Collection<?> values)
	{
		final ArrayList<Object> sqlParams = new ArrayList<>();
		final String sql = DB.buildSqlList(columnName, values, sqlParams);
		return SqlAndParams.of(sql, sqlParams);
	}

	private static SqlAndParams toSqlAndParams(@NonNull final IHUQueryBuilder huQuery)
	{
		final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(huQuery.createQueryFilter());
		return SqlAndParams.of(sqlQueryFilter.getSql(), sqlQueryFilter.getSqlParams(Env.getCtx()));
	}
}
