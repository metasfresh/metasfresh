/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.material.cockpit.filters;

import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;

public class QtyDemandSupplyFilters
{
	@NonNull
	public static IQuery<I_QtyDemand_QtySupply_V> createQuantitiesQueryFor(@NonNull final DocumentFilterList filters)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_QtyDemand_QtySupply_V> queryBuilder = queryBL
				.createQueryBuilder(I_QtyDemand_QtySupply_V.class);

		augmentQueryBuilder(queryBuilder, ProductFilterUtil.extractProductFilterVO(filters));

		return queryBuilder.create();
	}

	@NonNull
	public static IQuery<I_QtyDemand_QtySupply_V> createQuantitiesQueryFor(@NonNull final ProductId productId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL
				.createQueryBuilder(I_QtyDemand_QtySupply_V.class)
				.addEqualsFilter(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, productId)
				.create();
	}

	private static boolean augmentQueryBuilder(final IQueryBuilder<I_QtyDemand_QtySupply_V> queryBuilder, final ProductFilterVO productFilterVO)
	{
		final IQuery<I_M_Product> productQuery = ProductFilterUtil.createProductQueryOrNull(productFilterVO);
		if (productQuery == null)
		{
			return false;
		}

		queryBuilder.addInSubQueryFilter(I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, productQuery);
		return true;
	}
}
