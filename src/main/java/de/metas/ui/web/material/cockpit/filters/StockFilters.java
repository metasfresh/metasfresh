package de.metas.ui.web.material.cockpit.filters;

import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class StockFilters
{
	public static IQuery<I_MD_Stock> createStockQueryFor(@NonNull final List<DocumentFilter> filters)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Stock> queryBuilder = queryBL
				.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter();

		boolean anyRestrictionAdded = false;
		for (final DocumentFilter filter : filters)
		{
			final List<DocumentFilterParam> filterParameters = filter.getParameters();
			for (final DocumentFilterParam filterParameter : filterParameters)
			{
				augmentStockQueryBuilderWithFilterParam(queryBuilder, filterParameter);
				anyRestrictionAdded = true;
			}
		}
		if (anyRestrictionAdded)
		{
			final IQuery<I_MD_Stock> query = queryBuilder.create();
			return query;
		}

		// avoid memory problems in case the filters are accidentally empty
		return queryBuilder.filter(ConstantQueryFilter.of(false)).create();
	}

	private static void augmentStockQueryBuilderWithFilterParam(
			@NonNull final IQueryBuilder<I_MD_Stock> queryBuilder,
			@NonNull final DocumentFilterParam filterParameter)
	{
		final String fieldName = filterParameter.getFieldName();
		final Object value = filterParameter.getValue();

		if (I_MD_Cockpit.COLUMNNAME_ProductName.equals(fieldName))
		{
			final String productName = (String)value;
			addLikeFilterToQueryBuilder(I_M_Product.COLUMN_Name, productName, queryBuilder);
		}
		else if (I_MD_Cockpit.COLUMNNAME_ProductValue.equals(fieldName))
		{
			final String productValue = (String)value;
			addLikeFilterToQueryBuilder(I_M_Product.COLUMN_Value, productValue, queryBuilder);
		}
	}

	private static void addLikeFilterToQueryBuilder(
			@NonNull final ModelColumn<I_M_Product, Object> column,
			@NonNull final String value,
			@NonNull final IQueryBuilder<I_MD_Stock> queryBuilder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Product> productByValueQuery = queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addStringLikeFilter(column, value, true)
				.create();

		queryBuilder.addInSubQueryFilter(
				I_MD_Stock.COLUMN_M_Product_ID,
				I_M_Product.COLUMN_M_Product_ID, productByValueQuery);
	}
}
