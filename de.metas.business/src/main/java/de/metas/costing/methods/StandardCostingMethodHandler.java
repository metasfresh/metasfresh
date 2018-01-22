package de.metas.costing.methods;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MCost;

import de.metas.costing.CostDetailEvent;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethodHandlerTemplate;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class StandardCostingMethodHandler extends CostingMethodHandlerTemplate
{
	@Override
	protected void processPurchaseInvoice(final CostDetailEvent event, final CurrentCost cost)
	{
		final CostSegment costSegment = event.getCostSegment();
		final String costingMethod = event.getCostingMethod();
		final BigDecimal amt = event.getAmt();
		final BigDecimal qty = event.getQty();
		final BigDecimal price = event.getPrice();

		// Update cost record only if newly created.
		// Elsewhere we risk to set the CurrentCostPrice to an undesired price.
		if (InterfaceWrapperHelper.isNew(cost)
				&& cost.getCurrentCostPrice().signum() == 0
				&& cost.getCurrentCostPriceLL().signum() == 0)
		{
			cost.setCurrentCostPrice(price);
			// seed initial price
			if (cost.getCurrentCostPrice().signum() == 0)
			{
				final int orderLineId = 0; // N/A
				final BigDecimal currentCostPrice = MCost.getSeedCosts(costSegment, costingMethod, orderLineId);
				cost.setCurrentCostPrice(currentCostPrice);
			}
		}
		
		cost.add(amt, qty);
	}

	@Override
	protected void processOutboundTransactionDefaultImpl(final CostDetailEvent event, final CurrentCost cost)
	{
		final BigDecimal amt = event.getAmt();
		final BigDecimal qty = event.getQty();
		final boolean addition = qty.signum() > 0;
		final BigDecimal price = event.getPrice();

		if (addition)
		{
			cost.add(amt, qty);
			
			// Initial
			if (cost.getCurrentCostPrice().signum() == 0
					&& cost.getCurrentCostPriceLL().signum() == 0
					&& InterfaceWrapperHelper.isNew(cost))
			{
				cost.setCurrentCostPrice(price);
			}
		}
		else
		{
			cost.adjustCurrentQty(qty);
		}
	}
}
