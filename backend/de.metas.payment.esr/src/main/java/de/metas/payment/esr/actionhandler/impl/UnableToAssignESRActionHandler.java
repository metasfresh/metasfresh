package de.metas.payment.esr.actionhandler.impl;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;

import de.metas.payment.PaymentId;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class UnableToAssignESRActionHandler extends AbstractESRActionHandler
{

	@Override
	public boolean process(I_ESR_ImportLine line, String message)
	{
		super.process(line, message);
		// 04517 : Payment was created on a previous step. Do nothing.

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		if (null != payment)
		{
			// 07783 : unset the flag
			payment.setIsAutoAllocateAvailableAmt(false);
			InterfaceWrapperHelper.save(payment);
		}

		return true;
	}

}
