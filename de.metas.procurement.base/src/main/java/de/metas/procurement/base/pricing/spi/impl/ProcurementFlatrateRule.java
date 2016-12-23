package de.metas.procurement.base.pricing.spi.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.rules.PricingRuleAdapter;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.IPMMPricingBL;
import de.metas.procurement.base.order.impl.PMMPricingAware_C_OrderLine;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProcurementFlatrateRule extends PricingRuleAdapter
{

	private final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * Returns {@code true} if the referenced oblect of the given {@code pricingCtx} is a purchase order line whose {@link PMMPricingAware_C_OrderLine} has a flatrate term.
	 */
	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{

		// make sure an object is referenced
		final Object referencedObject = pricingCtx.getReferencedObject();
		if (referencedObject == null)
		{
			logger.debug("Not applying because pricingCtx has no referencedObject");
			return false;
		}

		// This rule only applies to C_OrderLine entries
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(referencedObject.getClass());
		if (!org.compiere.model.I_C_OrderLine.Table_Name.equals(tableName))
		{
			logger.debug("Not applying because referencedObject='" + referencedObject + "' has tableName='" + tableName + "'");
			return false;
		}

		// orderline instance
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(referencedObject, I_C_OrderLine.class);

		// order header instance
		final I_C_Order order = ol.getC_Order();

		if (order.isSOTrx())
		{
			// this rule only applies to purchase orders
			logger.debug("Not applying because the referencedObject's header is a sales order");
			return false;
		}

		final IPMMPricingAware pricingAwareOrderLine = PMMPricingAware_C_OrderLine.of(ol);

		// the partner from the order must have a current contract for the product
		if (pricingAwareOrderLine.getC_Flatrate_Term() == null)
		{
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final Object referencedObject = pricingCtx.getReferencedObject();

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(referencedObject, I_C_OrderLine.class);

		final IPMMPricingAware pricingAware = PMMPricingAware_C_OrderLine.of(ol);

		// update the price from procurement contract
		Services.get(IPMMPricingBL.class).updatePriceFromContract(pricingAware);

		// set details in result
		result.setPriceStd(ol.getPriceStd());
		result.setC_Currency_ID(ol.getC_Currency_ID());

		// Mark the result as calculated.
		// This price will be the final one if there is no superior rule to be applied.
		result.setCalculated(true);

	}

}
