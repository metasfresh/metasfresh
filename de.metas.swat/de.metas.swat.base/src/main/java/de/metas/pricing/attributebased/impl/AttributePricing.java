package de.metas.pricing.attributebased.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.api.ProductPriceQuery;
import org.adempiere.pricing.api.ProductPriceQuery.IProductPriceQueryMatcher;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.logging.LogManager;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IProductPriceAware;
import de.metas.pricing.attributebased.ProductPriceAware;

public class AttributePricing extends PricingRuleAdapter
{
	private static final Logger logger = LogManager.getLogger(AttributePricing.class);

	private static final CopyOnWriteArrayList<IProductPriceQueryMatcher> _defaultMatchers = new CopyOnWriteArrayList<>();

	public static final void registerDefaultMatcher(final IProductPriceQueryMatcher matcher)
	{
		Check.assumeNotNull(matcher, "Parameter matcher is not null");
		_defaultMatchers.addIfAbsent(matcher);
		logger.info("Registered default matcher: {}", matcher);
	}
	
	private List<IProductPriceQueryMatcher> getDefaultMatchers()
	{
		return _defaultMatchers;
	}

	/**
	 * Checks if the attribute pricing rule could be applied. Mainly it checks:
	 * <ul>
	 * <li>the result shall not be already calculated
	 * <li>a product shall exist in pricing context
	 * <li>a matching attributed {@link I_M_ProductPrice} shall be found
	 * </ul>
	 */
	@Override
	public final boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (result.isCalculated())
		{
			logger.debug("Not applying because already calculated: {}", result);
			return false;
		}

		if (pricingCtx.getM_Product_ID() <= 0)
		{
			logger.debug("Not applying because no product: {}", pricingCtx);
			return false;
		}

		if (!getAttributeSetInstanceAware(pricingCtx).isPresent())
		{
			logger.debug("Not applying because not ASI aware: {}", pricingCtx);
			return false;
		}

		//
		// Gets the product price attribute, if any.
		// If nothing found, this pricing rule does not apply.
		if (!getProductPrice(pricingCtx).isPresent())
		{
			logger.debug("Not applying because no product attribute pricing found: {}" + pricingCtx);
			return false;
		}

