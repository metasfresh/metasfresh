package de.metas.banking.service.impl;

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

import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.ICashStatementBL;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.util.Services;

public class CashStatementBL implements ICashStatementBL
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	// metas: us025b
	@Override
	public void createCashStatementLine(final I_C_Payment payment)
	{
		final IBankStatementPaymentBL bankStatmentPaymentBL = Services.get(IBankStatementPaymentBL.class);

		final I_C_BankStatement bs = getCreateCashStatement(payment);
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bs.getC_BankStatement_ID());
		final LocalDate statementDate = TimeUtil.asLocalDate(bs.getStatementDate());

		final Money paymentAmt = extractPayAmt(payment);
		final PaymentDirection paymentDirection = PaymentDirection.ofReceiptFlag(payment.isReceipt());
		final Money statementAmt = paymentDirection.convertPayAmtToStatementAmt(paymentAmt);

		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(bankStatementId)
				.orgId(OrgId.ofRepoId(bs.getAD_Org_ID()))
				//
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				//
				.statementLineDate(statementDate)
				//
				.statementAmt(statementAmt)
				.trxAmt(statementAmt)
				//
				.build());

		final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(bankStatementLineId);
		bankStatmentPaymentBL.linkSinglePayment(bankStatement, bankStatementLine, payment);
	}

	private static Money extractPayAmt(final I_C_Payment payment)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(payment.getC_Currency_ID());
		return Money.of(payment.getPayAmt(), currencyId);
	}

	// metas: us025b
	private I_C_BankStatement getCreateCashStatement(final I_C_Payment payment)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);
		final int C_BP_BankAccount_ID = payment.getC_BP_BankAccount_ID();
		final LocalDate statementDate = TimeUtil.asLocalDate(payment.getDateTrx());

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
		return createBankStatement(ba, statementDate);
	}

	private I_C_BankStatement createBankStatement(final I_C_BP_BankAccount account, final LocalDate statementDate)
	{
		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(BankStatementCreateRequest.builder()
				.orgId(OrgId.ofRepoId(account.getAD_Org_ID()))
				.orgBankAccountId(BankAccountId.ofRepoId(account.getC_BP_BankAccount_ID()))
				.statementDate(statementDate)
				.name(statementDate.toString())
				.build());

		return bankStatementDAO.getById(bankStatementId);
	}
}
