package de.metas.handlingunits.pricing.spi.impl;

import java.util.Optional;

import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.NotEqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.slf4j.Logger;

import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.interfaces.I_M_HU_PI_Item_Product_Aware;
import de.metas.logging.LogManager;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.attributebased.impl.AttributePricing;
import de.metas.pricing.service.ProductPriceQuery;
import de.metas.pricing.service.ProductPrices;
import de.metas.pricing.service.ProductPriceQuery.IProductPriceQueryMatcher;
import de.metas.pricing.service.ProductPriceQuery.ProductPriceQueryMatcher;

/**
 * Note that we invoke {@link AttributePricing#registerDefaultMatcher(IProductPriceQueryMatcher)} with {@link #HUPIItemProductMatcher_None} (in a model interceptor)
 * to make sure that our super class will ignore those product price records that have a {@code M_HU_PI_Item_Product_ID} set.<br>
 * That way this class can reuse a lot of stuff like the {@link #applies(IPricingContext, IPricingResult)} method from its superclass.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUPricing extends AttributePricing
{
	private static final transient Logger logger = LogManager.getLogger(HUPricing.class);

	private static final String HUPIItemProductMatcher_NAME = "M_HU_PI_Item_Product_Matcher";
	public static final IProductPriceQueryMatcher HUPIItemProductMatcher_None = ProductPriceQueryMatcher.of(HUPIItemProductMatcher_NAME, EqualsQueryFilter.isNull(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID));

	/**
	 * Matches any product price with a not-null M_HU_PI_Item_Product_ID.
	 */
	private static final IProductPriceQueryMatcher HUPIItemProductMatcher_Any = ProductPriceQueryMatcher.of(HUPIItemProductMatcher_NAME, new NotEqualsQueryFilter<>(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, null));

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

		final ProductPriceQuery productPriceQuery = ProductPrices.newQuery(plv)
				.setM_Product_ID(pricingCtx.getM_Product_ID())
				.matching(createHUPIItemProductMatcher(huPIItemProductId));

		// Match attributes if we have attributes.
		if (attributeSetInstance == null || attributeSetInstance.getM_AttributeSetInstance_ID() <= 0)
		{
			productPriceQuery.dontMatchAttributes();
		}
		else
		{
			productPriceQuery.matchingAttributes(attributeSetInstance);
		}

		final I_M_ProductPrice productPrice = productPriceQuery.firstMatching(I_M_ProductPrice.class);
		if (productPrice == null)
		{
			logger.debug("No product attribute pricing found: {}", pricingCtx);
			return Optional.empty(); // no matching
		}

		return Optional.of(productPrice);
	}

	/**
	 * Just calls the parent method and nothing else.
	 * In particular, does not do anything special about the "HU price".
	 * Instead we make sure that
	 * <ul>
	 * <li>there is a proper UOM-conversion for QtyEntered => QtyEnteredInPriceUOM</li>
	 * <li>LineNetAmt is computed from QtyEnteredInPriceUOM x PriceActual</li>
	 * </ul>
	 * 
	 * @task 08147
	 */
	@Override
	protected void setResultForProductPriceAttribute(
			final IPricingContext pricingCtx,
			final IPricingResult result,
			final org.compiere.model.I_M_ProductPrice productPrice)
	{
		super.setResultForProductPriceAttribute(pricingCtx, result, productPrice);
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
		final I_M_ProductPrice defaultPrice = ProductPrices.newQuery(plv)
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
		final int ctxPIItemProductId = getM_HU_PI_Item_Product_ID(pricingCtx);
		if (ctxPIItemProductId <= 0)
		{
			// We don't have a M_HU_PI_Item_Product_ID on the pricing context.
			// Return the default price. It's M_HU_PI_Item_Product_ID will be used, e.g. in the C_OrderLine or C_OLCand which this invocation is about
			return defaultPrice;
		}

		final int productPrice_HUPIItemProductId = defaultPrice.getM_HU_PI_Item_Product_ID();
		if (productPrice_HUPIItemProductId == ctxPIItemProductId)
		{
			return defaultPrice;
		}

		return null;
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

	public static IProductPriceQueryMatcher createHUPIItemProductMatcher(final int huPIItemProductId)
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
