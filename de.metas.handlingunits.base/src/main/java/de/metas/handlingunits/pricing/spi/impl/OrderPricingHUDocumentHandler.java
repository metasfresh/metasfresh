package de.metas.handlingunits.pricing.spi.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IOrderBL;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute;

public class OrderPricingHUDocumentHandler implements IHUDocumentHandler
{
	/**
	 * Suggests the {@link I_M_HU_PI_Item_Product} for Order Quick Input
	 */
	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_ItemProductFor(final Object orderObj, final I_M_Product product)
	{
		Check.assumeInstanceOf(orderObj, I_C_Order.class, "orderObj not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(orderObj);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderObj);

		final I_C_Order order = InterfaceWrapperHelper.create(orderObj, I_C_Order.class);

		final I_M_PriceList_Version plv = Services.get(IOrderBL.class).getPriceListVersion(order);
		
		final int productId = product == null ? -1 : product.getM_Product_ID();

		final I_M_ProductPrice_Attribute productPriceAttribute = Services.get(IAttributePricingBL.class).getDefaultAttributePriceOrNull(order,
				productId,
				plv,
				false); // strictDefault

		if (null != productPriceAttribute)
		{
			final de.metas.handlingunits.model.I_M_ProductPrice_Attribute productPriceAttributeExt = InterfaceWrapperHelper.create(productPriceAttribute,
					de.metas.handlingunits.model.I_M_ProductPrice_Attribute.class);
			final I_M_HU_PI_Item_Product hu_pip = InterfaceWrapperHelper.create(ctx, productPriceAttributeExt.getM_HU_PI_Item_Product_ID(), I_M_HU_PI_Item_Product.class, trxName);
			return hu_pip;
		}

		return null;
	}

	@Override
	public void applyChangesFor(final Object document)
	{
		// Nothing to do for order.
	}
}
