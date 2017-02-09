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

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

import com.google.common.base.Optional;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IProductPriceAttributeAware;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute;

/**
 * Note: currently this implementation is used to update a given record's ASI when its {@link I_M_HU_PI_Item_Product} changes.
 *
 *
 */
public class OrderLinePricingHUDocumentHandler implements IHUDocumentHandler
{
	/**
	 * Does nothing and returns <code>null</code>.
	 */
	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_ItemProductFor(final Object document, final I_M_Product product)
	{
		// Not needed.
		return null;
	}

	/**
	 * Assumes that the given <code>document</code> is an order line.<br>
	 * It creates a new ASI using the default I_M_ProductPrice_Attribute for the given order line and set's the order line to reference the new ASI (ignoring a possible preexisting ASI).
	 * <p>
	 * The method does nothing if:
	 * <ul>
	 * <li><code>M_Product_ID</code> is not changed, or is changed, but the orderLine is new and already has another ASI. Rationale: in case the line is new and already has an ASI, we assume that this
	 * ASI was created and added for a reason, and is not stale, but as fresh as the product itself.
	 * <li>there is no default <code>M_ProductPrice_Attribute</code> to be found, or
	 * <li>the default <code>M_ProductPrice_Attribute</code> has a M_HU_PI_Item_Product_ID which differs from the order line's M_HU_PI_Item_Product_ID
	 * </ul>
	 *
	 *
	 * @see InterfaceWrapperHelper#isValueChanged(Object, String)
	 * @see IOrderLineBL#getPriceListVersion(de.metas.interfaces.I_C_OrderLine)
	 * @see IAttributePricingBL#getDefaultAttributePriceOrNull(Object, int, I_M_PriceList_Version, boolean)
	 */
	@Override
	public void applyChangesFor(final Object document)
	{
		Check.assumeInstanceOf(document, I_C_OrderLine.class, "document");

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(document, I_C_OrderLine.class);

		final boolean orderLineNew = InterfaceWrapperHelper.isNew(orderLine);
		final boolean productChanged = InterfaceWrapperHelper.isValueChanged(orderLine, org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID);
		final boolean asiAlreadySet = orderLine.getM_AttributeSetInstance_ID() > 0;

		final boolean createNewASI = productChanged && (!orderLineNew || !asiAlreadySet);

		if (!createNewASI)
		{
			return;
		}

		final I_M_ProductPrice_Attribute productPriceAttribute;

		// task 08839: get the productPriceAttribute, considering to get the explicid one the might be attached to this order line.
		// this is needed in case we create the order from an C_OCLand and do not wan tthe system to guess a default ASI and PIIP.
		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(orderLine);

		final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
		final Optional<IProductPriceAttributeAware> orderLineHasExplicitASI = attributePricingBL.getDynAttrProductPriceAttributeAware(asiAware);
		if (orderLineHasExplicitASI.isPresent()
				&& orderLineHasExplicitASI.get().isExplicitProductPriceAttribute())
		{
			final IProductPriceAttributeAware productPriceAttributeAware = orderLineHasExplicitASI.get();
			final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);
			final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
			final int productPriceAttributeID = productPriceAttributeAware.getM_ProductPrice_Attribute_ID();
			if (productPriceAttributeID > 0)
			{
				productPriceAttribute = InterfaceWrapperHelper.create(ctx, I_M_ProductPrice_Attribute.Table_Name, productPriceAttributeID, I_M_ProductPrice_Attribute.class, trxName);
			}
			else
			{
				productPriceAttribute = null;
			}
		}
		else
		{
			productPriceAttribute = getDefaultProductPriceAttribute(orderLine);
		}

		if (productPriceAttribute == null)
		{
			// no default Product Price Attribute was found => nothing to do
			return;
		}

		final de.metas.handlingunits.model.I_M_ProductPrice_Attribute productPriceAttributeEx = InterfaceWrapperHelper.create(productPriceAttribute,
				de.metas.handlingunits.model.I_M_ProductPrice_Attribute.class);
		final int pricePIItemProductId = productPriceAttributeEx.getM_HU_PI_Item_Product_ID();
		if (pricePIItemProductId > 0 && orderLine.getM_HU_PI_Item_Product_ID() != pricePIItemProductId)
		{
			// "HU PI Item Product" from Product Price Attribute does not match the one from Order Line
			return;
		}

		final I_M_AttributeSetInstance asi = attributePricingBL.generateASI(productPriceAttribute);
		orderLine.setM_AttributeSetInstance(asi);
	}

	/**
	 * Retrieves the <b>strict</b> default attribute price record for the given <code>orderLine</code>'s PLV and product.
	 *
	 * @param orderLine
	 * @return
	 * @see IOrderLineBL#getPriceListVersion(de.metas.interfaces.I_C_OrderLine)
	 * @see IAttributePricingBL#getDefaultAttributePriceOrNull(Object, int, I_M_PriceList_Version, boolean)
	 */
	private I_M_ProductPrice_Attribute getDefaultProductPriceAttribute(final I_C_OrderLine orderLine)
	{
		final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		final I_M_PriceList_Version plv = orderLineBL.getPriceListVersion(orderLine);

		// We want *the* Default I_M_ProductPrice_Attribute (no fallbacks etc), because we use this to generate the ASI.
		final boolean strictDefault = true;
		return attributePricingBL.getDefaultAttributePriceOrNull(orderLine.getM_Product_ID(), plv, strictDefault);
	}
}
