package de.metas.banking.payment.impl;

import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.banking.BankStatementLineReference;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest.PaymentToLink;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkResult;
import de.metas.banking.payment.PaymentLinkResult;
import de.metas.banking.service.BankStatementLineRefCreateRequest;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentReconcileReference;
import de.metas.util.Check;
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

final class BankStatementLineMultiPaymentLinkCommand
{
	private static final AdMessageKey BANK_STATEMENT_MUST_BE_COMPLETED_OR_IN_PROGRESS_MSG = AdMessageKey.of("de.metas.banking.process.C_BankStatement_AddBpartnerAndPayment.BankStatement_must_be_Completed_or_In_Progress");

	//
	// Services
	private final IBankStatementBL bankStatementBL;
	private final IBankStatementDAO bankStatementDAO;
	private final IPaymentBL paymentBL;
	private final IBankStatementListenerService bankStatementListenersService;
	private final MoneyService moneyService;

	//
	// Parameters
	private final BankStatementLineMultiPaymentLinkRequest request;

	//
	// State
	private ImmutableMap<PaymentId, I_C_Payment> paymentsCache; // lazy
	private boolean doReconcilePayments = false;
	private final ArrayList<PaymentLinkResult> linkedPayments = new ArrayList<>();

	@Builder
	private BankStatementLineMultiPaymentLinkCommand(
			@NonNull final IBankStatementBL bankStatementBL,
			@NonNull final IBankStatementDAO bankStatementDAO,
			@NonNull final IPaymentBL paymentBL,
			@NonNull final IBankStatementListenerService bankStatementListenersService,
			@NonNull final MoneyService moneyService,
			//
			@NonNull final BankStatementLineMultiPaymentLinkRequest request)
	{
		this.bankStatementBL = bankStatementBL;
		this.bankStatementDAO = bankStatementDAO;
		this.paymentBL = paymentBL;
		this.bankStatementListenersService = bankStatementListenersService;
		this.moneyService = moneyService;

		this.request = request;
	}

	public BankStatementLineMultiPaymentLinkResult execute()
	{
		final BankStatementLineId bankStatementLineId = request.getBankStatementLineId();
		final I_C_BankStatementLine bankStatementLine = bankStatementBL.getLineById(bankStatementLineId);

		final BankStatementId bankStatementId = BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID());
		final I_C_BankStatement bankStatement = bankStatementBL.getById(bankStatementId);

		final DocStatus docStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (!docStatus.isDraftedInProgressOrCompleted())
		{
			throw new AdempiereException(BANK_STATEMENT_MUST_BE_COMPLETED_OR_IN_PROGRESS_MSG);
		}
		this.doReconcilePayments = docStatus.isCompleted();

		assertBankStatementLineIsStillValid(bankStatementLine);

		for (final PaymentToLink paymentToLink : request.getPaymentsToLink())
		{
			assertPaymentToLinkIsStillValid(paymentToLink);
		}

		markAsMultiplePayments(bankStatementLine);

		int lineNo = 10;
		for (final PaymentToLink paymentToLink : request.getPaymentsToLink())
		{
			createBankStatementLineRef(paymentToLink, bankStatementLine, lineNo);
			lineNo += 10;
		}

		final BankStatementLineMultiPaymentLinkResult result = BankStatementLineMultiPaymentLinkResult.builder()
				.bankStatementLineId(bankStatementLineId)
				.payments(linkedPayments)
				.build();

		bankStatementListenersService.firePaymentsLinked(result.getPayments());

		bankStatementBL.unpost(bankStatement);

