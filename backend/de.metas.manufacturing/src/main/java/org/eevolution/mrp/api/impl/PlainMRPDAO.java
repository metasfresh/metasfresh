package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

public class PlainMRPDAO extends MRPDAO
{
	private final Map<ArrayKey, BigDecimal> qtysOnHandsMap = new HashMap<>();

	@Override
	public final BigDecimal getQtyOnHand(final Properties ctx, final int M_Warehouse_ID, final int M_Product_ID, final String trxName)
	{
		final ArrayKey key = Util.mkKey(M_Warehouse_ID, M_Product_ID);
		final BigDecimal qtyOnHand = qtysOnHandsMap.get(key);
		if (qtyOnHand == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyOnHand;
	}

	public final BigDecimal getQtyOnHand(final I_M_Warehouse warehouse, final I_M_Product product)
	{
		final ArrayKey key = mkKey(warehouse, product);
		final BigDecimal qtyOnHand = qtysOnHandsMap.get(key);
		if (qtyOnHand == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyOnHand;
	}

	public final void setQtyOnHand(final I_M_Warehouse warehouse, final I_M_Product product, final BigDecimal qtyOnHand)
	{
		final ArrayKey key = mkKey(warehouse, product);
		qtysOnHandsMap.put(key, qtyOnHand);
	}

	private ArrayKey mkKey(final I_M_Warehouse warehouse, final I_M_Product product)
	{
		final int warehouseId = warehouse.getM_Warehouse_ID();
		final int productId = product.getM_Product_ID();
		return Util.mkKey(warehouseId, productId);
	}
}
