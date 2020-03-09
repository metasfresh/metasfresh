package de.metas.banking.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Payment;
import org.compiere.model.Query;
import org.compiere.util.TimeUtil;

import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.ICashStatementBL;
import de.metas.util.Services;

public class CashStatementBL implements ICashStatementBL
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
	private final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);

	@Override
	public I_C_BankStatementLine createCashStatementLine(final I_C_Payment payment)
	{
		I_C_BankStatement bs = getCreateCashStatement(payment);

		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine.class);
		bsl.setAD_Org_ID(bs.getAD_Org_ID());
		bsl.setC_BankStatement_ID(bs.getC_BankStatement_ID());
		bankStatementBL.setDate(bsl, bs.getStatementDate());
		bankStatmentPaymentBL.setPayment(bsl, payment);

		bsl.setProcessed(true);
		InterfaceWrapperHelper.save(bsl);

		return bsl;
	}

	private I_C_BankStatement getCreateCashStatement(final I_C_Payment payment)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);
		final int C_BP_BankAccount_ID = payment.getC_BP_BankAccount_ID();
		final Timestamp statementDate = TimeUtil.getDay(payment.getDateTrx());

		String whereClause = I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID + "=?"
				+ " AND TRUNC(" + I_C_BankStatement.COLUMNNAME_StatementDate + ")=?"
				+ " AND " + I_C_BankStatement.COLUMNNAME_Processed + "=?";

		I_C_BankStatement bs = new Query(ctx, I_C_BankStatement.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { C_BP_BankAccount_ID, statementDate, false })
				.firstOnly();
		if (bs != null)
		{
			return bs;
		}

		// Get BankAccount/CashBook
		I_C_BP_BankAccount ba = InterfaceWrapperHelper.create(ctx, C_BP_BankAccount_ID, I_C_BP_BankAccount.class, trxName);
		if (ba == null || ba.getC_BP_BankAccount_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_BP_BankAccount_ID@ (ID=" + C_BP_BankAccount_ID + ")");
		}

		// Create Statement
		bs = createBankStatement(ba, statementDate);
		return bs;
	}

	private I_C_BankStatement createBankStatement(
			final I_C_BP_BankAccount account,
			final Timestamp statementDate)
	{
		final I_C_BankStatement bankStatement = InterfaceWrapperHelper.newInstance(I_C_BankStatement.class);
		bankStatement.setAD_Org_ID(account.getAD_Org_ID());
		bankStatement.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		bankStatement.setStatementDate(statementDate);

		bankStatement.setName(statementDate.toString());
		bankStatement.setIsManual(false);
		bankStatementDAO.save(bankStatement);

		return bankStatement;
	}
}