		return result;
	}

	private void assertBankStatementLineIsStillValid(@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		if (bankStatementLine.isReconciled())
		{
			throw new AdempiereException("Bank statement line is already reconciled");
		}

		final Amount statementLineAmt = extractStatementLineAmt(bankStatementLine);
		final Amount statementLineAmtToLink = request.getTotalStatementLineAmt();
		if (!statementLineAmt.subtract(statementLineAmtToLink).isZero())
		{
			throw new AdempiereException("Partial bank statement line reconciliation is not allowed")
					.setParameter("statementLineAmt", statementLineAmt)
					.setParameter("statementLineAmtToLink", statementLineAmtToLink);
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
		final ImmutableSet<PaymentId> paymentIds = request.getPaymentsToLink().stream()
				.map(PaymentToLink::getPaymentId)
				.collect(ImmutableSet.toImmutableSet());

		return paymentBL.getByIds(paymentIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						payment -> PaymentId.ofRepoId(payment.getC_Payment_ID()),
						payment -> payment));
	}

	private Amount extractStatementLineAmt(@NonNull final I_C_BankStatementLine record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		return moneyService.toAmount(record.getStmtAmt(), currencyId);
	}

	private void assertPaymentToLinkIsStillValid(@NonNull final PaymentToLink paymentToLink)
	{
		final I_C_Payment payment = getPaymentById(paymentToLink.getPaymentId());
		final DocStatus docStatus = DocStatus.ofCode(payment.getDocStatus());
		if (!docStatus.isCompleted())
		{
			throw new AdempiereException("Payment shall be completed: " + payment.getDocumentNo());
		}

		if (payment.isReconciled())
		{
			throw new AdempiereException("Payment " + payment.getDocumentNo() + " was already reconciled");
		}

		final PaymentDirection paymentDirection = extractPaymentDirection(payment);
		final Amount payAmt = extractPayAmt(payment);
		final Amount statementLineAmtExpected = paymentDirection.convertPayAmtToStatementAmt(payAmt);
		if (!paymentToLink.getStatementLineAmt().isEqualByComparingTo(statementLineAmtExpected))
		{
			throw new AdempiereException("Cannot partially reconcile the payment " + payment.getDocumentNo());
		}

	}

	private static PaymentDirection extractPaymentDirection(final I_C_Payment payment)
	{
		return PaymentDirection.ofReceiptFlag(payment.isReceipt());
	}

	private Amount extractPayAmt(@NonNull final I_C_Payment payment)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(payment.getC_Currency_ID());
		return moneyService.toAmount(payment.getPayAmt(), currencyId);
	}

	private void markAsMultiplePayments(final I_C_BankStatementLine bankStatementLine)
	{
		bankStatementLine.setIsReconciled(true);
		bankStatementLine.setIsMultiplePaymentOrInvoice(true);
		bankStatementLine.setIsMultiplePayment(true);

		bankStatementLine.setC_BPartner_ID(-1);
		bankStatementLine.setC_Payment_ID(-1);
		bankStatementLine.setC_Invoice_ID(-1);
		bankStatementDAO.save(bankStatementLine);
	}

	private void createBankStatementLineRef(
			@NonNull final PaymentToLink paymentToLink,
			@NonNull final I_C_BankStatementLine bankStatementLine,
			final int lineNo)
	{
		final PaymentId paymentId = paymentToLink.getPaymentId();
		final I_C_Payment payment = getPaymentById(paymentId);

		final BankStatementLineReference lineRef = bankStatementDAO.createBankStatementLineRef(BankStatementLineRefCreateRequest.builder()
				.bankStatementId(BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID()))
				.bankStatementLineId(BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID()))
				.processed(bankStatementLine.isProcessed())
				//
				.orgId(OrgId.ofRepoId(bankStatementLine.getAD_Org_ID()))
				//
				.lineNo(lineNo)
				//
				.paymentId(paymentId)
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				.invoiceId(InvoiceId.ofRepoIdOrNull(payment.getC_Invoice_ID()))
				//
				.trxAmt(moneyService.toMoney(paymentToLink.getStatementLineAmt()))
				//
				.build());

		//
		// Mark payment as reconciled
		if (doReconcilePayments)
		{
			paymentBL.markReconciledAndSave(
					payment,
					PaymentReconcileReference.bankStatementLineRef(lineRef.getId()));
		}

		//
		linkedPayments.add(PaymentLinkResult.builder()
				.bankStatementId(lineRef.getBankStatementId())
				.bankStatementLineId(lineRef.getBankStatementLineId())
				.bankStatementLineRefId(lineRef.getBankStatementLineRefId())
				.paymentId(lineRef.getPaymentId())
				.statementTrxAmt(lineRef.getTrxAmt())
				.paymentMarkedAsReconciled(payment.isReconciled())
				.build());
	}
}
