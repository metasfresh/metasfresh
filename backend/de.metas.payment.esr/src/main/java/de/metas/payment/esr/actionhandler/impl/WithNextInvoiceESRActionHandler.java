package de.metas.payment.esr.actionhandler.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;

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

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice}. This handler updates the line's payment, setting
 * C_Payment.IsAutoAllocateAvailableAmt = 'Y'. Therefore the system will automatically allocate the yet unallocated money to the next invoice(s) of the same BPartner.
 * 
 */
public class WithNextInvoiceESRActionHandler extends AbstractESRActionHandler
{

	private static final transient Logger logger = LogManager.getLogger(ESRImportBL.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		super.process(line, message);

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		if (null != payment)
		{
			// 04193 : Just set the flag, the logic is handled on completion of an invoice.
			payment.setIsAutoAllocateAvailableAmt(true);
			InterfaceWrapperHelper.save(payment);
		}
		else
		{
			logger.warn("No payment found for line : " + line.getESR_ImportLine_ID());
			return false;
		}
		return true;
	}

}
