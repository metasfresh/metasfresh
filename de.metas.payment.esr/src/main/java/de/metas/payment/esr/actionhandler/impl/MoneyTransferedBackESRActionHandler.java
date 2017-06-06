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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TrxRunnable;

import de.metas.allocation.api.IAllocationBL;
import de.metas.payment.api.DefaultPaymentBuilder.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner}. Creates a reversal payment for the given line's payment. The reversal's
 * amount is the line's payment's over payment amount, if the line's payment references an invoice and the full payAmt otherwise. The reversal payment is completed and allocated against the line's
 * payment.
 * 
 */
public class MoneyTransferedBackESRActionHandler extends AbstractESRActionHandler
{

	@Override
	public boolean process(final I_ESR_ImportLine line, String message)
	{
		super.process(line, message);

		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);
		
		final I_C_Payment linePayment = line.getC_Payment();
		InterfaceWrapperHelper.refresh(linePayment, trxName); // refresh the payment : very important; otherwise the over amount is not seen
		
		Check.assumeNotNull(linePayment, "Null payment for line {}", line.getESR_ImportLine_ID());

		final BigDecimal transferedBackAmt;
		if (linePayment.getC_Invoice_ID() > 0)
		{
			// If there is an invoice, then OverUnderAmt for transfer needs to be positive.
			Check.errorIf(
					linePayment.getOverUnderAmt().signum() < 0
					, "PayAmt for line {} needs to be greater zero, but is {}.", line.getESR_ImportLine_ID(), linePayment.getOverUnderAmt());

			// we only transfered back the money which is more than invoiced
			transferedBackAmt = linePayment.getOverUnderAmt();
		}
		else
		{
			// there is no invoice, so we transfer back all the money
			transferedBackAmt = linePayment.getPayAmt();
		}
		
		trxManager.run(trxName, new TrxRunnable()
		{
			@Override
			public void run(String trxName) throws Exception
			{
				// must assure that the line has transaction
				InterfaceWrapperHelper.refresh(line, trxName);
				
				// Create the reversal payment
				final I_C_Payment transferBackPayment =
						Services.get(IPaymentBL.class).newBuilder(line)
							.setAD_Org_ID(line.getAD_Org_ID())
							.setC_BPartner_ID(linePayment.getC_BPartner_ID())
							.setDocbaseType(X_C_DocType.DOCBASETYPE_APPayment)
							.setPayAmt(transferedBackAmt)
							.setC_Currency_ID(linePayment.getC_Currency_ID())
							.setTenderType(TenderType.ACH)
							.setC_BP_BankAccount_ID(linePayment.getC_BP_BankAccount_ID())
							.setDateAcct(SystemTime.asDayTimestamp())
							.setDateTrx(SystemTime.asDayTimestamp())
							.createAndProcess();
		
				// Create the allocation
				// @formatter:off
				Services.get(IAllocationBL.class).newBuilder(InterfaceWrapperHelper.getContextAware(line))
						.setAD_Org_ID(line.getAD_Org_ID())
						.setC_Currency_ID(transferBackPayment.getC_Currency_ID())
						.setDateAcct(transferBackPayment.getDateAcct())
						.setDateTrx(transferBackPayment.getDateTrx())
						.addLine()
							.setAD_Org_ID(line.getAD_Org_ID())
							.setC_BPartner_ID(linePayment.getC_BPartner_ID())
							.setC_Payment_ID(linePayment.getC_Payment_ID())
							.setAmount(transferedBackAmt)
						.lineDone()
						.addLine()
							.setAD_Org_ID(line.getAD_Org_ID())
							.setC_BPartner_ID(transferBackPayment.getC_BPartner_ID())
							.setC_Payment_ID(transferBackPayment.getC_Payment_ID())
							.setAmount(transferedBackAmt.negate())
						.lineDone()
						.create(true);
				// @formatter:on
			}
		});
		return true;
	}
}
