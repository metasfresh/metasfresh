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

import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList_Version;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.handlingunits.model.I_M_ProductPrice_Attribute;
import de.metas.handlingunits.pricing.IHUPricingBL;
import de.metas.logging.LogManager;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IAttributePricingDAO;
import de.metas.pricing.attributebased.impl.AttributePricing;

public class HUPricing extends AttributePricing
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final EqualsQueryFilter<de.metas.pricing.attributebased.I_M_ProductPrice_Attribute> FILTER_NoPIItemProduct = new EqualsQueryFilter<>(
			I_M_ProductPrice_Attribute.COLUMNNAME_M_HU_PI_Item_Product_ID, null);

	/**
	 * Calls {@link IAttributePricingDAO#registerQueryFilter(org.adempiere.ad.dao.IQueryFilter)} a filter so that the generic AttributePricing implementation won't see M_ProductPrice_Attributes that
	 * have a M_HU_PI_Item_Product_ID.
	 *
	 * From skype chat:
	 *
	 * <pre>
	 * [Dienstag, 4. Februar 2014 15:33] Cis:
	 * 
	 * if the HU pricing rule (that runs first) doesn't find a match, the attribute pricing rule runs next and can find a wrong match, because it can't "see" the M_HU_PI_Item_Product
	 * more concretely: we have two rules:
	 * IFCO A, with Red
	 * IFCO B with Blue
	 * 
	 * And we put a product in IFCO A with Blue
	 * 
	 * HU pricing rule won't find a match,
	 * Attribute pricing rule will match it with "Blue", which is wrong, since it should fall back to the "base" productPrice
	 *
	 * <pre>
	 *
	 * ..and that's why we register the filter here.
	 */
	public HUPricing()
	{
		super();
		Services.get(IAttributePricingDAO.class).registerQueryFilter(FILTER_NoPIItemProduct);
	}

	@Override
	protected Optional<I_M_ProductPrice_Attribute> findMatchingProductPriceAttribute(final IPricingContext pricingCtx)
	{
		//
		// Check if default price is set
		// NOTE: We only "override" for default price when we have no ASI, as it will be added automatically later.
		final int attributeSetInstanceId = getM_AttributeSetInstance_ID(pricingCtx);
		final I_M_ProductPrice_Attribute defaultProductPriceAttribute = getDefaultAttributePriceOrNull(pricingCtx);
		if (defaultProductPriceAttribute != null
				&& attributeSetInstanceId <= 0)
		{
			// Our M_HU_PI_Item_Product matches the default M_ProductPrice_Attribute and there is no ASI to veto us from using that default.
			return Optional.of(defaultProductPriceAttribute);
		}

// task 09051: don't leave yet, because there might be a M_ProductPrice_Attribute with just a M_HU_PI_Item_Product and no other attribute values and stuff to match 
// @formatter:off
//		if (attributeSetInstanceId <= 0)
//		{
//			logger.debug("No ASI found: {}", pricingCtx);
//			return Optional.absent();
//		}
// @formatter:on

		// Get the price list version, if any.
		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv == null)
		{
			logger.debug("No price list version found: {}", pricingCtx);
			return Optional.absent();
		}

		//
		// Get the product price attribute which matches our Product, ASI, PriceListVersion and M_HU_PI_Item_Product_ID.
		final IHUPricingBL huPricingBL = Services.get(IHUPricingBL.class);
		final Properties ctx = pricingCtx.getCtx();
		final String trxName = pricingCtx.getTrxName();
		final int productId = pricingCtx.getM_Product_ID();
		final int huPIItemProductId = huPricingBL.getM_HU_PI_Item_Product_ID(pricingCtx);
		final I_M_ProductPrice_Attribute productPriceAttribute = huPricingBL.getPriceAttribute(ctx,
				productId,
				attributeSetInstanceId,
				plv,
				huPIItemProductId,
				trxName);
		if (null == productPriceAttribute || productPriceAttribute.getM_ProductPrice_Attribute_ID() <= 0)
		{
			logger.debug("No product attribute pricing found: {}", pricingCtx);
			return Optional.absent(); // no matching
		}

		return Optional.of(productPriceAttribute);
	}

	@Override
	protected void setResultForProductPriceAttribute(
			final IPricingContext pricingCtx,
			final IPricingResult result,
			final de.metas.pricing.attributebased.I_M_ProductPrice_Attribute productPriceAttribute)
	{
		super.setResultForProductPriceAttribute(pricingCtx, result, productPriceAttribute);

		// @formatter:off
		// 08147: don't do anything special about the "HU price". Instead we make sure that
		//  *there is a proper UOM-conversion for QtyEntered => QtyEnteredInPriceUOM
		//  *LineNetAmt is computed from QtyEnteredInPriceUOM x PriceActual

		// older comment
		// 06942: reset the prices according to the HU price (if it actually is a HU price)
//		if (productPriceAttribute.isHUPrice())
//		{
//			Check.assume(productPriceAttribute.getM_HU_PI_Item_Product_ID() > 0,
//					"{} has a pip assigned, because otherwise we would not be where", productPriceAttribute);
//			final I_M_HU_PI_Item_Product pip = productPriceAttribute.getM_HU_PI_Item_Product();
//
//			final BigDecimal divisor = pip.getQty();
//
//			// 07674: we will round the price-per-unit to the PL's precision *even* if the line's net-amount will not be a clean multiple of the HU-price anymore.
//			// for the time being it's the user's job to choose their prices in accordance to their PIP-qtys.
//			final int precision = priceList.getPricePrecision();
//
//			final BigDecimal huPriceStd;
//			final BigDecimal huPriceList;
//			final BigDecimal huPriceLimit;
//			if (divisor.signum() > 0)
//			{
//				huPriceStd = productPriceAttribute.getPriceStd().divide(divisor, precision, RoundingMode.HALF_UP);
//				huPriceList = productPriceAttribute.getPriceList().divide(divisor, precision, RoundingMode.HALF_UP);
//				huPriceLimit = productPriceAttribute.getPriceLimit().divide(divisor, precision, RoundingMode.HALF_UP);
//			}
//			else
//			{
//				huPriceStd = BigDecimal.ZERO;
//				huPriceList = BigDecimal.ZERO;
//				huPriceLimit = BigDecimal.ZERO;
//			}
//			result.setPriceStd(huPriceStd);
//			result.setPriceList(huPriceList);
//			result.setPriceLimit(huPriceLimit);
//		}
		// @formatter:on
	}

	/**
	 * Returns the default I_M_ProductPrice_Attribute, <b>if</b> it matches the <code>M_HU_PI_Item_Product_ID</code> of the given <code>pricingCtx</code>.
	 * 
	 * @param pricingCtx
	 * @return
	 */
	private final I_M_ProductPrice_Attribute getDefaultAttributePriceOrNull(final IPricingContext pricingCtx)
	{
		//
		// Get the price list version, if any
		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv == null)
		{
			return null;
		}

		//
		// Get the default product price attribute, if any
		final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
		final boolean strictDefault = true; // backward compatible
		final I_M_ProductPrice_Attribute defaultAttributePrice =
				InterfaceWrapperHelper.create(

						attributePricingBL.getDefaultAttributePriceOrNull(
								pricingCtx.getReferencedObject(),
								pricingCtx.getM_Product_ID(),
								plv,
								strictDefault),

						I_M_ProductPrice_Attribute.class);
		if (defaultAttributePrice == null)
		{
			return null;
		}

		//
		// Make sure the default product price attribute is matching our pricing context M_HU_PI_Item_Product_ID,
		// or it has no M_HU_PI_Item_Product_ID set.
		final IHUPricingBL huPricingBL = Services.get(IHUPricingBL.class);
		if (huPricingBL.getM_HU_PI_Item_Product_ID(pricingCtx) == defaultAttributePrice.getM_HU_PI_Item_Product_ID()
				|| defaultAttributePrice.getM_HU_PI_Item_Product_ID() <= 0)
		{
			return defaultAttributePrice;
		}

		return null;
	}
}
