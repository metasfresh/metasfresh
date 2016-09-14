package de.metas.pricing.attributebased.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.X_M_DiscountSchemaLine;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.logging.LogManager;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IAttributePricingDAO;
import de.metas.pricing.attributebased.IProductPriceAttributeAware;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute;
import de.metas.pricing.attributebased.ProductPriceAttributeAware;

public class AttributePricing extends PricingRuleAdapter
{

	private final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * Checks if the attribute pricing rule could be applied. Mainly it checks:
	 * <ul>
	 * <li>the result shall not be already calculated
	 * <li>a product shall exist in pricing context
	 * <li>a matching {@link I_M_ProductPrice_Attribute} shall be found
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
		final Optional<? extends I_M_ProductPrice_Attribute> productPriceAttribute = getProductPriceAttribute(pricingCtx);
		if (!productPriceAttribute.isPresent())
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
		final I_M_ProductPrice_Attribute productPriceAttribute = getProductPriceAttribute(pricingCtx).get();

		setResultForProductPriceAttribute(pricingCtx, result, productPriceAttribute);
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
			final I_M_ProductPrice_Attribute productPriceAttribute)
	{
		Check.assumeNotNull(productPriceAttribute, "productPriceAttribute not null");

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(productPriceAttribute.getM_ProductPrice(), I_M_ProductPrice.class);
		final I_M_Product product = InterfaceWrapperHelper.create(productPrice.getM_Product(), I_M_Product.class);
		final I_M_PriceList_Version pricelistVersion = productPrice.getM_PriceList_Version();
		final I_M_PriceList priceList = InterfaceWrapperHelper.create(pricelistVersion.getM_PriceList(), I_M_PriceList.class);

		result.setPriceStd(productPriceAttribute.getPriceStd());
		result.setPriceList(productPriceAttribute.getPriceList());
		result.setPriceLimit(productPriceAttribute.getPriceLimit());
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
		attributePricingBL.addToPricingAttributes(productPriceAttribute, result.getPricingAttributes()); // task 08803
	}

	/**
	 * Gets the {@link I_M_ProductPrice_Attribute} to be used for setting the prices.
	 * 
	 * It checks if there is an {@link I_M_ProductPrice_Attribute} set explicitly and if not it tries to search for the best matching one, if any.
	 * 
	 * @param pricingCtx
	 * @return
	 */
	private final Optional<? extends I_M_ProductPrice_Attribute> getProductPriceAttribute(final IPricingContext pricingCtx)
	{
		//
		// Use the explicit Product Price Attribute, if set and if it's valid.
		// In case we deal with explicit product price attribute, even if it's not valid we will not search forward but we will return "null".
		final IProductPriceAttributeAware explicitProductPriceAttributeAware = getProductPriceAttributeAware(pricingCtx).orNull();
		if (explicitProductPriceAttributeAware != null && explicitProductPriceAttributeAware.isExplicitProductPriceAttribute())
		{
			final Optional<I_M_ProductPrice_Attribute> explicitProductPrice = retrieveProductPriceAttributeIfValid(pricingCtx, explicitProductPriceAttributeAware.getM_ProductPrice_Attribute_ID());
			return explicitProductPrice;
		}

		//
		// Find best matching product price attribute
		final Optional<? extends I_M_ProductPrice_Attribute> productPriceAttribute = findMatchingProductPriceAttribute(pricingCtx);
		return productPriceAttribute;
	}

	private final Optional<IProductPriceAttributeAware> getProductPriceAttributeAware(final IPricingContext pricingCtx)
	{
		final Object referencedObject = pricingCtx.getReferencedObject();

		// 1st try the direct way, using the referenced object itself as IProductPriceAttributeAware
		final Optional<IProductPriceAttributeAware> directPpaAware = ProductPriceAttributeAware.ofModel(referencedObject);
		if (directPpaAware.isPresent())
		{
			return directPpaAware;
		}

		// 2nd fall back to viewing the referenced object as ASI and then check if someone attached a IProductPriceAttributeAware to it as dynamic attribute
		final Optional<IAttributeSetInstanceAware> attributeSetInstanceAware = getAttributeSetInstanceAware(pricingCtx);
		if (attributeSetInstanceAware.isPresent())
		{
			final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);

			final Optional<IProductPriceAttributeAware> orderLineHasExplicitASI = attributePricingBL.getDynAttrProductPriceAttributeAware(attributeSetInstanceAware.get());
			return orderLineHasExplicitASI;
		}
		return Optional.absent();
	}

	/**
	 * Retrieves the {@link I_M_ProductPrice_Attribute} by given ID and validates if is compatible with our pricing context.
	 *
	 * @param pricingCtx
	 * @param productPriceAttributeId
	 * @return product price attribute if exists and it's valid
	 */
	private final Optional<I_M_ProductPrice_Attribute> retrieveProductPriceAttributeIfValid(
			final IPricingContext pricingCtx,
			final int productPriceAttributeId)
	{
		if (productPriceAttributeId <= 0)
		{
			logger.debug("Returning null because M_ProductPrice_Attribute_ID is not set: {}", pricingCtx);
			return Optional.absent();
		}

		final I_M_ProductPrice_Attribute productPriceAttribute = InterfaceWrapperHelper.create(pricingCtx.getCtx(), productPriceAttributeId, I_M_ProductPrice_Attribute.class, pricingCtx.getTrxName());
		if (productPriceAttribute == null || productPriceAttribute.getM_ProductPrice_Attribute_ID() <= 0)
		{
			logger.debug("Returning null because M_ProductPrice_Attribute_ID={} was not found", productPriceAttributeId);
			return Optional.absent();
		}

		// Make sure the product price attribute is still active.
		if (!productPriceAttribute.isActive())
		{
			logger.debug("Returning null because M_ProductPrice_Attribute is not active: {}", productPriceAttribute);
			return Optional.absent();
		}

		// Make sure if the product price matches our pricing context
		final org.compiere.model.I_M_ProductPrice productPrice = productPriceAttribute.getM_ProductPrice();
		if (!productPrice.isActive())
		{
			logger.debug("Returning null because M_ProductPrice is not active: {}", productPrice);
			return Optional.absent();
		}
		if (productPrice.getM_Product_ID() != pricingCtx.getM_Product_ID())
		{
			logger.debug("Returning null because M_ProductPrice.M_Product_ID is not matching pricing context product: {}", productPrice);
			return Optional.absent();
		}

		return Optional.of(productPriceAttribute);
	}

	/**
	 * Finds the best matching {@link I_M_ProductPrice_Attribute} to be used.
	 *
	 * NOTE: this method can be overridden entirely by extending classes.
	 *
	 * @param pricingCtx
	 * @return best matching {@link I_M_ProductPrice_Attribute}
	 */
	protected Optional<? extends I_M_ProductPrice_Attribute> findMatchingProductPriceAttribute(final IPricingContext pricingCtx)
	{
		final Properties ctx = pricingCtx.getCtx();
		final String trxName = pricingCtx.getTrxName();

		final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);

		final int attributeSetInstanceId = getM_AttributeSetInstance_ID(pricingCtx);
		if (attributeSetInstanceId <= 0)
		{
			logger.debug("No M_AttributeSetInstance_ID found: {}", pricingCtx);
			return Optional.absent();
		}

		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if (plv == null)
		{
			logger.debug("No M_PriceList_Version found: {}", pricingCtx);
			return Optional.absent();
		}

		final I_M_ProductPrice_Attribute productPriceAttribute =
				attributePricingBL.getPriceAttribute(ctx, pricingCtx.getM_Product_ID(),
						attributeSetInstanceId,
						plv,
						trxName);

		if (null == productPriceAttribute || productPriceAttribute.getM_ProductPrice_Attribute_ID() <= 0)
		{
			logger.debug("No product attribute pricing found: {}", pricingCtx);
			return Optional.absent(); // no matching
		}

		return Optional.of(productPriceAttribute);
	}

	/**
	 * 
	 * @param pricingCtx
	 * @return <ul>
	 *         <li>M_AttributeSetInstance_ID (which might be 0 if not set)
	 *         <li>-1 if the given <code>pricingCtx</code> has no <code>ReferencedObject</code> or if the referenced object can't be converted in an {@link IAttributeSetInstanceAware}.
	 *         </ul>
	 */
	protected final static int getM_AttributeSetInstance_ID(final IPricingContext pricingCtx)
	{
		final IAttributeSetInstanceAware asiAware = getAttributeSetInstanceAware(pricingCtx).orNull();
		if (asiAware == null)
		{
			return -1;
		}

		//
		// Get M_AttributeSetInstance_ID and return it.
		// NOTE: to respect the method contract, ALWAYS return ZERO if it's not set, no matter if the getter returned -1.
		final int attributeSetInstanceId = asiAware.getM_AttributeSetInstance_ID();
		return attributeSetInstanceId < 0 ? 0 : attributeSetInstanceId;
	}

	protected final static Optional<IAttributeSetInstanceAware> getAttributeSetInstanceAware(final IPricingContext pricingCtx)
	{
		final Object referencedObj = pricingCtx.getReferencedObject();
		if (null == referencedObj)
		{
			return Optional.absent();
		}

		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(referencedObj);
		return Optional.fromNullable(asiAware);
	}

	@Override
	public final void updateFromDiscounLine(final I_M_PriceList_Version plv, final Iterator<I_M_ProductPrice> productPrices, final I_M_DiscountSchemaLine dsl)
	{
		while (productPrices.hasNext())
		{
			final I_M_ProductPrice productPrice = productPrices.next();
			updateFromDiscounLine(plv, productPrice, dsl);
		}
	}

	private void updateFromDiscounLine(final I_M_PriceList_Version plv,
			final I_M_ProductPrice productPrice,
			final I_M_DiscountSchemaLine dsl)
	{
		if (productPrice == null || !InterfaceWrapperHelper.create(productPrice, de.metas.pricing.attributebased.I_M_ProductPrice.class).isAttributeDependant())
		{
			return; // task 08804: nothing to do
		}

		final List<I_M_ProductPrice_Attribute> productPriceAttributes = Services.get(IAttributePricingDAO.class).retrieveAllPriceAttributes(productPrice);

		for (final I_M_ProductPrice_Attribute productPriceAttribute : productPriceAttributes)
		{
			final String listBase = dsl.getList_Base();
			BigDecimal basePriceList = BigDecimal.ZERO;

			//
			// Modify price list
			if (X_M_DiscountSchemaLine.LIST_BASE_Standardpreis.equals(listBase))
			{
				basePriceList = productPriceAttribute.getPriceStd();
			}
			else if (X_M_DiscountSchemaLine.LIST_BASE_Mindestpreis.equals(listBase))
			{
				basePriceList = productPriceAttribute.getPriceLimit();
			}
			else
			{
				basePriceList = productPriceAttribute.getPriceList();
			}

			// task 08136/08183, additional requirement: if a product price is zero in the base/source PLV, then it shall remain zero, even if an *_AddAmt was specified
			if (basePriceList.signum() != 0)
			{
				basePriceList = basePriceList.add(dsl.getList_AddAmt());
			}
			basePriceList = removePercent(basePriceList, dsl.getList_Discount());

			//
			// Modify price std
			final String stdBase = dsl.getStd_Base();
			BigDecimal basePriceStd = BigDecimal.ZERO;
			if (X_M_DiscountSchemaLine.STD_BASE_Listenpreis.equals(stdBase))
			{
				basePriceStd = productPriceAttribute.getPriceList();
			}
			else if (X_M_DiscountSchemaLine.STD_BASE_Mindestpreis.equals(stdBase))
			{
				basePriceStd = productPriceAttribute.getPriceLimit();
			}
			else
			{
				basePriceStd = productPriceAttribute.getPriceStd();
			}

			// task 08136/08183, additional requirement: if a product price is zero in the base/source PLV, then it shall remain zero, even if an *_AddAmt was specified
			if (basePriceStd.signum() != 0)
			{
				basePriceStd = basePriceStd.add(dsl.getStd_AddAmt());
			}
			basePriceStd = removePercent(basePriceStd, dsl.getStd_Discount());

			//
			// Modify price limit
			final String limitBase = dsl.getLimit_Base();
			BigDecimal basePriceLimit = BigDecimal.ZERO;
			if (X_M_DiscountSchemaLine.LIMIT_BASE_Listenpreis.equals(limitBase))
			{
				basePriceLimit = productPriceAttribute.getPriceList();
			}
			else if (X_M_DiscountSchemaLine.LIMIT_BASE_Standardpreis.equals(limitBase))
			{
				basePriceLimit = productPriceAttribute.getPriceStd();
			}
			else
			{
				basePriceLimit = productPriceAttribute.getPriceLimit();
			}

			// task 08136/08183, additional requirement: if a product price is zero in the base/source PLV, then it shall remain zero, even if an *_AddAmt was specified
			if (basePriceLimit.signum() != 0)
			{
				basePriceLimit = basePriceLimit.add(dsl.getLimit_AddAmt());
			}
			basePriceLimit = removePercent(basePriceLimit, dsl.getLimit_Discount());

			final int precision = plv.getM_PriceList().getPricePrecision();
			basePriceLimit = calculatePrice(basePriceLimit, precision, dsl.getLimit_Rounding());
			basePriceStd = calculatePrice(basePriceStd, precision, dsl.getStd_Rounding());
			basePriceList = calculatePrice(basePriceList, precision, dsl.getList_Rounding());
			//
			// set all prices and save
			productPriceAttribute.setPriceList(basePriceList);
			productPriceAttribute.setPriceStd(basePriceStd);
			productPriceAttribute.setPriceLimit(basePriceLimit);

			InterfaceWrapperHelper.save(productPriceAttribute);
		}

	}

	/**
	 * calculate price based on the rounding
	 * 
	 * @param basePriceLimit
	 * @param precision
	 * @param rounding
	 * @return
	 */
	public static BigDecimal calculatePrice(final BigDecimal basePriceLimit, final int precision, final String rounding)
	{
		// "PriceList = CASE '" + dl.getString("List_Rounding") + "' "
		// + " WHEN 'N' THEN PriceList "
		// + " WHEN '0' THEN ROUND(PriceList, 0)" // Even .00
		// + " WHEN 'D' THEN ROUND(PriceList, 1)" // Dime .10
		// + " WHEN 'T' THEN ROUND(PriceList, -1) " // Ten 10.00
		// + " WHEN '5' THEN ROUND(PriceList*20,0)/20" // Nickle .05
		// + " WHEN 'Q' THEN ROUND(PriceList*4,0)/4" // Quarter .25
		// + " WHEN '9' THEN CASE" // Whole 9 or 5
		// + "    WHEN MOD(ROUND(PriceList),10)<=5 THEN ROUND(PriceList)+(5-MOD(ROUND(PriceList),10))"
		// + "    WHEN MOD(ROUND(PriceList),10)>5 THEN ROUND(PriceList)+(9-MOD(ROUND(PriceList),10)) END"
		// + " ELSE ROUND(PriceList, " + v.getInt("StdPrecision") + ")"
		// + " END,"

		if (rounding == null)
		{
			// saw in tests that this is the way we handle nulls
			return basePriceLimit.setScale(0, RoundingMode.UP);

		}
		final BigDecimal price;
		switch (rounding)
		{

		// " WHEN 'N' THEN PriceList "
			case X_M_DiscountSchemaLine.LIST_ROUNDING_NoRounding:
				price = basePriceLimit;
				break;

			// " WHEN '0' THEN ROUND(PriceList, 0)" // Even .00

			case X_M_DiscountSchemaLine.LIST_ROUNDING_GanzeZahl00:
				price = basePriceLimit.setScale(0, RoundingMode.HALF_EVEN);
				break;

			// + " WHEN '5' THEN ROUND(PriceList*20,0)/20" // Nickle .05 :

			case X_M_DiscountSchemaLine.LIST_ROUNDING_Nickel051015:
				price = round(basePriceLimit, new BigDecimal("0.05"), RoundingMode.HALF_DOWN);
				break;

			// + " WHEN 'D' THEN ROUND(PriceList, 1)" // Dime .10
			case X_M_DiscountSchemaLine.LIST_ROUNDING_Dime102030:
				price = round(basePriceLimit, new BigDecimal("0.10"), RoundingMode.HALF_UP);
				break;

			// + " WHEN 'T' THEN ROUND(PriceList, -1) " // Ten 10.00

			case X_M_DiscountSchemaLine.LIST_ROUNDING_Ten10002000:
				price = round(basePriceLimit, BigDecimal.TEN, RoundingMode.HALF_UP);
				break;
			// + " WHEN 'Q' THEN ROUND(PriceList*4,0)/4" // Quarter .25
			case X_M_DiscountSchemaLine.LIST_ROUNDING_Quarter255075:
				price = round(basePriceLimit, new BigDecimal("0.25"), RoundingMode.HALF_UP);
				break;
			// + " WHEN '9' THEN CASE" // Whole 9 or 5
			// + "    WHEN MOD(ROUND(PriceList),10)<=5 THEN ROUND(PriceList)+(5-MOD(ROUND(PriceList),10))"
			// + "    WHEN MOD(ROUND(PriceList),10)>5 THEN ROUND(PriceList)+(9-MOD(ROUND(PriceList),10)) END"
			case X_M_DiscountSchemaLine.LIST_ROUNDING_EndingIn95:
			{

				final BigDecimal roundedBasePrice = basePriceLimit.setScale(0, RoundingMode.HALF_EVEN);
				if (roundedBasePrice.remainder(BigDecimal.TEN).compareTo(new BigDecimal(5)) <= 0)
				{
					price = roundedBasePrice.add(new BigDecimal(5).subtract(roundedBasePrice).remainder(BigDecimal.TEN));
				}
				else
				{
					price = roundedBasePrice.add(new BigDecimal(9).subtract(roundedBasePrice).remainder(BigDecimal.TEN));
				}

				break;
			}
			default:
				price = basePriceLimit.setScale(precision, RoundingMode.UP);
		}

		return price;
	}

	private BigDecimal removePercent(final BigDecimal base, final BigDecimal discount)
	{
		final BigDecimal oneHundred = new BigDecimal("100");
		return base.multiply(oneHundred.subtract(discount).divide(oneHundred));
	}

	/**
	 * @See http://stackoverflow.com/questions/2106615/round-bigdecimal-to-nearest-5-cents
	 * 
	 * @param value
	 * @param increment
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal round(final BigDecimal value, final BigDecimal increment, final RoundingMode roundingMode)
	{
		if (increment.signum() == 0)
		{
			// 0 increment prevents division by 0. Return the value as is
			return value;
		}
		else
		{
			BigDecimal divided = value.divide(increment, 0, roundingMode);
			BigDecimal result = divided.multiply(increment);
			return result;
		}
	}

}
