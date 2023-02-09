package de.metas.ui.web.material.cockpit.filters;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;

import static java.math.BigDecimal.ZERO;

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
	public static IQuery<I_MD_Stock> createStockQueryFor(@NonNull final DocumentFilterList filters)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Stock> queryBuilder = queryBL
				.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_MD_Stock.COLUMN_QtyOnHand, Operator.GREATER, ZERO);

		augmentQueryBuilder(queryBuilder, ProductFilterUtil.extractProductFilterVO(filters));

		// note: need to afford loading *all* MD_Stock records for the material cockpit; afaik there isn't a product-related restriction there.
		// it's OK because they are aggregated on product, locator and attributesKey, so we won't have a million of them
		return queryBuilder.create();
	}

	private static boolean augmentQueryBuilder(final IQueryBuilder<I_MD_Stock> queryBuilder, final ProductFilterVO productFilterVO)
	{
		final IQuery<I_M_Product> productQuery = ProductFilterUtil.createProductQueryOrNull(productFilterVO);
		if (productQuery == null)
		{
			return false;
		}

		queryBuilder.addInSubQueryFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, productQuery);
		return true;
	}
}
