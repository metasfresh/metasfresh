package de.metas.costing.methods;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostPrice;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

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

@Component
public class AverageInvoiceCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public AverageInvoiceCostingMethodHandler(@NonNull final CostingMethodHandlerUtils utils)
	{
		super(utils);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.AverageInvoice;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice_MaterialCosts(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		
		final CostDetailCreateResult result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

		currentCosts.addWeightedAverage(request.getAmt(), request.getQty(), utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final boolean isReturnTrx = request.getQty().signum() > 0;

		final CurrentCost currentCosts = utils.getCurrentCost(request);
		final CostDetailPreviousAmounts previousCosts = CostDetailPreviousAmounts.of(currentCosts);
		
		final CostDetailCreateResult result;
		if (isReturnTrx)
		{
			result = utils.createCostDetailRecordWithChangedCosts(request, previousCosts);

			currentCosts.addWeightedAverage(request.getAmt(), request.getQty(), utils.getQuantityUOMConverter());
		}
		else
		{
			final CostPrice price = currentCosts.getCostPrice();
			final Quantity qty = utils.convertToUOM(request.getQty(), price.getUomId(), request.getProductId());
			final CostAmount amt = price.multiply(qty).roundToPrecisionIfNeeded(currentCosts.getPrecision());

			final CostDetailCreateRequest requestEffective = request.withAmount(amt);
			result = utils.createCostDetailRecordWithChangedCosts(requestEffective, previousCosts);

			currentCosts.addToCurrentQtyAndCumulate(qty, amt);
		}

		utils.saveCurrentCost(currentCosts);

		return result;
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		final CurrentCost currentCosts = utils.getCurrentCost(request.getCostSegmentAndElement());

		currentCosts.addToCurrentQtyAndCumulate(request.getQty().negate(), request.getAmt().negate(), utils.getQuantityUOMConverter());

		utils.saveCurrentCost(currentCosts);
	}

	@Override
	public MoveCostsResult createMovementCosts(@NonNull final MoveCostsRequest request)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
