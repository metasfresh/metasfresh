package de.metas.pricing.attributebased.impl;

import ch.qos.logback.classic.Level;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.pricing.IPackingMaterialAware;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IProductPriceAware;
import de.metas.pricing.attributebased.ProductPriceAware;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.rules.price_list_version.PriceListVersionConfiguration;
import de.metas.pricing.service.ProductPriceQuery.IProductPriceQueryMatcher;
import de.metas.pricing.service.ProductPrices;
import de.metas.pricing.service.ProductScalePriceService;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class AttributePricing implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(AttributePricing.class);

	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);

	private final ProductTaxCategoryService productTaxCategoryService = SpringContextHolder.instance.getBean(ProductTaxCategoryService.class);
	private final ProductScalePriceService productScalePriceService = SpringContextHolder.instance.getBean(ProductScalePriceService.class);

	private static final CopyOnWriteArrayList<IProductPriceQueryMatcher> _defaultMatchers = new CopyOnWriteArrayList<>();

	/**
	 * Allows to add a matcher that will be applied when this rule looks for a matching product price.
	 */
	public static void registerDefaultMatcher(@NonNull final IProductPriceQueryMatcher matcher)
	{
		_defaultMatchers.addIfAbsent(matcher);
		logger.info("Registered default matcher: {}", matcher);
	}

	/**
	 * Checks if the attribute pricing rule could be applied. Mainly it checks:
	 * <ul>
	 * <li>the result shall not be already calculated
	 * <li>a product shall exist in pricing context
	 * <li>a matching attributed {@link I_M_ProductPrice} shall be found.<br>
	 * Note that here that matcher(s) that were registered via {@link #registerDefaultMatcher(IProductPriceQueryMatcher)} are also applied.
	 * </ul>
	 */
	@Override
	public final boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (result.isCalculated())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("applies - Not applying because already calculated: {}", result);
			return false;
		}

		if (pricingCtx.getProductId() == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("applies - Not applying because no product: {}", pricingCtx);
			return false;
		}

		if (!pricingCtx.getAttributeSetInstanceAware().isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("applies - Not applying because not ASI aware: {}", pricingCtx);
			return false;
		}

		//
		// Gets the "attributed" "product price, if any.
		// If nothing found, this pricing rule does not apply.
		if (!getProductPriceAttribute(pricingCtx).isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("applies - Not applying because no product price with an attribute found: {}" + pricingCtx);
			return false;
		}

		return true;
	}

	@Override
	public final void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		// Get the product price attribute to use
		// NOTE: at this point it shall not be null because we just validate it in "applies".
		final I_M_ProductPrice productPrice = getProductPriceAttribute(pricingCtx).get();

		setResultForProductPriceAttribute(pricingCtx, result, productPrice);
	}

	/**
	 * Updates the {@link IPricingResult} using the given <code>productPrice</code>.
	 */
	@OverridingMethodsMustInvokeSuper
	protected void setResultForProductPriceAttribute(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result,
			@NonNull final I_M_ProductPrice productPrice)
	{
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(productId);
		final I_M_PriceList_Version pricelistVersion = loadOutOfTrx(productPrice.getM_PriceList_Version_ID(), I_M_PriceList_Version.class);
		final I_M_PriceList priceList = pricelistVersion.getM_PriceList();

		final ProductScalePriceService.ProductPriceSettings productPriceSettings = productScalePriceService.getProductPriceSettings(productPrice, pricingCtx.getQuantity());
		if (productPriceSettings == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No ProductPriceSettings returned for qty : {} and M_ProductPrice_ID: {}", pricingCtx.getQty(), productPrice.getM_ProductPrice_ID());
			return;
		}

		result.setPriceStd(productPriceSettings.getPriceStd());
		result.setPriceList(productPriceSettings.getPriceList());
		result.setPriceLimit(productPriceSettings.getPriceLimit());
		result.setCurrencyId(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
		result.setProductCategoryId(productCategoryId);
		result.setPriceEditable(productPrice.isPriceEditable());
		result.setDiscountEditable(productPrice.isDiscountEditable());
		result.setEnforcePriceLimit(extractEnforcePriceLimit(priceList));
		result.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.ofNullableCodeOrNominal(productPrice.getInvoicableQtyBasedOn()));
		result.setTaxIncluded(false);
		result.setPricingSystemId(PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID()));
		result.setPriceListVersionId(PriceListVersionId.ofRepoId(productPrice.getM_PriceList_Version_ID()));
		result.setTaxCategoryId(productTaxCategoryService.getTaxCategoryId(productPrice));
		result.setCalculated(true);
		// 06942 : use product price uom all the time
		result.setPriceUomId(UomId.ofRepoId(productPrice.getC_UOM_ID()));

		// 08803: store the information about the price relevant attributes
		result.addPricingAttributes(attributePricingBL.extractPricingAttributes(productPrice));

		InterfaceWrapperHelper.getRepoIdOptional(productPrice, IPackingMaterialAware.COLUMNNAME_M_HU_PI_Item_Product_ID, HUPIItemProductId::ofRepoId)
				.ifPresent(result::setPackingMaterialId);
	}

	private BooleanWithReason extractEnforcePriceLimit(final I_M_PriceList priceList)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		return priceList.isEnforcePriceLimit()
				? BooleanWithReason.trueBecause(msgBL.translatable("M_PriceList_ID"))
				: BooleanWithReason.falseBecause(msgBL.translatable("M_PriceList_ID"));
	}

	/**
	 * Gets the {@link I_M_ProductPrice} to be used for setting the prices.
	 * <p>
	 * It checks if the referenced object from the given {@code pricingCtx} references a {@link I_M_ProductPrice} and explicitly demands a particular product price attribute.
	 * If that's the case, if returns that product price (if valid!).
	 * If that's not the case it tries to search for the best matching one, if any.
	 */
	private Optional<? extends I_M_ProductPrice> getProductPriceAttribute(@NonNull final IPricingContext pricingCtx)
	{
		//
		// Use the explicit Product Price Attribute, if set and if it's valid.
		// In case we deal with explicit product price attribute, even if it's not valid we will not search forward but we will return "null".
		final IProductPriceAware explicitProductPriceAttributeAware = getProductPriceAttributeAware(pricingCtx).orElse(null);
		if (explicitProductPriceAttributeAware != null && explicitProductPriceAttributeAware.isExplicitProductPriceAttribute())
		{
			return retrieveProductPriceAttributeIfValid(pricingCtx, explicitProductPriceAttributeAware.getM_ProductPrice_ID());
		}

		//
		// Find best matching product price attribute
		return findMatchingProductPriceAttribute(pricingCtx);
	}

	private Optional<IProductPriceAware> getProductPriceAttributeAware(final IPricingContext pricingCtx)
	{
		final Object referencedObject = pricingCtx.getReferencedObject();

		// 1st try the direct way, using the referenced object itself as IProductPriceAttributeAware
		final Optional<IProductPriceAware> directProductPriceAware = ProductPriceAware.ofModel(referencedObject);
		if (directProductPriceAware.isPresent())
		{
			return directProductPriceAware;
		}

		// 2nd fall back to viewing the referenced object as ASI and then check if someone attached a IProductPriceAware to it as dynamic attribute
		final IAttributeSetInstanceAware attributeSetInstanceAware = pricingCtx.getAttributeSetInstanceAware().orElse(null);
		if (attributeSetInstanceAware != null)
		{
			return attributePricingBL.getDynAttrProductPriceAttributeAware(attributeSetInstanceAware);
		}
		return Optional.empty();
	}

	/**
	 * Retrieves the {@link I_M_ProductPrice} by given ID and validates if is compatible with our pricing context.
	 *
	 * @return product price attribute if exists and it's valid
	 */
	private Optional<I_M_ProductPrice> retrieveProductPriceAttributeIfValid(final IPricingContext pricingCtx, final int productPriceId)
	{
		if (productPriceId <= 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("retrieveProductPriceAttributeIfValid - Return empty because M_ProductPrice_ID is not set: {}", pricingCtx);
			return Optional.empty();
		}

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(pricingCtx.getCtx(), productPriceId, I_M_ProductPrice.class, pricingCtx.getTrxName());
		if (productPrice == null || productPrice.getM_ProductPrice_ID() <= 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("retrieveProductPriceAttributeIfValid - Return empty because M_ProductPrice_ID={} was not found", productPriceId);
			return Optional.empty();
		}

		// Make sure the product price attribute is still active.
		if (!productPrice.isActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("retrieveProductPriceAttributeIfValid - Return empty because M_ProductPrice_ID={} is not active: {}", productPriceId);
			return Optional.empty();
		}

		// Make sure if the product price matches our pricing context
		if (productPrice.getM_Product_ID() != ProductId.toRepoId(pricingCtx.getProductId()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("retrieveProductPriceAttributeIfValid - Return empty because M_ProductPrice.M_Product_ID is not matching pricing context product: {}", productPrice);
			return Optional.empty();
		}

		Loggables.withLogger(logger, Level.DEBUG).addLog("retrieveProductPriceAttributeIfValid - M_ProductPrice_ID={} is valid", productPriceId);
		return Optional.of(productPrice);
	}

	/**
	 * Finds the best matching {@link I_M_ProductPrice} to be used.
	 * <p>
	 * NOTE: this method can be overridden entirely by extending classes.
	 *
	 * @return best matching {@link I_M_ProductPrice}
	 */
	protected Optional<? extends I_M_ProductPrice> findMatchingProductPriceAttribute(final IPricingContext pricingCtx)
	{
		final I_M_AttributeSetInstance attributeSetInstance = getM_AttributeSetInstance(pricingCtx);
		if (attributeSetInstance == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("findMatchingProductPriceAttribute - Return empty because no M_AttributeSetInstance_ID found: {}", pricingCtx);
			return Optional.empty();
		}

		final PriceListVersionId ctxPriceListVersionId = pricingCtx.getPriceListVersionId();
		if (ctxPriceListVersionId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("findMatchingProductPriceAttribute - Return empty because no M_PriceList_Version found: {}", pricingCtx);
			return Optional.empty();
		}

		final I_M_ProductPrice productPrice = ProductPrices.newQuery(ctxPriceListVersionId)
				.setProductId(pricingCtx.getProductId())
				.onlyValidPrices(true)
				.matching(_defaultMatchers)
				.strictlyMatchingAttributes(attributeSetInstance)
				.firstMatching();

		if (productPrice == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("findMatchingProductPriceAttribute - Return empty because no product price found: {}", pricingCtx);
			return Optional.empty(); // no matching
		}

		return Optional.of(productPrice);
	}

	/**
	 * Extracts an ASI from the given {@code pricingCtx}.
	 *
	 * @return <ul>
	 *         <li>ASI
	 *         <li><code>null</code> if the given <code>pricingCtx</code> has no <code>ReferencedObject</code><br/>
	 *         or if the referenced object can't be converted to an {@link IAttributeSetInstanceAware}<br/>
	 *         or if the referenced object has M_AttributeSetInstance_ID less or equal zero.
	 *         </ul>
	 */
	@Nullable
	protected static I_M_AttributeSetInstance getM_AttributeSetInstance(final IPricingContext pricingCtx)
	{
		final IAttributeSetInstanceAware asiAware = pricingCtx.getAttributeSetInstanceAware().orElse(null);
		if (asiAware == null)
		{
			return null;
		}

		//
		// Get M_AttributeSetInstance_ID and return it.
		// NOTE: to respect the method contract, ALWAYS return ZERO if it's not set, no matter if the getter returned -1.
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(asiAware.getM_AttributeSetInstance_ID());
		if (attributeSetInstanceId.isNone())
		{
			return null;
		}

		return Services.get(IAttributeDAO.class).getAttributeSetInstanceById(attributeSetInstanceId);
	}
}
