package org.adempiere.product.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.product.service.IProductDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class ProductDAO implements IProductDAO
{

	@Override
	public I_M_Product_Acct retrieveM_Product_AcctOrNull(final I_M_Product product)
	{
		final IContextAware ctx = InterfaceWrapperHelper.getContextAware(product);

		final I_C_AcctSchema schema = Services.get(IAcctSchemaDAO.class).retrieveAcctSchema(ctx.getCtx());

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product_Acct.class, ctx)
				.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_C_AcctSchema_ID, schema.getC_AcctSchema_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_Product_Acct.class);
	}

	@Override
	@Cached(cacheName = I_M_Product.Table_Name + "#by#" + I_M_Product.COLUMNNAME_UPC)
	public I_M_Product retrieveProductByUPC(@CacheCtx final Properties ctx, final String upc)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Product.COLUMNNAME_UPC, upc)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create()
				.firstOnly(I_M_Product.class);
	}

	@Override
	@Cached(cacheName = I_M_Product.Table_Name + "#by#" + I_M_Product.COLUMNNAME_S_Resource_ID)
	public I_M_Product retrieveForResourceId(@CacheCtx final Properties ctx, final int resourceId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class, ctx, trxName)
				.addEqualsFilter(I_M_Product.COLUMN_S_Resource_ID, resourceId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create()
				.firstOnly(I_M_Product.class);
	}
}