		return true;
	}

	@Override
	public final void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		// Get the product price attribute to use
		// NOTE: at this point it shall not be null because we just validate it in "applies".
		final I_M_ProductPrice productPrice = getProductPrice(pricingCtx).get();

		setResultForProductPriceAttribute(pricingCtx, result, productPrice);
	}

	/**
	 * Updates the {@link IPricingResult} using the given <code>productPriceAttribute</code>.
	 * 
	 * @param pricingCtx
	 * @param result
	 * @param productPriceAttribute
	 */
	@OverridingMethodsMustInvokeSuper
	protected void setResultForProductPriceAttribute(
			final IPricingContext pricingCtx,
			final IPricingResult result,
			final I_M_ProductPrice productPrice)
	{
		Check.assumeNotNull(productPrice, "Parameter productPrice is not null");

		final I_M_Product product = InterfaceWrapperHelper.create(productPrice.getM_Product(), I_M_Product.class);
		final I_M_PriceList_Version pricelistVersion = productPrice.getM_PriceList_Version();
		final I_M_PriceList priceList = InterfaceWrapperHelper.create(pricelistVersion.getM_PriceList(), I_M_PriceList.class);

		result.setPriceStd(productPrice.getPriceStd());
		result.setPriceList(productPrice.getPriceList());
		result.setPriceLimit(productPrice.getPriceLimit());
		result.setC_Currency_ID(priceList.getC_Currency_ID());
		result.setM_Product_Category_ID(product.getM_Product_Category_ID());
		result.setEnforcePriceLimit(priceList.isEnforcePriceLimit());
		result.setTaxIncluded(false);
		result.setM_PricingSystem_ID(priceList.getM_PricingSystem_ID());
		result.setM_PriceList_Version_ID(productPrice.getM_PriceList_Version_ID());
		result.setC_TaxCategory_ID(productPrice.getC_TaxCategory_ID());
		result.setCalculated(true);
		// 06942 : use product price uom all the time
		result.setPrice_UOM_ID(productPrice.getC_UOM_ID());

		// 08803: store the information about the price relevant attributes
		final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
		result.addPricingAttributes(attributePricingBL.extractPricingAttributes(productPrice));
	}

	/**
	 * Gets the {@link I_M_ProductPrice} to be used for setting the prices.
	 * 
	 * It checks if there is an attributed {@link I_M_ProductPrice} set explicitly and if not it tries to search for the best matching one, if any.
	 * 
	 * @param pricingCtx
	 */
	private final Optional<? extends I_M_ProductPrice> getProductPrice(final IPricingContext pricingCtx)
	{
		//
		// Use the explicit Product Price Attribute, if set and if it's valid.
		// In case we deal with explicit product price attribute, even if it's not valid we will not search forward but we will return "null".
		final IProductPriceAware explicitProductPriceAttributeAware = getProductPriceAttributeAware(pricingCtx).orElse(null);
		if (explicitProductPriceAttributeAware != null && explicitProductPriceAttributeAware.isExplicitProductPriceAttribute())
		{
			final Optional<I_M_ProductPrice> explicitProductPrice = retrieveProductPriceAttributeIfValid(pricingCtx, explicitProductPriceAttributeAware.getM_ProductPrice_ID());
			return explicitProductPrice;
		}

		//
		// Find best matching product price attribute
		final Optional<? extends I_M_ProductPrice> productPriceAttribute = findMatchingProductPrice(pricingCtx);
		return productPriceAttribute;
	}

	private final Optional<IProductPriceAware> getProductPriceAttributeAware(final IPricingContext pricingCtx)
	{
		final Object referencedObject = pricingCtx.getReferencedObject();

		// 1st try the direct way, using the referenced object itself as IProductPriceAttributeAware
		final Optional<IProductPriceAware> directProductPriceAware = ProductPriceAware.ofModel(referencedObject);
		if (directProductPriceAware.isPresent())
		{
			return directProductPriceAware;
		}

		// 2nd fall back to viewing the referenced object as ASI and then check if someone attached a IProductPriceAware to it as dynamic attribute
		final Optional<IAttributeSetInstanceAware> attributeSetInstanceAware = getAttributeSetInstanceAware(pricingCtx);
		if (attributeSetInstanceAware.isPresent())
		{
			final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);

			final Optional<IProductPriceAware> explicitProductPriceAware = attributePricingBL.getDynAttrProductPriceAttributeAware(attributeSetInstanceAware.get());
			return explicitProductPriceAware;
		}
		return Optional.empty();
	}

	/**
	 * Retrieves the {@link I_M_ProductPrice} by given ID and validates if is compatible with our pricing context.
	 *
	 * @param pricingCtx
	 * @param productPriceId
	 * @return product price attribute if exists and it's valid
	 */
	private static final Optional<I_M_ProductPrice> retrieveProductPriceAttributeIfValid(final IPricingContext pricingCtx, final int productPriceId)
	{
		if (productPriceId <= 0)
		{
			logger.debug("Returning null because M_ProductPrice_ID is not set: {}", pricingCtx);
			return Optional.empty();
		}

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(pricingCtx.getCtx(), productPriceId, I_M_ProductPrice.class, pricingCtx.getTrxName());
		if (productPrice == null || productPrice.getM_ProductPrice_ID() <= 0)
		{
			logger.debug("Returning null because M_ProductPrice_ID={} was not found", productPriceId);
			return Optional.empty();
		}

		// Make sure the product price attribute is still active.
		if (!productPrice.isActive())
		{
			logger.debug("Returning null because M_ProductPrice is not active: {}", productPrice);
			return Optional.empty();
		}

		// Make sure if the product price matches our pricing context
		if (productPrice.getM_Product_ID() != pricingCtx.getM_Product_ID())
		{
			logger.debug("Returning null because M_ProductPrice.M_Product_ID is not matching pricing context product: {}", productPrice);
			return Optional.empty();
		}

		return Optional.of(productPrice);
	}

	/**
	 * Finds the best matching {@link I_M_ProductPrice} to be used.
	 *
	 * NOTE: this method can be overridden entirely by extending classes.
	 *
	 * @param pricingCtx
	 * @return best matching {@link I_M_ProductPrice}
	 */
	protected Optional<? extends I_M_ProductPrice> findMatchingProductPrice(final IPricingContext pricingCtx)
	{
		final I_M_AttributeSetInstance attributeSetInstance = getM_AttributeSetInstance(pricingCtx);
		if (attributeSetInstance == null)
		{
			logger.debug("No M_AttributeSetInstance_ID found: {}", pricingCtx);
			return Optional.empty();
		}

		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv == null)
		{
			logger.debug("No M_PriceList_Version found: {}", pricingCtx);
			return Optional.empty();
		}

		final I_M_ProductPrice productPrice = ProductPriceQuery.newInstance(plv)
				.setM_Product_ID(pricingCtx.getM_Product_ID())
				.matching(getDefaultMatchers())
				.matchingAttributes(attributeSetInstance)
				.firstMatching();

		if (productPrice == null)
		{
			logger.debug("No product price found: {}", pricingCtx);
			return Optional.empty(); // no matching
		}

		return Optional.of(productPrice);
	}

	/**
	 * 
	 * @param pricingCtx
	 * @return
	 *         <ul>
	 *         <li>ASI
	 *         <li><code>null</code> if the given <code>pricingCtx</code> has no <code>ReferencedObject</code> or if the referenced object can't be converted in an {@link IAttributeSetInstanceAware}.
	 *         </ul>
	 */
	protected final static I_M_AttributeSetInstance getM_AttributeSetInstance(final IPricingContext pricingCtx)
	{
		final IAttributeSetInstanceAware asiAware = getAttributeSetInstanceAware(pricingCtx).orElse(null);
		if (asiAware == null)
		{
			return null;
		}

		//
		// Get M_AttributeSetInstance_ID and return it.
		// NOTE: to respect the method contract, ALWAYS return ZERO if it's not set, no matter if the getter returned -1.
		final int attributeSetInstanceId = asiAware.getM_AttributeSetInstance_ID();
		if(attributeSetInstanceId <= 0)
		{
			return null;
		}
		
		final I_M_AttributeSetInstance attributeSetInstance = InterfaceWrapperHelper.create(pricingCtx.getCtx(), attributeSetInstanceId, I_M_AttributeSetInstance.class, pricingCtx.getTrxName());
		return attributeSetInstance;
	}

	private static final Optional<IAttributeSetInstanceAware> getAttributeSetInstanceAware(final IPricingContext pricingCtx)
	{
		final Object referencedObj = pricingCtx.getReferencedObject();
		if (null == referencedObj)
		{
			return Optional.empty();
		}

		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(referencedObj);
		return Optional.ofNullable(asiAware);
	}
}
