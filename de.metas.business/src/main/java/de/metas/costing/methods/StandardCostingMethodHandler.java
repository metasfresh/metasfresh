package de.metas.costing.methods;

import org.springframework.stereotype.Component;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingMethodHandlerTemplate;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.quantity.Quantity;

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
public class StandardCostingMethodHandler extends CostingMethodHandlerTemplate
{
	public StandardCostingMethodHandler(final ICurrentCostsRepository currentCostsRepo, final ICostDetailRepository costDetailsRepo)
	{
		super(currentCostsRepo, costDetailsRepo);
	}

	@Override
	public CostingMethod getCostingMethod()
	{
		return CostingMethod.StandardCosting;
	}

	@Override
	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCost = getCurrentCost(request);
		final Quantity qty = request.getQty();
		final CostAmount amt = currentCost.getCurrentCostPrice().multiply(qty);

		final CostDetailCreateResult result = createCostDefaultImpl(request.toBuilder()
				.amt(amt)
				.build());

		currentCost.adjustCurrentQty(qty);
		currentCost.addCumulatedAmtAndQty(amt, qty);

		saveCurrentCosts(currentCost);

		return result;
	}

	@Override
	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final CurrentCost currentCost = getCurrentCost(request);
		final Quantity qty = request.getQty();
		final CostAmount amt = currentCost.getCurrentCostPrice().multiply(qty);
		final boolean isReturnTrx = qty.signum() > 0;

		final CostDetailCreateResult result = createCostDefaultImpl(request.toBuilder()
				.amt(amt)
				.build());

		currentCost.adjustCurrentQty(qty);
		if (isReturnTrx)
		{
			currentCost.addCumulatedAmtAndQty(amt, qty);
		}
		else
		{
			currentCost.adjustCurrentQty(qty);
		}

		saveCurrentCosts(currentCost);

		return result;
	}
}
