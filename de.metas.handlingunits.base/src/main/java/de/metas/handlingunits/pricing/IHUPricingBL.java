package de.metas.handlingunits.pricing;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice_Attribute;
import de.metas.interfaces.I_M_HU_PI_Item_Product_Aware;

public interface IHUPricingBL extends ISingletonService
{
	/**
	 * This method calls {@link de.metas.pricing.attributebased.IAttributePricingDAO#retrieveAllPriceAttributes(de.metas.adempiere.model.I_M_ProductPrice)
	 * IAttributePricingDAO.retrieveAllPriceAttributes()}, therefore filters that were registered
	 * {@link de.metas.pricing.attributebased.IAttributePricingDAO#registerQueryFilter(org.adempiere.ad.dao.IQueryFilter) IAttributePricingDA.registerQueryFilter()} play no role here.
	 *
	 * @param ctx
	 * @param m_Product_ID
	 * @param m_AttributeSetInstance_ID
	 * @param priceListVersion
	 * @param m_HU_PI_Item_Product_Id
	 * @param trxName
	 * @return
	 */
	I_M_ProductPrice_Attribute getPriceAttribute(Properties ctx,
			int m_Product_ID,
			int m_AttributeSetInstance_ID,
			I_M_PriceList_Version priceListVersion,
			int m_HU_PI_Item_Product_Id,
			String trxName);

	/**
	 * This method shall extract the M_HU_PI_Item_Product_ID from the referenced object of the given <code>context</code> ({@link IPricingContext#getReferencedObject()}). It shall distinguish the
	 * following cases
	 * <ul>
	 * <li>if the referenced object is <code>null</code>, then return <code>-1</code>
	 * <li>else, if the referenced object is a {@link I_M_HU_PI_Item_Product_Aware}, then it return its {@link I_M_HU_PI_Item_Product_Aware#getM_HU_PI_Item_Product_ID() getM_HU_PI_Item_Product_ID()}
	 * value
	 * <li>else, if the referenced object has a <code>M_HU_PI_Item_Product_ID</code> column, then return that column's value. IMPORTANANT: give precedence to a possible override value, see
	 * {@link org.adempiere.model.InterfaceWrapperHelper#getValueOverrideOrValue(Object, String)}
	 * <li>else, it returns <code>-1</code>
	 * </ul>
	 *
	 * @param context
	 * @return
	 */
	int getM_HU_PI_Item_Product_ID(IPricingContext context);

	/**
	 * See {@link #getM_HU_PI_Item_Product_ID(IPricingContext)}
	 * 
	 * @param context
	 * @return
	 */
	I_M_HU_PI_Item_Product getM_HU_PI_Item_ProductOrNull(IPricingContext context);
}
