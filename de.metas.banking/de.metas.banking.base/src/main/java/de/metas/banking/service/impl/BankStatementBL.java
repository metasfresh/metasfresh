package de.metas.banking.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

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

import de.metas.acct.api.IFactAcctDAO;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListener;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class BankStatementBL implements IBankStatementBL
{
	private static final Logger logger = LogManager.getLogger(BankStatementBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);
	private final IBankStatementListenerService listenersService = Services.get(IBankStatementListenerService.class);
	private final IPaymentBL paymentService = Services.get(IPaymentBL.class);
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);

	@Override
	public int getNextLineNo(final BankStatementId bankStatementId)
	{
		final int lastLineNo = bankStatementDAO.retrieveLastLineNo(bankStatementId);
		return lastLineNo + 10;
	}

	@Override
	public void handleAfterPrepare(final I_C_BankStatement bankStatement)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID());

		for (final I_C_BankStatementLine line : bankStatementDAO.retrieveLines(bankStatementId, I_C_BankStatementLine.class))
		{
			if (line.isMultiplePaymentOrInvoice() && line.isMultiplePayment())
			{
				// Payment in C_BankStatementLine_Ref are mandatory
				for (final I_C_BankStatementLine_Ref refLine : bankStatementDAO.retrieveLineReferences(line))
				{
					if (refLine.getC_Payment_ID() <= 0)
					{
						// TODO -> AD_Message
						throw new AdempiereException("Missing payment in reference line "
								+ refLine.getLine() + " of line "
								+ line.getLine());
					}
				}
			}
		}
	}

	@Override
	public void handleAfterComplete(final I_C_BankStatement bankStatement)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID());

		for (final I_C_BankStatementLine line : bankStatementDAO.retrieveLines(bankStatementId, I_C_BankStatementLine.class))
		{
			bankStatmentPaymentBL.findOrCreateUnreconciledPaymentsAndLinkToBankStatementLine(bankStatement, line);
			reconcilePaymentsFromBankStatementLine_Ref(bankStatementDAO, line);
		}
	}

	private void reconcilePaymentsFromBankStatementLine_Ref(final IBankStatementDAO bankStatementDAO, final I_C_BankStatementLine line)
	{
		if (line.isMultiplePaymentOrInvoice() && line.isMultiplePayment())
		{
			for (final I_C_BankStatementLine_Ref refLine : bankStatementDAO.retrieveLineReferences(line))
			{
				final PaymentId paymentId = PaymentId.ofRepoIdOrNull(refLine.getC_Payment_ID());
				if (paymentId != null)
				{
					paymentService.markReconciled(paymentId);
				}
			}
		}
	}

	@Override
	public void handleBeforeVoid(final I_C_BankStatement bankStatement)
	{
		final IBankStatementListener listeners = listenersService.getListeners();

		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatement.getC_BankStatement_ID());

		for (final I_C_BankStatementLine line : bankStatementDAO.retrieveLines(bankStatementId, I_C_BankStatementLine.class))
		{
			listeners.onBankStatementLineVoiding(line);

			if (line.isMultiplePaymentOrInvoice() && line.isMultiplePayment())
			{
				// NOTE: for line, the payment is unlinked in MBankStatement.voidIt()

				for (final I_C_BankStatementLine_Ref refLine : bankStatementDAO.retrieveLineReferences(line))
				{
					//
					// Unlink payment
					final PaymentId paymentId = PaymentId.ofRepoIdOrNull(refLine.getC_Payment_ID());
					if (paymentId != null)
					{
						paymentService.markNotReconciled(paymentId);

						refLine.setC_Payment_ID(-1);
						bankStatementDAO.save(refLine);
					}
				}
			}
		}

	}

	@Override
	public void recalculateStatementLineAmounts(final org.compiere.model.I_C_BankStatementLine bankStatementLine)
	{
		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(bankStatementLine, I_C_BankStatementLine.class);
		if (!bsl.isMultiplePaymentOrInvoice())
		{
			return;
		}

		//
		// Aggregated amounts from reference lines:
		BigDecimal totalStmtAmt = BigDecimal.ZERO;
		BigDecimal totalTrxAmt = BigDecimal.ZERO;
		BigDecimal totalDiscountAmt = BigDecimal.ZERO;
		BigDecimal totalWriteOffAmt = BigDecimal.ZERO;
		BigDecimal totalOverUnderAmt = BigDecimal.ZERO;

		//
		// Iterate all reference lines and build up the aggregated amounts
		for (final I_C_BankStatementLine_Ref refLine : bankStatementDAO.retrieveLineReferences(bsl))
		{
			if (refLine.getC_Currency_ID() == bsl.getC_Currency_ID())
			{
				totalStmtAmt = totalStmtAmt.add(refLine.getTrxAmt());
				totalTrxAmt = totalTrxAmt.add(refLine.getTrxAmt());
				totalDiscountAmt = totalDiscountAmt.add(refLine.getDiscountAmt());
				totalWriteOffAmt = totalWriteOffAmt.add(refLine.getWriteOffAmt());
				totalOverUnderAmt = totalOverUnderAmt.add(refLine.getOverUnderAmt());
			}
			else
			{
				Check.assume(refLine.getC_Invoice_ID() > 0, "@NotFound@ @C_Invoice_ID@");
				final I_C_Invoice inv = refLine.getC_Invoice();

				final CurrencyConversionContext conversionCtx = currencyConversionBL.createCurrencyConversionContext(
						TimeUtil.asLocalDate(bsl.getDateAcct()), // ConvDate,
						CurrencyConversionTypeId.ofRepoIdOrNull(inv.getC_ConversionType_ID()), // ConversionType_ID,
						ClientId.ofRepoId(bsl.getAD_Client_ID()), // AD_Client_ID
						OrgId.ofRepoId(bsl.getAD_Org_ID()) // AD_Org_ID
				);

				final CurrencyId refLineCurrencyId = CurrencyId.ofRepoId(refLine.getC_Currency_ID());
				final CurrencyId bslCurrencyId = CurrencyId.ofRepoId(bsl.getC_Currency_ID());

				final BigDecimal trxAmt = currencyConversionBL.convert(conversionCtx,
						refLine.getTrxAmt(),
						refLineCurrencyId, // CurFrom_ID,
						bslCurrencyId) // CurTo_ID
						.getAmount();
				final BigDecimal discountAmt = currencyConversionBL.convert(conversionCtx,
						refLine.getDiscountAmt(),
						refLineCurrencyId, // CurFrom_ID,
						bslCurrencyId) // CurTo_ID
						.getAmount();
				final BigDecimal writeOffAmt = currencyConversionBL.convert(conversionCtx,
						refLine.getWriteOffAmt(),
						refLineCurrencyId, // CurFrom_ID,
						bslCurrencyId) // CurTo_ID
						.getAmount();
				final BigDecimal overUnderAmt = currencyConversionBL.convert(conversionCtx,
						refLine.getOverUnderAmt(),
						refLineCurrencyId, // CurFrom_ID,
						bslCurrencyId) // CurTo_ID
						.getAmount();

				totalStmtAmt = totalStmtAmt.add(trxAmt);
				totalTrxAmt = totalTrxAmt.add(trxAmt);
				totalDiscountAmt = totalDiscountAmt.add(discountAmt);
				totalWriteOffAmt = totalWriteOffAmt.add(writeOffAmt);
				totalOverUnderAmt = totalOverUnderAmt.add(overUnderAmt);
			}
		}

		//
		// Update the bank statement line with calculated aggregated amounts
		if (logger.isDebugEnabled())
		{
			logger.debug(" stmtAmt: " + totalStmtAmt + " discountAmt: " + totalDiscountAmt + " writeOffAmt: " + totalWriteOffAmt + " overUnderAmt: " + totalOverUnderAmt);
		}
		bsl.setStmtAmt(totalStmtAmt);
		bsl.setTrxAmt(totalTrxAmt);
		bsl.setDiscountAmt(totalDiscountAmt);
		bsl.setWriteOffAmt(totalWriteOffAmt);
		bsl.setOverUnderAmt(totalOverUnderAmt);
		if (totalOverUnderAmt.signum() != 0)
		{
			bsl.setIsOverUnderPayment(true);
		}
		else
		{
			bsl.setIsOverUnderPayment(false);
		}
		InterfaceWrapperHelper.save(bsl);
	}

	@Override
	public void updateEndingBalance(final I_C_BankStatement bankStatement)
	{
		final BigDecimal endingBalance = bankStatement
				.getBeginningBalance()
				.add(bankStatement.getStatementDifference());

		bankStatement.setEndingBalance(endingBalance);
	}

	@Override
	public void unpost(final I_C_BankStatement bankStatement)
	{
		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(bankStatement);
		MPeriod.testPeriodOpen(ctx, bankStatement.getStatementDate(), X_C_DocType.DOCBASETYPE_BankStatement, bankStatement.getAD_Org_ID());

		factAcctDAO.deleteForDocumentModel(bankStatement);

		bankStatement.setPosted(false);
		InterfaceWrapperHelper.save(bankStatement);
	}

	@Override
	public void setDate(
			@NonNull final org.compiere.model.I_C_BankStatementLine bankStatementLine,
			@NonNull final Timestamp date)
	{
		bankStatementLine.setStatementLineDate(date);
		bankStatementLine.setValutaDate(date);
		bankStatementLine.setDateAcct(date);
	}
}
