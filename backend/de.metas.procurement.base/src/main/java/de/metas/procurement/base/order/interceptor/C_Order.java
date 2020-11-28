package de.metas.procurement.base.order.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.procurement.base.order.IPMMPurchaseCandidateDAO;
import de.metas.util.Services;

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

@Interceptor(I_C_Order.class)
public class C_Order
{
	private static final String MSG_CannotReActiveOrderCreatedFromPurchaseCandidates = "@CannotReActiveOrderCreatedFromPurchaseCandidates@";

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_REVERSECORRECT, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL })
	public void onReversed(final I_C_Order order)
	{
		final IPMMPurchaseCandidateDAO purchaseCandidateDAO = Services.get(IPMMPurchaseCandidateDAO.class);
		purchaseCandidateDAO.deletePurchaseCandidateOrderLines(order);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void onReActivate(final I_C_Order order)
	{
		final IPMMPurchaseCandidateDAO purchaseCandidateDAO = Services.get(IPMMPurchaseCandidateDAO.class);
		final boolean wasCreatedFromPurchaseCandidates = purchaseCandidateDAO.retrivePurchaseCandidateOrderLines(order)
				.create()
				.anyMatch();

		if (wasCreatedFromPurchaseCandidates)
		{
			throw new AdempiereException(MSG_CannotReActiveOrderCreatedFromPurchaseCandidates);
		}
	}
}
