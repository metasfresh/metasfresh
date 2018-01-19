package de.metas.costing.methods;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.MCostQueue;

import de.metas.costing.CostDetailEvent;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethodHandlerTemplate;
import lombok.Data;
import lombok.NonNull;

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

public abstract class FifoOrLifoCostingMethodHandler extends CostingMethodHandlerTemplate
{
	@Override
	protected void processPurchaseInvoice(final CostDetailEvent event, final CostResult cost)
	{
		final CostSegment costSegment = event.getCostSegment();
		final int costElementId = event.getCostElementId();
		final String costingMethod = event.getCostingMethod();
		final BigDecimal amt = event.getAmt();
		final BigDecimal qty = event.getQty();
		final int precision = event.getPrecision();

		final MCostQueue cq = MCostQueue.get(costSegment, costElementId);
		cq.setCosts(amt, qty, precision);
		cq.save();

		final List<MCostQueue> cQueue = MCostQueue.getQueue(costSegment, costElementId, costingMethod);
		if (!cQueue.isEmpty())
		{
			cost.setCurrentCostPrice(cQueue.get(0).getCurrentCostPrice());
		}
		cost.add(amt, qty);
	}

	@Override
	protected void processOutboundTransactionDefaultImpl(final CostDetailEvent event, final CostResult cost)
	{
		final CostSegment costSegment = event.getCostSegment();
		final int costElementId = event.getCostElementId();
		final String costingMethod = event.getCostingMethod();
		final BigDecimal amt = event.getAmt();
		final BigDecimal qty = event.getQty();
		final boolean addition = qty.signum() > 0;
		final int precision = event.getPrecision();

		if (addition)
		{
			// Real ASI - costing level Org
			final MCostQueue cq = MCostQueue.get(costSegment, costElementId);
			cq.setCosts(amt, qty, precision);
			cq.save();
		}
		else
		{
			// Adjust Queue - costing level Org/ASI
			MCostQueue.adjustQty(costSegment, costElementId, costingMethod, qty.negate());
		}
		// Get Costs - costing level Org/ASI
		final List<MCostQueue> cQueue = MCostQueue.getQueue(costSegment, costElementId, costingMethod);
		if (!cQueue.isEmpty())
		{
			cost.setCurrentCostPrice(cQueue.get(0).getCurrentCostPrice());
		}
		cost.setCurrentQty(cost.getCurrentQty().add(qty));
	}
	
	@Data
	protected static class QtyCost
	{
		@NonNull
		private BigDecimal qty;
		@NonNull
		private BigDecimal cost;
		
		public QtyCost(final BigDecimal qty, final BigDecimal cost)
		{
			this.qty = qty;
			this.cost = cost;
		}
		
		public void addQty(final BigDecimal qtyToAdd)
		{
			this.qty = this.qty.add(qtyToAdd);
		}
	}

}
