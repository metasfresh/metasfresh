package org.adempiere.inout.util;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.CustomColNames;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.CCache;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_BPartner;

/**
 * Contains maps to cache objects that are used in die shipment context so they need to be loaded only once from DB. Should be replaced with a generic caching implementation. This framework would need
 * to make sure that all cached objects be discarded (e.g. at the end of a transaction). I don't really like the existing {@link CCache} because I don't seen how to control stale objects and the
 * likes.
 * 
 * @author tobi42
 * 
 */
public class CachedObjects
{
	/**
	 * Cache for the orderLine's orders.
	 */
	private final Map<Integer, I_C_Order> orderCache = new HashMap<Integer, I_C_Order>();

	/**
	 * Cache for BPartners' {@link CustomColNames#C_BPartner_ALLOW_CONSOLIDATE_INOUT} values.
	 */
	private final Map<Integer, I_C_BPartner> bPartnerCache = new HashMap<Integer, I_C_BPartner>();
	private final Map<Integer, I_M_Product> productCache = new HashMap<Integer, I_M_Product>();
	private final Map<Integer, String> mmPolicyCache = new HashMap<Integer, String>();

//	private final Map<Integer, BigDecimal> unconfirmedQtys = new HashMap<Integer, BigDecimal>();
	private final Map<Integer, I_M_AttributeSet> attributeSetCache = new HashMap<Integer, I_M_AttributeSet>();

	public Map<Integer, I_C_Order> getOrderCache()
	{
		return orderCache;
	}

	public Map<Integer, I_M_Product> getProductCache()
	{
		return productCache;
	}

	public Map<Integer, String> getMmPolicyCache()
	{
		return mmPolicyCache;
	}

//	public Map<Integer, BigDecimal> getUnconfirmedQtys()
//	{
//		return unconfirmedQtys;
//	}

	public Map<Integer, I_M_AttributeSet> getAttributeSetCache()
	{
		return attributeSetCache;
	}

	public I_C_BPartner retrieveAndCacheBPartner(final I_C_OrderLine line)
	{
		I_C_BPartner bPartner = bPartnerCache.get(line.getC_BPartner_ID());

		if (bPartner == null)
		{
			bPartner = InterfaceWrapperHelper.create(line.getC_BPartner(), I_C_BPartner.class);
			bPartnerCache.put(line.getC_BPartner_ID(), bPartner);
		}
		return bPartner;
	}

	public I_C_BPartner retrieveAndCacheBPartner(final I_M_InOut inout)
	{
		I_C_BPartner bPartner = bPartnerCache.get(inout.getC_BPartner_ID());

		if (bPartner == null)
		{
			bPartner = InterfaceWrapperHelper.create(inout.getC_BPartner(), I_C_BPartner.class);
			bPartnerCache.put(inout.getC_BPartner_ID(), bPartner);
		}
		return bPartner;
	}

	public I_C_Order retrieveAndCacheOrder(final I_C_OrderLine line, final String trxName)
	{
		I_C_Order order = orderCache.get(line.getC_Order_ID());

		if (order == null)
		{
			order = InterfaceWrapperHelper.create(line.getC_Order(), I_C_Order.class);
			orderCache.put(line.getC_Order_ID(), order);
		}
		return order;
	}

	public I_M_Product retrieveAndCacheProduct(final I_C_OrderLine line)
	{
		final int productId = line.getM_Product_ID();
		if (productId <= 0)
		{
			return null;
		}

		final Map<Integer, I_M_Product> productCache = getProductCache();
		I_M_Product product = productCache.get(productId);
		if (product == null)
		{
			product = InterfaceWrapperHelper.create(line.getM_Product(), I_M_Product.class);
			productCache.put(productId, product);
		}
		return product;
	}

	public I_M_Product retrieveAndCacheProduct(final I_M_InOutLine line)
	{
		final int productId = line.getM_Product_ID();
		if (productId <= 0)
		{
			return null;
		}

		final Map<Integer, I_M_Product> productCache = getProductCache();
		I_M_Product product = productCache.get(productId);
		if (product == null)
		{
			product = InterfaceWrapperHelper.create(line.getM_Product(), I_M_Product.class);
			productCache.put(productId, product);
		}
		return product;
	}

}
