package org.eevolution.api;

import java.util.Arrays;
import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.product.ProductId;
import de.metas.util.ISingletonService;

public interface IProductBOMDAO extends ISingletonService
{
	I_PP_Product_BOM getById(ProductBOMId bomId);

	@Deprecated
	default I_PP_Product_BOM getById(final int productBomId)
	{
		return productBomId > 0 ? getById(ProductBOMId.ofRepoId(productBomId)) : null;
	}

	I_PP_Product_BOMLine getBOMLineById(int productBOMLineId);

	List<I_PP_Product_BOMLine> retrieveLines(I_PP_Product_BOM productBOM);

	List<I_PP_Product_BOMLine> retrieveLines(I_PP_Product_BOM productBOM, Date date);

	int retrieveLastLineNo(int ppProductBOMId);

	int retrieveDefaultBOMId(I_M_Product product);

	I_PP_Product_BOM retrieveDefaultBOM(I_M_Product product);

	int getDefaultProductBOMIdByProductId(ProductId productId);

	boolean hasBOMs(I_M_Product product);

	IQuery<I_PP_Product_BOMLine> retrieveBOMLinesForProductQuery(Properties ctx, int productId, String trxName);

	List<I_PP_Product_BOM> retrieveBOMsContainingExactProducts(Collection<Integer> productIds);

	default List<I_PP_Product_BOM> retrieveBOMsContainingExactProducts(final Integer... productIds)
	{
		return retrieveBOMsContainingExactProducts(Arrays.asList(productIds));
	}

	void save(I_PP_Product_BOMLine bomLine);

	ProductBOMId createBOM(BOMCreateRequest request);

	ProductId getBOMProductId(ProductBOMId bomId);
}
