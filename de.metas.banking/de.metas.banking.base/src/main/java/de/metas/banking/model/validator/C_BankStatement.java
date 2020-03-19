package de.metas.banking.model.validator;

/*
 * #%L
 * de.metas.banking.base
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


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.ModelValidator;

import de.metas.banking.service.IBankStatementBL;
import de.metas.util.Services;

@Interceptor(I_C_BankStatement.class)
public class C_BankStatement
{
	public static final C_BankStatement instance = new C_BankStatement();

	private C_BankStatement()
	{
		super();
	}

	// NOTE: commented out because initally this interceptor was not registered and it seems that updating the ending balance on prepare was sufficient.
	// @ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
	// ifColumnsChanged = { I_C_BankStatement.COLUMNNAME_BeginningBalance, I_C_BankStatement.COLUMNNAME_EndingBalance })
	// public void updateEndingBalance(final I_C_BankStatement bankStatement)
	// {
	// Services.get(IBankStatementBL.class).updateEndingBalance(bankStatement);
	// }
	

	// @DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	// public void createPayments(final I_C_BankStatement bankStatement)
	// {
	// // TODO: iterate the lines that reference an invoice, create and allocate payments
	// final List<I_C_BankStatementLine> bankStatementLines =
	// Services.get(IBankStatementDAO.class).reterieveLines(bankStatement, I_C_BankStatementLine.class);
	// for (final I_C_BankStatementLine bankStatementLine : bankStatementLines)
	// {
	// if (bankStatementLine.getC_Invoice_ID() <= 0)
	// {
	// continue; // nothing to do
	// }
	//
	// final I_C_Invoice invoice = bankStatementLine.getC_Invoice();
	//
	// Services.get(IPaymentBL.class)
	// .newBuilder(bankStatement)
	// .setC_Invoice(invoice)
	// .setC_Currency_ID(bankStatementLine.getC_Currency_ID())
	// .setPayAmt(bankStatementLine.getStmtAmt())
	// .create(true);
	// // wtf about different amounts in the bsl?
	// // undisplay other fields?
	// }
	// }

	@DocValidate(timings = ModelValidator.TIMING_AFTER_PREPARE)
	public void handleAfterPrepare(final I_C_BankStatement bankStatement)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		bankStatementBL.handleAfterPrepare(bankStatement);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void handleAfterComplete(final I_C_BankStatement bankStatement)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		bankStatementBL.handleAfterComplete(bankStatement);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_VOID)
	public void handleBeforeVoid(final I_C_BankStatement bankStatement)
	{
		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		bankStatementBL.handleBeforeVoid(bankStatement);
	}

}
