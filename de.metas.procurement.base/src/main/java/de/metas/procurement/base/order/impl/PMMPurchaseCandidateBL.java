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
	public BigDecimal calculateQtyToOrder(final I_PMM_PurchaseCandidate candidate)
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
}
