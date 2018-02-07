package de.metas.costing.methods;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostQueue;
import org.compiere.util.Env;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingMethodHandlerTemplate;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.quantity.Quantity;
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
	public FifoOrLifoCostingMethodHandler(final ICurrentCostsRepository currentCostsRepo, final ICostDetailRepository costDetailsRepo)
	{
		super(currentCostsRepo, costDetailsRepo);
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final CostDetailCreateResult result = createCostDefaultImpl(request);

		final CurrentCost currentCost = getCurrentCost(request);
		final CostSegment costSegment = currentCost.getCostSegment();
		final CostElement costElement = currentCost.getCostElement();
		final int costElementId = costElement.getId();
		final CostingMethod costingMethod = costElement.getCostingMethod();
		final CostAmount amt = request.getAmt();
		final Quantity qty = request.getQty();
		final int precision = currentCost.getPrecision();

		final MCostQueue cq = MCostQueue.get(costSegment, costElementId);
		cq.setCosts(amt.getValue(), qty, precision);
		cq.save();

		final List<MCostQueue> cQueue = MCostQueue.getQueue(costSegment, costElementId, costingMethod);
		if (!cQueue.isEmpty())
		{
			final MAcctSchema as = MAcctSchema.get(Env.getCtx(), costSegment.getAcctSchemaId());
			final int currencyId = as.getC_Currency_ID();
			currentCost.setCurrentCostPrice(CostAmount.of(cQueue.get(0).getCurrentCostPrice(), currencyId));
		}
		currentCost.add(amt, qty);

		saveCurrentCosts(currentCost);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final CurrentCost cost = getCurrentCost(request);

		final CostSegment costSegment = cost.getCostSegment();
		final CostElement costElement = cost.getCostElement();
		final int costElementId = costElement.getId();
		final CostingMethod costingMethod = costElement.getCostingMethod();
		final Quantity qty = request.getQty();
		final boolean isReturnTrx = qty.signum() > 0;
		final int precision = cost.getPrecision();

		final CostAmount amt;
		if (isReturnTrx)
		{
			amt = request.getAmt();

			// Real ASI - costing level Org
			final MCostQueue cq = MCostQueue.get(costSegment, costElementId);
			cq.setCosts(amt.getValue(), qty, precision);
			cq.save();
		}
		else
		{
			// Adjust Queue - costing level Org/ASI
			final BigDecimal price = MCostQueue.adjustQty(costSegment, costElementId, costingMethod, qty.negate());
			amt = CostAmount.of(price, cost.getCurrencyId()).multiply(qty);
		}

		final CostDetailCreateResult result = createCostDefaultImpl(request.toBuilder().amt(amt).build());

		// Get Costs - costing level Org/ASI
		final List<MCostQueue> cQueue = MCostQueue.getQueue(costSegment, costElementId, costingMethod);
		if (!cQueue.isEmpty())
		{
			final MAcctSchema as = MAcctSchema.get(Env.getCtx(), costSegment.getAcctSchemaId());
			final int currencyId = as.getC_Currency_ID();
			cost.setCurrentCostPrice(CostAmount.of(cQueue.get(0).getCurrentCostPrice(), currencyId));
		}
		cost.adjustCurrentQty(qty);

		saveCurrentCosts(cost);

		return result;
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
			qty = qty.add(qtyToAdd);
		}
	}

}
