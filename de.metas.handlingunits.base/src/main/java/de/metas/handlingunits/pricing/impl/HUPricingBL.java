package de.metas.handlingunits.pricing.impl;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice_Attribute;
import de.metas.handlingunits.pricing.IHUPricingBL;
import de.metas.interfaces.I_M_HU_PI_Item_Product_Aware;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IAttributePricingDAO;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute_Line;

public class HUPricingBL implements IHUPricingBL
{

	@Override
	public I_M_ProductPrice_Attribute getPriceAttribute(final Properties ctx,
			final int productId,
			final int attributeSetInstanceId,
			final I_M_PriceList_Version priceListVersion,
			final int piItemProductId,
			final String trxName)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final IAttributePricingDAO attributePricingDAO = Services.get(IAttributePricingDAO.class);

		final de.metas.adempiere.model.I_M_ProductPrice productPrice = priceListDAO.retrieveProductPriceOrNull(priceListVersion, productId);
		if (productPrice == null)
		{
			return null;
		}

		final I_M_AttributeSetInstance attributeSetInstance = InterfaceWrapperHelper.create(ctx, attributeSetInstanceId, I_M_AttributeSetInstance.class, trxName);
		final List<I_M_AttributeInstance> instances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(attributeSetInstance);

		final List<I_M_ProductPrice_Attribute> productPriceAttributes = InterfaceWrapperHelper.createList(
				attributePricingDAO.retrievePriceAttributes(productPrice),
				I_M_ProductPrice_Attribute.class);

		for (final I_M_ProductPrice_Attribute productPriceAttribute : productPriceAttributes)
		{
			// If M_HU_PI_Item_Product_ID is not set then this Product Price Attribute is not subject of HU pricing
			if (productPriceAttribute.getM_HU_PI_Item_Product_ID() <= 0)
			{
				continue;
			}

			// Check if M_HU_PI_Item_Product_ID matches
			if (productPriceAttribute.getM_HU_PI_Item_Product_ID() != piItemProductId)
			{
				continue;
			}

			if (attributeSetInstanceId <= 0)
			{
				// For items with no ASI, we must make sure the product price attribute has no ASIs either.
				final List<I_M_ProductPrice_Attribute_Line> lines = attributePricingDAO.retrieveAttributeLines(productPriceAttribute);
				if (!lines.isEmpty())
				{
					continue;
				}
			}

			if (Services.get(IAttributePricingBL.class).isValidProductPriceAttribute(productPriceAttribute, instances))
			{
				return productPriceAttribute;
			}
		}
		return null;
	}

	/**
	 *
	 * @param context
	 * @return mHuPiItemProduct or -1 if no mHuPiItemProduct was found
	 */
	@Override
	public int getM_HU_PI_Item_Product_ID(final IPricingContext context)
	{
		final Object referencedObj = context.getReferencedObject();

		if (null == referencedObj)
		{
			return -1;
		}

		//
		// check if we have an piip-aware
		if (referencedObj instanceof I_M_HU_PI_Item_Product_Aware)
		{
			return ((I_M_HU_PI_Item_Product_Aware)referencedObj).getM_HU_PI_Item_Product_ID();
		}

		//
		// check if there is a M_HU_PI_Item_Product_ID(_Override) column.
		if (InterfaceWrapperHelper.hasModelColumnName(referencedObj, I_M_HU_PI_Item_Product_Aware.COLUMNNAME_M_HU_PI_Item_Product_ID))
		{
			final Integer valueOverrideOrValue = InterfaceWrapperHelper.getValueOverrideOrValue(referencedObj, I_M_HU_PI_Item_Product_Aware.COLUMNNAME_M_HU_PI_Item_Product_ID);
			return valueOverrideOrValue == null ? 0 : valueOverrideOrValue.intValue();
		}

		return -1;
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_ProductOrNull(final IPricingContext context)
	{
		final int pipID = getM_HU_PI_Item_Product_ID(context);
		if (pipID <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(context.getCtx(), pipID, I_M_HU_PI_Item_Product.class, context.getTrxName());
	}
}
