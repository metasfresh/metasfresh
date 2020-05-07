package org.adempiere.uom.api.impl;

import java.math.BigDecimal;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.util.CacheCtx;

public class UOMConversionDAO implements IUOMConversionDAO
{
	@Override
	public List<I_C_UOM_Conversion> retrieveProductConversions(final Properties ctx, final I_M_Product product)
	{
		if (product == null || product.getM_Product_ID() <= 0)
		{
			return Collections.emptyList();
		}

		return retrieveProductConversions(ctx, product.getM_Product_ID());

	}	// getProductConversions

	/**
	 * Task 09304: cache and never expire, but note that the cache will be invalidated if someone changes the master data remotely.
	 * 
	 * @see org.compiere.util.CacheMgt#enableRemoteCacheInvalidationForTableName(String)
	 */
	// old javadoc, keeping it for reference: "task 09261: cache, but expire after 1 minute because the masterdata could be changed by another user."
	@Cached(cacheName = I_C_UOM_Conversion.Table_Name
			+ "#by"
			+ "#" + I_C_UOM_Conversion.COLUMNNAME_M_Product_ID
			, expireMinutes = Cached.EXPIREMINUTES_Never)
	public List<I_C_UOM_Conversion> retrieveProductConversions(@CacheCtx final Properties ctx, final int productID)
	{
		List<I_C_UOM_Conversion> list = new ArrayList<I_C_UOM_Conversion>();

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, productID, I_M_Product.class, ITrx.TRXNAME_None);
		// Add default conversion
		I_C_UOM_Conversion defaultRate = createProductDefaultConversion(product);
		list.add(defaultRate);

		// All the conversions defined for the product
		final List<I_C_UOM_Conversion> conversions = Services.get(IQueryBL.class).createQueryBuilder(I_C_UOM_Conversion.class, ctx, ITrx.TRXNAME_None)
				.filter(new EqualsQueryFilter<I_C_UOM_Conversion>(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, productID))
				// .filter(new EqualsQueryFilter<I_C_UOM_Conversion>(I_C_UOM_Conversion.COLUMNNAME_C_UOM_ID, product.))
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_UOM_Conversion.class);

		list.addAll(conversions);

		return Collections.unmodifiableList(list);
	}

	/**
	 * Parent Constructor
	 *
	 * @param parent product parent
	 */
	private I_C_UOM_Conversion createProductDefaultConversion(final I_M_Product product)
	{
		final int productUOMId = product.getC_UOM_ID();
		final int productId = product.getM_Product_ID();

		final I_C_UOM_Conversion conversion = InterfaceWrapperHelper.newInstance(I_C_UOM_Conversion.class, product);
		conversion.setM_Product_ID(productId);
		conversion.setC_UOM_ID(productUOMId);
		conversion.setC_UOM_To_ID(productUOMId);
		conversion.setMultiplyRate(BigDecimal.ONE);
		conversion.setDivideRate(BigDecimal.ONE);

		// NOTE: don't save it

		InterfaceWrapperHelper.setSaveDeleteDisabled(conversion, true);

		return conversion;
	}

	@Override
	public List<I_C_UOM_Conversion> retrieveGenericConversions(final Properties ctx)
	{
		final List<I_C_UOM_Conversion> conversions = Services.get(IQueryBL.class).createQueryBuilder(I_C_UOM_Conversion.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, null)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_UOM_Conversion.class);

		return conversions;
	}

}
