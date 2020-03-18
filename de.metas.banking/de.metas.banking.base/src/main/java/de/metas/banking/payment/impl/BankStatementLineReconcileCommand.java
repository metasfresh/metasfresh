package de.metas.banking.payment.impl;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.BankStatementLineReconcileRequest;
import de.metas.banking.payment.BankStatementLineReconcileRequest.PaymentToReconcile;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

final class BankStatementLineReconcileCommand
{
	private static final AdMessageKey BANK_STATEMENT_MUST_BE_COMPLETED_OR_IN_PROGRESS_MSG = AdMessageKey.of("de.metas.banking.process.C_BankStatement_AddBpartnerAndPayment.BankStatement_must_be_Completed_or_In_Progress");

	private final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final CurrencyRepository currencyRepository;

	//
	// Parameters
	private final BankStatementLineReconcileRequest request;

	//
	// State
	private ImmutableMap<PaymentId, I_C_Payment> paymentsCache;

	@Builder
	private BankStatementLineReconcileCommand(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final BankStatementLineReconcileRequest request)
	{
		this.currencyRepository = currencyRepository;

		this.request = request;
	}

	public void execute()
	{
		final I_C_BankStatementLine bankStatementLine = bankStatementDAO.getLineById(request.getBankStatementLineId());

		assertBankStatementLineIsStillValid(bankStatementLine);
		for (final PaymentToReconcile paymentToReconcile : request.getPaymentsToReconcile())
		{
			assertPaymentToReconcileIsStillValid(paymentToReconcile);
		}

		markAsMultiplePayments(bankStatementLine);

		int lineNo = 10;
		for (final PaymentToReconcile paymentToReconcile : request.getPaymentsToReconcile())
		{
			createBankStatementLineRef(paymentToReconcile, bankStatementLine, lineNo);
			lineNo += 10;
		}
	}

	private void assertBankStatementLineIsStillValid(final I_C_BankStatementLine bankStatementLine)
	{
		if (bankStatementBL.isReconciled(bankStatementLine))
		{
			throw new AdempiereException("Bank statement line is already reconciled");
		}

		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		final I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementId);
		final DocStatus docStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (!docStatus.isDraftedInProgressOrCompleted())
		{
			throw new AdempiereException(BANK_STATEMENT_MUST_BE_COMPLETED_OR_IN_PROGRESS_MSG);
		}

