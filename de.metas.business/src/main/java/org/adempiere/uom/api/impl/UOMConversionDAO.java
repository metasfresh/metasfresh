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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionDAO;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.cache.annotation.CacheCtx;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

public class UOMConversionDAO implements IUOMConversionDAO
{
	@Override
	public List<I_C_UOM_Conversion> retrieveProductConversions(@NonNull final ProductId productId)
	{
		return retrieveProductConversions(Env.getCtx(), productId);
	}

	/**
	 * Task 09304: cache and never expire, but note that the cache will be invalidated if someone changes the master data remotely.
	 * 
	 * @see de.metas.cache.CacheMgt#enableRemoteCacheInvalidationForTableName(String)
	 */
	// old javadoc, keeping it for reference: "task 09261: cache, but expire after 1 minute because the masterdata could be changed by another user."
	@Cached(cacheName = I_C_UOM_Conversion.Table_Name
			+ "#by"
			+ "#" + I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, expireMinutes = Cached.EXPIREMINUTES_Never)
	public List<I_C_UOM_Conversion> retrieveProductConversions(@CacheCtx final Properties ctx, final ProductId productId)
	{
		List<I_C_UOM_Conversion> list = new ArrayList<>();

		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		// Add default conversion
		I_C_UOM_Conversion defaultRate = createProductDefaultConversion(product);
		list.add(defaultRate);

		// All the conversions defined for the product
		final List<I_C_UOM_Conversion> conversions = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_UOM_Conversion.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_UOM_Conversion.class);

		list.addAll(conversions);

		return ImmutableList.copyOf(list);
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
