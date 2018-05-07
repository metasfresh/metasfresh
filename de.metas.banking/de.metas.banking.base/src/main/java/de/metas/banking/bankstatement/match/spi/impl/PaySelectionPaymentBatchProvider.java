package de.metas.banking.bankstatement.match.spi.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;

import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.bankstatement.match.spi.IPaymentBatchProvider;
import de.metas.banking.bankstatement.match.spi.PaymentBatch;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.model.I_C_PaySelectionLine;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.i18n.IMsgBL;

/*
 * #%L
 * de.metas.banking.swingui
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

public class PaySelectionPaymentBatchProvider implements IPaymentBatchProvider
{

	@Override
	public IPaymentBatch retrievePaymentBatch(final I_C_Payment payment)
	{
		final I_C_PaySelection paySelection = Services.get(IPaySelectionDAO.class).retrievePaySelection(payment);
		if (paySelection == null)
		{
			return null;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		return PaymentBatch.builder()
				.setRecord(paySelection)
				.setName(msgBL.translate(Env.getCtx(), I_C_PaySelection.COLUMNNAME_C_PaySelection_ID) + " #" + paySelection.getName())
				.setDate(paySelection.getPayDate())
				.setPaymentBatchProvider(this)
				.build();
	}

	@Override
	public void linkBankStatementLine(final IPaymentBatch paymentBatch, final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		final IContextAware context = InterfaceWrapperHelper.getContextAware(bankStatementLineRef);
		final I_C_PaySelection paySelection = paymentBatch.getRecord().getModel(context, I_C_PaySelection.class);
		if (paySelection == null)
		{
			return;
		}
		
		final I_C_PaySelectionLine paySelectionLine = Services.get(IPaySelectionDAO.class).retrievePaySelectionLineForPayment(paySelection, bankStatementLineRef.getC_Payment_ID());
		if(paySelectionLine == null)
		{
			return;
		}
		
		final I_C_BankStatementLine bankStatementLine = bankStatementLineRef.getC_BankStatementLine();
		Services.get(IPaySelectionBL.class).linkBankStatementLine(paySelectionLine, bankStatementLine, bankStatementLineRef);
	}
}
