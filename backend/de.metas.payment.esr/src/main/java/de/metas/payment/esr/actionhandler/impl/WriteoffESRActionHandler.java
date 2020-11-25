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

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TrxRunnable;

import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Write_Off_Amount}. This handler writes of the open amount of the line's invoice. For lines that don't have an
 * invoice, the handler does nothing.
 * 
 */
public class WriteoffESRActionHandler extends AbstractESRActionHandler
{

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		super.process(line, message);

		Check.assumeNotNull(line.getESR_Payment_Action(), "@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@");

		// 04192 : Writeoff the invoice.
		final I_C_Invoice writeoffInvoice = line.getC_Invoice();
		if (writeoffInvoice == null)
		{
			// We have nothing to do, but the line should still be flagged as processed.
			return true;
		}

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		// sanity check: there must be an payment with a negative OverUnderAmt

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);

		InterfaceWrapperHelper.refresh(payment, trxName); // refresh the payment : very important; otherwise the over amount is not seen
		InterfaceWrapperHelper.refresh(writeoffInvoice, trxName); // refresh the payment : very important; otherwise the over amount is not seen
		Check.assumeNotNull(payment, "Null payment for line {}", line.getESR_ImportLine_ID());
		Check.errorIf(payment.getOverUnderAmt().signum() > 0, "Exces payment for line {}. Can't write this off", line.getESR_ImportLine_ID());

		final BigDecimal writeOffAmt = invoiceDAO.retrieveOpenAmt(writeoffInvoice);

		trxManager.run(trxName, new TrxRunnable()
		{
			@Override
			public void run(String trxName) throws Exception
			{
				// must assure that the invoice has transaction
				InterfaceWrapperHelper.refresh(writeoffInvoice, trxName);
				invoiceBL.writeOffInvoice(writeoffInvoice, writeOffAmt, message);
			}
		});
		return true;

	}

}