		final Amount statementLineAmt = extractStatementLineAmt(bankStatementLine);
		final Amount statementLineAmtToReconcile = request.getStatementLineAmtToReconcile();
		if (!statementLineAmt.subtract(statementLineAmtToReconcile).isZero())
		{
			throw new AdempiereException("Partial bank statement line reconciliation is not allowed")
					.setParameter("statementLineAmt", statementLineAmt)
					.setParameter("statementLineAmtToReconcile", statementLineAmtToReconcile);
		}
	}

	private I_C_Payment getPaymentById(@NonNull final PaymentId paymentId)
	{
		final I_C_Payment payment = getPaymentsCache().get(paymentId);
		Check.assumeNotNull(payment, "payment exists: {}", paymentId);
		return payment;
	}

	private ImmutableMap<PaymentId, I_C_Payment> getPaymentsCache()
	{
		ImmutableMap<PaymentId, I_C_Payment> paymentsCache = this.paymentsCache;
		if (paymentsCache == null)
		{
			paymentsCache = this.paymentsCache = retrievePayments();
		}
		return paymentsCache;
	}

	private ImmutableMap<PaymentId, I_C_Payment> retrievePayments()
	{
		final ImmutableSet<PaymentId> paymentIds = request.getPaymentsToReconcile().stream()
				.map(PaymentToReconcile::getPaymentId)
				.collect(ImmutableSet.toImmutableSet());

		return paymentDAO.getByIds(paymentIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						payment -> PaymentId.ofRepoId(payment.getC_Payment_ID()),
						payment -> payment));
	}

	private Amount extractStatementLineAmt(@NonNull final I_C_BankStatementLine record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(currencyId);
		return Amount.of(record.getStmtAmt(), currencyCode);
	}

	private void assertPaymentToReconcileIsStillValid(@NonNull final PaymentToReconcile paymentToReconcile)
	{
		final I_C_Payment payment = getPaymentById(paymentToReconcile.getPaymentId());
		final DocStatus docStatus = DocStatus.ofCode(payment.getDocStatus());
		if (!docStatus.isCompleted())
		{
			throw new AdempiereException("Payment shall be completed: " + payment.getDocumentNo());
		}

		if (payment.isReconciled())
		{
			throw new AdempiereException("Payment " + payment.getDocumentNo() + " was already reconciled");
		}

		final Amount statementLineAmtExpected = extractPayAmt(payment).negateIfNot(payment.isReceipt());
		if (!paymentToReconcile.getStatementLineAmt().isEqualByComparingTo(statementLineAmtExpected))
		{
			throw new AdempiereException("Cannot partially reconcile the payment " + payment.getDocumentNo());
		}

	}

	private Amount extractPayAmt(@NonNull final I_C_Payment payment)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(payment.getC_Currency_ID());
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(currencyId);
		return Amount.of(payment.getPayAmt(), currencyCode);
	}

	private void markAsMultiplePayments(final I_C_BankStatementLine bankStatementLine)
	{
		bankStatementLine.setIsMultiplePaymentOrInvoice(true);
		bankStatementLine.setIsMultiplePayment(true);
		bankStatementLine.setC_BPartner_ID(-1);
		bankStatementLine.setC_Payment_ID(-1);
		bankStatementLine.setC_Invoice_ID(-1);
		bankStatementLine.setDiscountAmt(BigDecimal.ZERO);
		bankStatementLine.setWriteOffAmt(BigDecimal.ZERO);
		bankStatementLine.setIsOverUnderPayment(false);
		bankStatementLine.setOverUnderAmt(BigDecimal.ZERO);
		bankStatementDAO.save(bankStatementLine);
	}

	private void createBankStatementLineRef(
			@NonNull final PaymentToReconcile paymentToReconcile,
			@NonNull final I_C_BankStatementLine bankStatementLine,
			final int lineNo)
	{
		final I_C_Payment payment = getPaymentById(paymentToReconcile.getPaymentId());

		final I_C_BankStatementLine_Ref bankStatementLineRef = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class, bankStatementLine);
		IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(bankStatementLineRef, true); // disable recalculation. we will do it at the end

		bankStatementLineRef.setAD_Org_ID(bankStatementLine.getAD_Org_ID());
		bankStatementLineRef.setC_BankStatementLine_ID(bankStatementLine.getC_BankStatementLine_ID());
		bankStatementLineRef.setLine(lineNo);

		//
		// Set Invoice from pay selection line
		bankStatementLineRef.setC_BPartner_ID(payment.getC_BPartner_ID());
		bankStatementLineRef.setC_Payment_ID(payment.getC_Payment_ID());
		bankStatementLineRef.setC_Invoice_ID(payment.getC_Invoice_ID());
		bankStatementLineRef.setC_Currency_ID(payment.getC_Currency_ID());

		// we store the psl's discount amount, because if we create a payment from this line, then we don't want the psl's Discount to end up as a mere underpayment.
		bankStatementLineRef.setTrxAmt(paymentToReconcile.getStatementLineAmt().getAsBigDecimal());
		bankStatementLineRef.setDiscountAmt(BigDecimal.ZERO);
		bankStatementLineRef.setWriteOffAmt(BigDecimal.ZERO);
		bankStatementLineRef.setIsOverUnderPayment(false);
		bankStatementLineRef.setOverUnderAmt(BigDecimal.ZERO);

		bankStatementDAO.save(bankStatementLineRef);

		//
		// Mark payment as reconciled
		payment.setIsReconciled(true);
		paymentDAO.save(payment);

		//
		// TODO:
		// Update pay selection line or ESR Import
		// linkBankStatementLine(psl, bankStatementLine, bankStatementLineRef);
	}
}
