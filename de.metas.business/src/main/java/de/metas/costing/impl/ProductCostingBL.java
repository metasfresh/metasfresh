package de.metas.costing.impl;

import org.adempiere.acct.api.AcctSchema;
import org.adempiere.acct.api.AcctSchemaId;
import org.adempiere.acct.api.IAcctSchemaDAO;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.model.MProductCategoryAcct;

import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProductCostingBL implements IProductCostingBL
{
	@Override
	public CostingLevel getCostingLevel(@NonNull final I_M_Product product, @NonNull final AcctSchema acctSchema)
	{
		final int productCategoryId = product.getM_Product_Category_ID();
		final AcctSchemaId acctSchemaId = acctSchema.getId();
		final I_M_Product_Category_Acct pca = MProductCategoryAcct.get(productCategoryId, acctSchemaId);
		if (pca != null)
		{
			final CostingLevel pcCostingLevel = CostingLevel.forNullableCode(pca.getCostingLevel());
			if (pcCostingLevel != null)
			{
				return pcCostingLevel;
			}
		}
		return acctSchema.getCosting().getCostingLevel();
	}

	@Override
	public CostingLevel getCostingLevel(final ProductId productId, final AcctSchemaId acctSchemaId)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		return getCostingLevel(product, acctSchema);
	}

	@Override
	public CostingLevel getCostingLevel(final ProductId productId, final AcctSchema acctSchema)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		return getCostingLevel(product, acctSchema);
	}

	@Override
	public CostingMethod getCostingMethod(final I_M_Product product, final AcctSchema acctSchema)
	{
		final int productCategoryId = product.getM_Product_Category_ID();
		final I_M_Product_Category_Acct pca = MProductCategoryAcct.get(productCategoryId, acctSchema.getId());
		if (pca != null)
		{
			final CostingMethod pcaCostingMethod = CostingMethod.ofNullableCode(pca.getCostingMethod());
			if (pcaCostingMethod != null)
			{
				return pcaCostingMethod;
			}
		}

		return acctSchema.getCosting().getCostingMethod();
	}

	@Override
	public CostingMethod getCostingMethod(final ProductId productId, final AcctSchemaId acctSchemaId)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		final AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		return getCostingMethod(product, acctSchema);
	}

	@Override
	public CostingMethod getCostingMethod(final ProductId productId, final AcctSchema acctSchema)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		return getCostingMethod(product, acctSchema);
	}
}
