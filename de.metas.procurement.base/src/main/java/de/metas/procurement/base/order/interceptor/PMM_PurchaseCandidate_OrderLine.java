package de.metas.procurement.base.order.interceptor;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate_OrderLine;

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

@Interceptor(I_PMM_PurchaseCandidate_OrderLine.class)
public class PMM_PurchaseCandidate_OrderLine
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(final I_PMM_PurchaseCandidate_OrderLine alloc)
	{
		final BigDecimal qtyOrdered = alloc.getQtyOrdered();
		final I_PMM_PurchaseCandidate candidate = alloc.getPMM_PurchaseCandidate();
		
		candidate.setQtyOrdered(candidate.getQtyOrdered().subtract(qtyOrdered));
		candidate.setQtyToOrder(candidate.getQtyToOrder().add(qtyOrdered));
		InterfaceWrapperHelper.save(candidate);
	}
}
