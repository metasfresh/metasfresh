package de.metas.handlingunits.pricing.spi.impl;

import java.util.Optional;

import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.NotEqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.api.ProductPriceQuery;
import org.adempiere.pricing.api.ProductPriceQuery.IProductPriceQueryMatcher;
import org.adempiere.pricing.api.ProductPriceQuery.ProductPriceQueryMatcher;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.slf4j.Logger;

import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.interfaces.I_M_HU_PI_Item_Product_Aware;
import de.metas.logging.LogManager;
import de.metas.pricing.attributebased.impl.AttributePricing;

public class HUPricing extends AttributePricing
{
	private static final transient Logger logger = LogManager.getLogger(HUPricing.class);

	private static final String HUPIItemProductMatcher_NAME = "M_HU_PI_Item_Product_Matcher";
	private static final IProductPriceQueryMatcher HUPIItemProductMatcher_None = ProductPriceQueryMatcher.of(HUPIItemProductMatcher_NAME, EqualsQueryFilter.isNull(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID));
	private static final IProductPriceQueryMatcher HUPIItemProductMatcher_Any = ProductPriceQueryMatcher.of(HUPIItemProductMatcher_NAME, new NotEqualsQueryFilter<>(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, null));

	/**
	 * Registers a default matcher which filters out all product prices which have an M_HU_PI_Item_Product_ID set.
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
	static
	{
		AttributePricing.registerDefaultMatcher(HUPIItemProductMatcher_None);
	}

	@Override
	protected Optional<I_M_ProductPrice> findMatchingProductPrice(final IPricingContext pricingCtx)
	{
		//
		// Check if default price is set
		// NOTE: We only "override" for default price when we have no ASI, as it will be added automatically later.
		final I_M_AttributeSetInstance attributeSetInstance = getM_AttributeSetInstance(pricingCtx);
		if (attributeSetInstance == null)
		{
			final I_M_ProductPrice defaultProductPrice = findDefaultPriceOrNull(pricingCtx);
			if (defaultProductPrice != null)
			{
				// Our M_HU_PI_Item_Product matches the default price and there is no ASI to veto us from using that default.
				return Optional.of(defaultProductPrice);
			}
		}

		// task 09051: don't leave yet, because there might be a product price with just a M_HU_PI_Item_Product and no other attribute values and stuff to match
// @formatter:off
//		if (attributeSetInstance == null)
//		{
//			logger.debug("No ASI found: {}", pricingCtx);
//			return Optional.empty();
//		}
// @formatter:on

		// Get the price list version, if any.
		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv == null)
		{
			logger.debug("No price list version found: {}", pricingCtx);
			return Optional.empty();
		}

		//
		// Get the product price attribute which matches our Product, ASI, PriceListVersion and M_HU_PI_Item_Product_ID.
		final int huPIItemProductId = getM_HU_PI_Item_Product_ID(pricingCtx);
		if (huPIItemProductId <= 0)
		{
			logger.debug("No M_HU_PI_Item_Product_ID found: {}", pricingCtx);
			return Optional.empty();
		}
		final I_M_ProductPrice productPrice = ProductPriceQuery.newInstance(plv)
				.setM_Product_ID(pricingCtx.getM_Product_ID())
				.matchingAttributes(attributeSetInstance)
				.matching(createHUPIItemProductMatcher(huPIItemProductId))
				.firstMatching(I_M_ProductPrice.class);
		if (productPrice == null)
		{
			logger.debug("No product attribute pricing found: {}", pricingCtx);
			return Optional.empty(); // no matching
		}

		return Optional.of(productPrice);
	}

	@Override
	protected void setResultForProductPriceAttribute(
			final IPricingContext pricingCtx,
			final IPricingResult result,
			final org.compiere.model.I_M_ProductPrice productPrice)
	{
		super.setResultForProductPriceAttribute(pricingCtx, result, productPrice);

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
	 * @return the default product price, <b>if</b> it matches the <code>M_HU_PI_Item_Product_ID</code> of the given <code>pricingCtx</code>.
	 */
	private final I_M_ProductPrice findDefaultPriceOrNull(final IPricingContext pricingCtx)
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
		final boolean strictDefault = true; // backward compatible
		final I_M_ProductPrice defaultPrice = ProductPriceQuery.newInstance(plv)
				.setM_Product_ID(pricingCtx.getM_Product_ID())
				.onlyAttributePricing()
				.matching(HUPIItemProductMatcher_Any)
				.retrieveDefault(strictDefault, I_M_ProductPrice.class);
		if (defaultPrice == null)
		{
			return null;
		}

		//
		// Make sure the default product price attribute is matching our pricing context M_HU_PI_Item_Product_ID,
		// or it has no M_HU_PI_Item_Product_ID set.
		final int productPrice_HUPIItemProductId = defaultPrice.getM_HU_PI_Item_Product_ID();
		if (productPrice_HUPIItemProductId <= 0)
		{
			return defaultPrice;
		}
		else if (productPrice_HUPIItemProductId == getM_HU_PI_Item_Product_ID(pricingCtx))
		{
			return defaultPrice;
		}
		else
		{
			return null;
		}
	}

	private int getM_HU_PI_Item_Product_ID(final IPricingContext pricingCtx)
	{
		final Object referencedObj = pricingCtx.getReferencedObject();

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

	private static IProductPriceQueryMatcher createHUPIItemProductMatcher(final int huPIItemProductId)
	{
		if (huPIItemProductId <= 0)
		{
			return HUPIItemProductMatcher_None;
		}
		else
		{
			return ProductPriceQueryMatcher.of(HUPIItemProductMatcher_NAME, EqualsQueryFilter.of(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, huPIItemProductId));
		}

	}
}
