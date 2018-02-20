package de.metas.ui.web.material.cockpit.filters;

import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.document.filter.DocumentFilter;
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
		if (augmentQueryBuilder(queryBuilder, ProductFilterUtil.extractProductFilterVO(filters)))
		{
			anyRestrictionAdded = true;
		}

		if (anyRestrictionAdded)
		{
			return queryBuilder.create();
		}
		else
		{
			// avoid memory problems in case the filters are accidentally empty
			return queryBuilder.filter(ConstantQueryFilter.of(false)).create();
		}
	}

	private static boolean augmentQueryBuilder(final IQueryBuilder<I_MD_Stock> queryBuilder, final ProductFilterVO productFilterVO)
	{
		final IQuery<I_M_Product> productQuery = ProductFilterUtil.createProductQueryOrNull(productFilterVO);
		if (productQuery == null)
		{
			return false;
		}

		queryBuilder.addInSubQueryFilter(I_MD_Stock.COLUMN_M_Product_ID, I_M_Product.COLUMN_M_Product_ID, productQuery);
		return true;
	}
}
