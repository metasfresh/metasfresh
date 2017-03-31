package de.metas.flatrate.pricing.spi.impl;

/*
 * #%L
 * de.metas.contracts
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
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;
import org.slf4j.Logger;

import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.logging.LogManager;
import de.metas.product.IProductPA;

/**
 * Pricing rule applies for order lines that have a subscription id set. If the rule is called and
 * {@link IPricingContext#getReferencedObject()} returns a subscription order line, then the rule creates a pricing
 * context of it's own and calls the pricing engine with that pricing context. The rule's own pricing context contains
 * the subscription's price list.
 * 
 * @author ts
 * 
 */
public class SubscriptionPricingRule extends PricingRuleAdapter
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (result.isCalculated())
		{
			logger.debug("Not applying because already calculated");
			return false;
		}

		final Object referencedObject = pricingCtx.getReferencedObject();
		if (referencedObject == null)
		{
			logger.debug("Not applying because pricingCtx has no referencedObject");
			return false;
		}

		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(referencedObject.getClass());
		if (!I_C_OrderLine.Table_Name.equals(tableName))
		{
			logger.debug("Not applying because referencedObject='" + referencedObject + "' has tableName='" + tableName + "'");
			return false;
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(referencedObject, I_C_OrderLine.class);
		if (ol.getC_Flatrate_Conditions_ID() <= 0)
		{
			logger.debug("Not applying because ol='" + ol + "' has C_Subscription_ID<=0");
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final Object referencedObject = pricingCtx.getReferencedObject();
		Check.assume(referencedObject != null && referencedObject instanceof org.compiere.model.I_C_OrderLine, pricingCtx + " has an I_C_OrderLine as ReferencedObject");

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(referencedObject, I_C_OrderLine.class);
		Check.assume(ol.getC_Flatrate_Conditions_ID() > 0, ol + " has C_Flatrate_Conditions_ID>0");

		final I_C_Flatrate_Conditions sub = ol.getC_Flatrate_Conditions();

		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);

		final I_M_PriceList subscriptionPL = Services.get(IProductPA.class)
				.retrievePriceListByPricingSyst(ctx, sub.getM_PricingSystem_ID(), ol.getC_BPartner_Location_ID(), true, trxName);

		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		//
		// create a new pricing context.
		// copy most values from 'pricingCtx'...
		final IEditablePricingContext subscriptionPricingCtx = pricingBL.createPricingContext();
		subscriptionPricingCtx.setAD_Table_ID(pricingCtx.getAD_Table_ID());
		subscriptionPricingCtx.setC_BPartner_ID(pricingCtx.getC_BPartner_ID());
		subscriptionPricingCtx.setC_Currency_ID(pricingCtx.getC_Currency_ID());
		subscriptionPricingCtx.setC_UOM_ID(pricingCtx.getC_UOM_ID());
		subscriptionPricingCtx.setM_Product_ID(pricingCtx.getM_Product_ID());
		subscriptionPricingCtx.setPriceDate(pricingCtx.getPriceDate());
		subscriptionPricingCtx.setQty(pricingCtx.getQty());
		subscriptionPricingCtx.setSOTrx(pricingCtx.isSOTrx());
		subscriptionPricingCtx.setAD_Table_ID(pricingCtx.getAD_Table_ID());
		subscriptionPricingCtx.setRecord_ID(pricingCtx.getRecord_ID());

		// don't set a ReferencedObject, so that this rule's 'applies()' method will return false
		subscriptionPricingCtx.setReferencedObject(null);

		// set the price list from subscription's M_Pricing_Systen
		subscriptionPricingCtx.setM_PriceList_Version_ID(0);
		subscriptionPricingCtx.setM_PriceList_ID(subscriptionPL.getM_PriceList_ID());

		// call the pricing engine with our own parameters
		final IPricingResult subscriptionPricingResult = pricingBL.calculatePrice(subscriptionPricingCtx);

		if (!subscriptionPricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(subscriptionPricingCtx, ol.getLine());
		}

		// copy the results of our internal call into 'result'
		result.setC_Currency_ID(subscriptionPricingResult.getC_Currency_ID());
		result.setPrice_UOM_ID(subscriptionPricingResult.getPrice_UOM_ID());
		result.setCalculated(subscriptionPricingResult.isCalculated());
		result.setDisallowDiscount(subscriptionPricingResult.isDisallowDiscount());
		if(!pricingCtx.isDisallowDiscount())
		{
			result.setDiscount(subscriptionPricingResult.getDiscount());
		}
		result.setUsesDiscountSchema(subscriptionPricingResult.isUsesDiscountSchema());
		//08634: also set the discount schema
		result.setM_DiscountSchema_ID(subscriptionPricingResult.getM_DiscountSchema_ID());
		result.setEnforcePriceLimit(subscriptionPricingResult.isEnforcePriceLimit());
		result.setM_PricingSystem_ID(subscriptionPricingResult.getM_PricingSystem_ID());
		result.setM_PriceList_Version_ID(subscriptionPricingResult.getM_PriceList_Version_ID());
		result.setM_Product_Category_ID(subscriptionPricingResult.getM_Product_Category_ID());
		result.setPrecision(subscriptionPricingResult.getPrecision());
		result.setPriceLimit(subscriptionPricingResult.getPriceLimit());
		result.setPriceList(subscriptionPricingResult.getPriceList());
		result.setPriceStd(subscriptionPricingResult.getPriceStd());
		result.setTaxIncluded(subscriptionPricingResult.isTaxIncluded());
	}
}
