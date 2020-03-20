package de.metas.banking.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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
import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.Query;
import org.compiere.util.TimeUtil;

import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.ICashStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Services;

public class CashStatementBL implements ICashStatementBL
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	// metas: us025b
	@Override
	public void createCashStatementLine(final I_C_Payment payment)
	{
		final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);

		final I_C_BankStatement bs = getCreateCashStatement(payment);
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bs.getC_BankStatement_ID());
		final LocalDate statementDate = TimeUtil.asLocalDate(bs.getStatementDate());

		final CurrencyId currencyId = CurrencyId.ofRepoId(payment.getC_Currency_ID());
		final Money paymentAmt = Money.of(payment.getPayAmt(), currencyId);
		final Money statementAmt = paymentAmt.negateIf(!payment.isReceipt());

		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(bankStatementId)
				.orgId(OrgId.ofRepoId(bs.getAD_Org_ID()))
				//
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				//
				.statementLineDate(statementDate)
				.dateAcct(statementDate)
				.valutaDate(statementDate)
				//
				.statementAmt(statementAmt)
				.trxAmt(statementAmt)
				.chargeAmt(Money.zero(currencyId))
				.interestAmt(Money.zero(currencyId))
				//
				.build());

		final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(bankStatementLineId);
		bankStatmentPaymentBL.linkSinglePayment(bankStatement, bankStatementLine, payment);
	}

	// metas: us025b
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

	private I_C_BankStatement createBankStatement(I_C_BP_BankAccount account, Timestamp statementDate)
	{
		final I_C_BankStatement bs = newInstance(I_C_BankStatement.class);
		bs.setAD_Org_ID(account.getAD_Org_ID());
		bs.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		bs.setStatementDate(statementDate);

		bs.setName(bs.getStatementDate().toString());
		bs.setIsManual(false);
		bankStatementDAO.save(bs);
		return bs;
	}

	// metas end

}
