package de.metas.procurement.base.order.impl;

import java.math.BigDecimal;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.IPMMPurchaseCandidateBL;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PMMPurchaseCandidateBL implements IPMMPurchaseCandidateBL
{
	@Override
	public void setQtyPromisedAndUpdate(final I_PMM_PurchaseCandidate candidate, final BigDecimal qtyPromised, final BigDecimal qtyPromisedTU)
	{
		candidate.setQtyPromised(qtyPromised);
		candidate.setQtyPromised_TU(qtyPromisedTU);
		updateQtyToOrder(candidate);
	}

	@Override
	public void addQtyOrderedAndUpdate(final I_PMM_PurchaseCandidate candidate, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU)
	{
		candidate.setQtyOrdered(candidate.getQtyOrdered().add(qtyOrdered));
		candidate.setQtyOrdered_TU(candidate.getQtyOrdered_TU().add(qtyOrderedTU));

		updateQtyToOrder(candidate);
	}

	@Override
	public void subtractQtyOrderedAndUpdate(final I_PMM_PurchaseCandidate candidate, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU)
	{
		candidate.setQtyOrdered(candidate.getQtyOrdered().subtract(qtyOrdered));
		candidate.setQtyOrdered_TU(candidate.getQtyOrdered_TU().subtract(qtyOrderedTU));

		// Update QtyToOrder
		{
			final BigDecimal qtyToOrderNew = candidate.getQtyToOrder().add(qtyOrdered);
			candidate.setQtyToOrder(qtyToOrderNew);

			final BigDecimal qtyToOrderTUNew = candidate.getQtyToOrder_TU().add(qtyOrderedTU);
			candidate.setQtyToOrder_TU(qtyToOrderTUNew);
		}
	}

	private void updateQtyToOrder(final I_PMM_PurchaseCandidate candidate)
	{
		candidate.setQtyToOrder(calculateQtyToOrder(candidate));
		candidate.setQtyToOrder_TU(calculateQtyToOrderTU(candidate));
	}

	private BigDecimal calculateQtyToOrder(final I_PMM_PurchaseCandidate candidate)
	{
		final BigDecimal qtyPromised = candidate.getQtyPromised();
		final BigDecimal qtyOrdered = candidate.getQtyOrdered();

		final BigDecimal qtyToOrder = qtyPromised.subtract(qtyOrdered);
		if (qtyToOrder.signum() < 0)
		{
			return BigDecimal.ZERO;
		}

		return qtyToOrder;
	}

	private BigDecimal calculateQtyToOrderTU(final I_PMM_PurchaseCandidate candidate)
	{
		final BigDecimal qtyPromisedTU = candidate.getQtyPromised_TU();
		final BigDecimal qtyOrderedTU = candidate.getQtyOrdered_TU();

		final BigDecimal qtyToOrderTU = qtyPromisedTU.subtract(qtyOrderedTU);
		if (qtyToOrderTU.signum() < 0)
		{
			return BigDecimal.ZERO;
		}

		return qtyToOrderTU;
	}

}
