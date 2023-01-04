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
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Set;

class HUIdsFilterDataToSqlCaseConverter implements HUIdsFilterData.CaseConverter<FilterSql>
{
	// services
	private IHandlingUnitsDAO handlingUnitsDAO() {return Services.get(IHandlingUnitsDAO.class);}

	@Nullable
	private FilterSql.RecordsToAlwaysIncludeSql toAlwaysIncludeSql(final @NonNull Set<HuId> alwaysIncludeHUIds)
	{
		return !alwaysIncludeHUIds.isEmpty()
				? FilterSql.RecordsToAlwaysIncludeSql.ofColumnNameAndRecordIds(I_M_HU.COLUMNNAME_M_HU_ID, alwaysIncludeHUIds)
				: null;
	}

	private IHUQueryBuilder newHUQuery()
	{
		return handlingUnitsDAO()
				.createHUQueryBuilder()
				.setContext(PlainContextAware.newOutOfTrx())
				.setOnlyActiveHUs(false) // let other enforce this rule
				.setOnlyTopLevelHUs(false); // let other enforce this rule
	}

	private SqlAndParams toSql(@NonNull final IHUQueryBuilder huQuery)
	{
		final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(huQuery.createQueryFilter());

		return SqlAndParams.of(
				sqlQueryFilter.getSql(),
				sqlQueryFilter.getSqlParams(Env.getCtx()));
	}

	@Override
	public FilterSql acceptAll() {return FilterSql.ALLOW_ALL;}

	@Override
	public FilterSql acceptAllBut(@NonNull final Set<HuId> alwaysIncludeHUIds, @NonNull final Set<HuId> excludeHUIds)
	{
		final SqlAndParams whereClause = !excludeHUIds.isEmpty()
				? toSql(newHUQuery().addHUIdsToExclude(excludeHUIds))
				: null;

		return FilterSql.builder()
				.whereClause(whereClause)
				.alwaysIncludeSql(toAlwaysIncludeSql(alwaysIncludeHUIds))
				.build();
	}

	@Override
	public FilterSql acceptNone() {return FilterSql.ALLOW_NONE;}

	@Override
	public FilterSql acceptOnly(@NonNull final HuIdsFilterList fixedHUIds, @NonNull final Set<HuId> alwaysIncludeHUIds)
	{
		final SqlAndParams whereClause = fixedHUIds.isNone()
				? FilterSql.ALLOW_NONE.getWhereClause()
				: toSql(newHUQuery().addOnlyHUIds(fixedHUIds.toSet()));

		return FilterSql.builder()
				.whereClause(whereClause)
				.alwaysIncludeSql(toAlwaysIncludeSql(alwaysIncludeHUIds))
				.build();
	}

	@Override
	public FilterSql huQuery(final @NonNull IHUQueryBuilder initialHUQueryCopy, final @NonNull Set<HuId> alwaysIncludeHUIds, final @NonNull Set<HuId> excludeHUIds)
	{
		initialHUQueryCopy.setContext(PlainContextAware.newOutOfTrx());
		initialHUQueryCopy.addHUIdsToAlwaysInclude(alwaysIncludeHUIds);
		initialHUQueryCopy.addHUIdsToExclude(excludeHUIds);

		return FilterSql.builder()
				.whereClause(toSql(initialHUQueryCopy))
				.alwaysIncludeSql(toAlwaysIncludeSql(alwaysIncludeHUIds))
				.build();
	}
}
