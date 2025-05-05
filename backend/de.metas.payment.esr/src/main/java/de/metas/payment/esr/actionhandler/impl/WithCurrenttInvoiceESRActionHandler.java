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

import de.metas.logging.LogManager;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.esr.actionhandler.IESRActionHandler;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.slf4j.Logger;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice}. Invokes {@link IESRImportBL#linkInvoiceToPayment(I_ESR_ImportLine)}.
 * 
 */
public class WithCurrenttInvoiceESRActionHandler implements IESRActionHandler
{
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

	private static final transient Logger logger = LogManager.getLogger(ESRImportBL.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		final I_C_Invoice invoice = line.getC_Invoice();
		if (null != payment && null != invoice)
		{
			Services.get(IESRImportBL.class).linkInvoiceToPayment(line);
		}
		else
		{
			logger.warn("No payment found for line : " + line.getESR_ImportLine_ID());
			return false;
		}
		return true;
	}

}
