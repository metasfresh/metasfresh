package de.metas.procurement.base.order.impl;

import java.math.BigDecimal;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IPMMPricingAware;
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
	public void setQtyPromised(final I_PMM_PurchaseCandidate candidate, final BigDecimal qtyPromised, final BigDecimal qtyPromisedTU)
	{
		candidate.setQtyPromised(qtyPromised);
		candidate.setQtyPromised_TU(qtyPromisedTU);
	}

	@Override
	public void addQtyOrderedAndResetQtyToOrder(final I_PMM_PurchaseCandidate candidate, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU)
	{
		candidate.setQtyOrdered(candidate.getQtyOrdered().add(qtyOrdered));
		candidate.setQtyOrdered_TU(candidate.getQtyOrdered_TU().add(qtyOrderedTU));

		resetQtyToOrder(candidate);
	}

	@Override
	public void subtractQtyOrdered(final I_PMM_PurchaseCandidate candidate, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU)
	{
		candidate.setQtyOrdered(candidate.getQtyOrdered().subtract(qtyOrdered));
		candidate.setQtyOrdered_TU(candidate.getQtyOrdered_TU().subtract(qtyOrderedTU));
	}

	@Override
	public void updateQtyToOrderFromQtyToOrderTU(final I_PMM_PurchaseCandidate candidate)
	{
		final I_M_HU_PI_Item_Product huPIItemProduct = candidate.getM_HU_PI_Item_Product();
		if (huPIItemProduct != null)
		{
			final BigDecimal qtyToOrderTU = candidate.getQtyToOrder_TU();
			final BigDecimal tuCapacity = huPIItemProduct.getQty();
			final BigDecimal qtyToOrder = qtyToOrderTU.multiply(tuCapacity);
			candidate.setQtyToOrder(qtyToOrder);
		}
		else
		{
			candidate.setQtyOrdered(candidate.getQtyToOrder_TU());
		}
	}

	@Override
	public void resetQtyToOrder(I_PMM_PurchaseCandidate candidate)
	{
		candidate.setQtyToOrder(BigDecimal.ZERO);
		candidate.setQtyToOrder_TU(BigDecimal.ZERO);
	}

	@Override
	public IPMMPricingAware asPMMPricingAware(final I_PMM_PurchaseCandidate candidate)
	{
		return PMMPricingAware_PurchaseCandidate.of(candidate);
	}
}
