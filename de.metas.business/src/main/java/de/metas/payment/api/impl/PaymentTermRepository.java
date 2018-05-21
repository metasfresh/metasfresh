package de.metas.payment.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;

import org.compiere.model.I_C_PaymentTerm;

import de.metas.payment.api.IPaymentTermRepository;

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

public class PaymentTermRepository implements IPaymentTermRepository
{
	private I_C_PaymentTerm getById(final int paymentTermId)
	{
		return loadOutOfTrx(paymentTermId, I_C_PaymentTerm.class);
	}

	@Override
	public BigDecimal getPaymentTermDiscount(final int paymentTermId)
	{
		if (paymentTermId <= 0)
		{
			return BigDecimal.ZERO;
		}

		final I_C_PaymentTerm paymentTerm = getById(paymentTermId);
		if (paymentTerm == null)
		{
			return BigDecimal.ZERO;
		}

		return paymentTerm.getDiscount();

	}
}
