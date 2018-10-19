package de.metas.material.dispo.commons.repository.atp;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.compiere.Adempiere;
import org.compiere.db.Database;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.commons.AttributesKeyQueryHelper;
import de.metas.material.dispo.model.I_MD_Candidate_ATP_QueryResult;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
/* package */ final class AvailableToPromiseSqlHelper
{
	@VisibleForTesting
	public IQuery<I_MD_Candidate_ATP_QueryResult> createDBQueryForStockQuery(@NonNull final AvailableToPromiseQuery query)
	{
		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> queryBuilder = createDBQueryForStockQueryBuilder(query);

		final IQuery<I_MD_Candidate_ATP_QueryResult> dbQuery = queryBuilder
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.create();

		if (isRealSqlQuery())
		{
			Check.assume(dbQuery instanceof TypedSqlQuery, "If we are not in unit test mode, then our query has to be an sql query; query={}", dbQuery);
			final TypedSqlQuery<I_MD_Candidate_ATP_QueryResult> sqlDbQuery = (TypedSqlQuery<I_MD_Candidate_ATP_QueryResult>)dbQuery;

			final String dateString = Database.TO_DATE(TimeUtil.asTimestamp(query.getDate()), false);
			sqlDbQuery.setSqlFrom("de_metas_material.retrieve_atp_at_date(" + dateString + ")");
		}

		//
		return dbQuery;
	}

	@VisibleForTesting
	IQueryBuilder<I_MD_Candidate_ATP_QueryResult> createDBQueryForStockQueryBuilder(@NonNull final AvailableToPromiseQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> queryBuilder = //
				queryBL.createQueryBuilder(I_MD_Candidate_ATP_QueryResult.class);

		if (!isRealSqlQuery())
		{
			// Date; this only makes sense in unit test's there the I_MD_Candidate_ATP_QueryResult records to return were hand-crafted for the respective test
			queryBuilder.addCompareFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(query.getDate()));
		}

		// Warehouse
		final Set<Integer> warehouseIds = query.getWarehouseIds();
		if (!warehouseIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Warehouse_ID, warehouseIds);
		}

		// Product
		final List<Integer> productIds = query.getProductIds();
		if (!productIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Product_ID, productIds);
		}

		// BPartner
		final int bpartnerId = query.getBpartnerId();
		if (bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_ANY)
		{
			// nothing to filter
		}
		else if (bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_NONE)
		{
			queryBuilder.addEqualsFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_C_BPartner_Customer_ID, null);
		}
		else
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_C_BPartner_Customer_ID, bpartnerId, null);
		}

		//
		// Storage Attributes Key
		final AttributesKeyQueryHelper<I_MD_Candidate_ATP_QueryResult>//
		helper = AttributesKeyQueryHelper.createFor(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey);

		queryBuilder.filter(helper.createORFilterForStorageAttributesKeys(query.getStorageAttributesKeys()));
		return queryBuilder;
	}

	private boolean isRealSqlQuery()
	{
		final boolean isRealSqlQuery = !Adempiere.isUnitTestMode();
		return isRealSqlQuery;
	}



}
