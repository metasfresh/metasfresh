package de.metas.payment.esr.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.logging.Level;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.CLogger;

import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.spi.IESRActionHandler;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice}. Invokes {@link IESRImportBL#linkInvoiceToPayment(I_ESR_ImportLine)}.
 * 
 */
public class WithCurrenttInvoiceESRActionHandler implements IESRActionHandler
{

	private static final transient CLogger logger = CLogger.getCLogger(ESRImportBL.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{

		final I_C_Payment payment = line.getC_Payment();
		final I_C_Invoice invoice = line.getC_Invoice();
		if (null != payment && null != invoice)
		{
			Services.get(IESRImportBL.class).linkInvoiceToPayment(line);
			Services.get(IInvoiceBL.class).testAllocation(invoice);
		}
		else
		{
			logger.log(Level.WARNING, "No payment found for line : " + line.getESR_ImportLine_ID());
			return false;
		}
		return true;
	}

}
