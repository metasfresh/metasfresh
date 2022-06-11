package de.metas.handlingunits.pricing.spi.impl;

import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.NotEqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.interfaces.I_M_HU_PI_Item_Product_Aware;
import de.metas.logging.LogManager;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.attributebased.impl.AttributePricing;
import de.metas.pricing.service.ProductPriceQuery;
import de.metas.pricing.service.ProductPriceQuery.IProductPriceQueryMatcher;
import de.metas.pricing.service.ProductPriceQuery.ProductPriceQueryMatcher;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import lombok.NonNull;

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
	private static final IProductPriceQueryMatcher HUPIItemProductMatcher_Any = ProductPriceQueryMatcher.of(HUPIItemProductMatcher_NAME, NotEqualsQueryFilter.of(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, null));

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
		final I_M_PriceList_Version ctxPriceListVersion = pricingCtx.getM_PriceList_Version();
		if (ctxPriceListVersion == null)
		{
			logger.debug("No price list version found: {}", pricingCtx);
			return Optional.empty();
		}

		//
		// Get the product price attribute which matches our Product, ASI, PriceListVersion and packing material.
		final HUPIItemProductId packingMaterialId = getPackingMaterialId(pricingCtx);
		if (packingMaterialId == null)
		{
			logger.debug("No packing material found: {}", pricingCtx);
			return Optional.empty();
		}

		final ProductId productId = pricingCtx.getProductId();
		final I_M_ProductPrice productPrice = ProductPrices.iterateAllPriceListVersionsAndFindProductPrice(
				ctxPriceListVersion,
				priceListVersion -> findMatchingProductPriceOrNull(priceListVersion, productId, attributeSetInstance, packingMaterialId),
				TimeUtil.asZonedDateTime(pricingCtx.getPriceDate(), SystemTime.zoneId()));

		if (productPrice == null)
		{
			logger.debug("No product attribute pricing found: {}", pricingCtx);
			return Optional.empty(); // no matching
		}

		return Optional.of(productPrice);
	}

	private static I_M_ProductPrice findMatchingProductPriceOrNull(
			@NonNull final I_M_PriceList_Version plv,
			@NonNull final ProductId productId,
			@Nullable final I_M_AttributeSetInstance attributeSetInstance,
			@Nullable final HUPIItemProductId packingMaterialId)
	{
		final ProductPriceQuery productPriceQuery = ProductPrices.newQuery(plv)
				.setProductId(productId)
				.matching(createHUPIItemProductMatcher(packingMaterialId));

		// Match attributes if we have attributes.
		if (attributeSetInstance == null || attributeSetInstance.getM_AttributeSetInstance_ID() <= 0)
		{
			productPriceQuery.dontMatchAttributes();
		}
		else
		{
			productPriceQuery.notStrictlyMatchingAttributes(attributeSetInstance);
		}

		return productPriceQuery.firstMatching(I_M_ProductPrice.class);
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
		final I_M_PriceList_Version ctxPriceListVersion = pricingCtx.getM_PriceList_Version();
		if (ctxPriceListVersion == null)
		{
			return null;
		}

		//
		// Get the default product price attribute, if any
		final I_M_ProductPrice defaultPrice = ProductPrices.iterateAllPriceListVersionsAndFindProductPrice(
				ctxPriceListVersion,
				priceListVersion -> ProductPrices.newQuery(priceListVersion)
						.setProductId(pricingCtx.getProductId())
						.onlyAttributePricing()
						.onlyValidPrices(true)
						.matching(HUPIItemProductMatcher_Any)
						.retrieveStrictDefault(I_M_ProductPrice.class),
				TimeUtil.asZonedDateTime(pricingCtx.getPriceDate(), de.metas.common.util.time.SystemTime.zoneId()));
		if (defaultPrice == null)
		{
			return null;
		}

		//
		// Make sure the default product price attribute is matching our pricing context packing material,
		// or it has no packing material set.
		if (!isProductPriceMatchingContextPackingMaterial(defaultPrice, pricingCtx))
		{
			return null;
		}

		return defaultPrice;
	}

	/**
	 * @return true if product prices is matching the packing material from pricing context
	 *         or the pricing context does not have a packing material set
	 */
	private boolean isProductPriceMatchingContextPackingMaterial(final I_M_ProductPrice productPrice, final IPricingContext pricingCtx)
	{
		final HUPIItemProductId ctxPackingMaterialId = getPackingMaterialId(pricingCtx);
		if (ctxPackingMaterialId == null)
		{
			// We don't have a packing material in pricing context.
			// => Accept the packing material from product price
			return true;
		}

		final HUPIItemProductId productPricePackingMaterialId = HUPIItemProductId.ofRepoIdOrNull(productPrice.getM_HU_PI_Item_Product_ID());
		return HUPIItemProductId.equals(productPricePackingMaterialId, ctxPackingMaterialId);
	}

	private HUPIItemProductId getPackingMaterialId(final IPricingContext pricingCtx)
	{
		final Object referencedObj = pricingCtx.getReferencedObject();
		if (referencedObj == null)
		{
			return null;
		}

		//
		// check if we have an piip-aware
		if (referencedObj instanceof I_M_HU_PI_Item_Product_Aware)
		{
			final int packingMaterialRepoId = ((I_M_HU_PI_Item_Product_Aware)referencedObj).getM_HU_PI_Item_Product_ID();
			return HUPIItemProductId.ofRepoIdOrNull(packingMaterialRepoId);
		}

		//
		// check if there is a M_HU_PI_Item_Product_ID(_Override) column.
		if (InterfaceWrapperHelper.hasModelColumnName(referencedObj, I_M_HU_PI_Item_Product_Aware.COLUMNNAME_M_HU_PI_Item_Product_ID))
		{
			final Integer valueOverrideOrValue = InterfaceWrapperHelper.getValueOverrideOrValue(referencedObj, I_M_HU_PI_Item_Product_Aware.COLUMNNAME_M_HU_PI_Item_Product_ID);
			return valueOverrideOrValue == null ? null : HUPIItemProductId.ofRepoIdOrNull(valueOverrideOrValue.intValue());
		}

		return null;
	}

	public static IProductPriceQueryMatcher createHUPIItemProductMatcher(final HUPIItemProductId packingMaterialId)
	{
		if (packingMaterialId == null)
		{
			return HUPIItemProductMatcher_None;
		}
		else
		{
			return ProductPriceQueryMatcher.of(HUPIItemProductMatcher_NAME, EqualsQueryFilter.of(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, packingMaterialId));
		}
	}
}
