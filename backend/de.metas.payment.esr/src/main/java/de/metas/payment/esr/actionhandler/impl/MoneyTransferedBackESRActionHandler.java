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
import java.time.LocalDate;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TrxRunnable;

import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

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

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		final I_C_Payment linePayment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

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
		
		trxManager.run(trxName, (TrxRunnable)localTrxName -> {
			// must assure that the line has transaction
			InterfaceWrapperHelper.refresh(line, ITrx.TRXNAME_ThreadInherited);
			
			// Create the reversal payment
			final LocalDate dateTrx = SystemTime.asLocalDate();
			final I_C_Payment transferBackPayment =
					Services.get(IPaymentBL.class).newOutboundPaymentBuilder()
						.adOrgId(OrgId.ofRepoId(line.getAD_Org_ID()))
						.bpartnerId(BPartnerId.ofRepoId(linePayment.getC_BPartner_ID()))
						.payAmt(transferedBackAmt)
						.currencyId(CurrencyId.ofRepoId(linePayment.getC_Currency_ID()))
						.tenderType(TenderType.DirectDeposit)
						.orgBankAccountId(BankAccountId.ofRepoId(linePayment.getC_BP_BankAccount_ID()))
						.dateAcct(dateTrx)
						.dateTrx(dateTrx)
						.createAndProcess();

			// Create the allocation
			// @formatter:off
			Services.get(IAllocationBL.class).newBuilder()
					.orgId(line.getAD_Org_ID())
					.currencyId(transferBackPayment.getC_Currency_ID())
					.dateAcct(transferBackPayment.getDateAcct())
					.dateTrx(transferBackPayment.getDateTrx())
					.addLine()
						.orgId(line.getAD_Org_ID())
						.bpartnerId(linePayment.getC_BPartner_ID())
						.paymentId(linePayment.getC_Payment_ID())
						.amount(transferedBackAmt)
					.lineDone()
					.addLine()
						.orgId(line.getAD_Org_ID())
						.bpartnerId(transferBackPayment.getC_BPartner_ID())
						.paymentId(transferBackPayment.getC_Payment_ID())
						.amount(transferedBackAmt.negate())
					.lineDone()
					.create(true);
			// @formatter:on
		});
		return true;
	}
}
