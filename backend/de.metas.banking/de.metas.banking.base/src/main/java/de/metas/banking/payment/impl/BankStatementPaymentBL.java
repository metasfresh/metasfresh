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

package de.metas.banking.payment.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkResult;
import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.banking.payment.PaymentLinkResult;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentQuery;
import de.metas.payment.api.PaymentReconcileReference;
import de.metas.util.Services;
import lombok.NonNull;

@Component
public class BankStatementPaymentBL implements IBankStatementPaymentBL
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IBankStatementListenerService bankStatementListenersService = Services.get(IBankStatementListenerService.class);
	private final IBankStatementBL bankStatementBL;
	private final MoneyService moneyService;

	public BankStatementPaymentBL(
			@NonNull final IBankStatementBL bankStatementBL,
			@NonNull final MoneyService moneyService)
	{
		this.bankStatementBL = bankStatementBL;
		this.moneyService = moneyService;
	}

	@Override
	public void findOrCreateSinglePaymentAndLinkIfPossible(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine,
			@NonNull final Set<PaymentId> excludePaymentIds)
	{
		// Bank Statement Line is already reconciled => do nothing
		if (bankStatementLine.isReconciled())
		{
			return;
		}

		// if BPartner is not set, we cannot match it or generate a new payment
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bankStatementLine.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		final Set<PaymentId> eligiblePaymentIds = findEligiblePaymentIds(
				bankStatementLine,
				bpartnerId,
				excludePaymentIds,
				2 // limit
		);
		// noinspection StatementWithEmptyBody
		if (eligiblePaymentIds.size() > 1)
		{
			// Don't create a new Payment and don't link any of the existing payments if there are multiple payments found.
			// The user must fix this case manually by choosing the correct Payment
		}
		else if (eligiblePaymentIds.size() == 1)
		{
			final PaymentId paymentId = eligiblePaymentIds.iterator().next();
			linkSinglePayment(bankStatement, bankStatementLine, paymentId);
		}
		else
		{
			createSinglePaymentAndLink(bankStatement, bankStatementLine);
		}
	}

	@Override
	public Set<PaymentId> findEligiblePaymentIds(
			@NonNull final I_C_BankStatementLine bankStatementLine,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Set<PaymentId> excludePaymentIds,
			final int limit)
	{
		final Money trxAmt = extractTrxAmt(bankStatementLine);
		final PaymentDirection expectedPaymentDirection = PaymentDirection.ofBankStatementAmount(trxAmt);
		final Money expectedPaymentAmount = expectedPaymentDirection.convertStatementAmtToPayAmt(trxAmt);

		return paymentBL.getPaymentIds(PaymentQuery.builder()
				.limit(limit)
				.docStatus(DocStatus.Completed)
				.reconciled(false)
				.direction(expectedPaymentDirection)
				.bpartnerId(bpartnerId)
				.payAmt(expectedPaymentAmount)
				.excludePaymentIds(excludePaymentIds)
				.build());
	}

	private static Money extractTrxAmt(final I_C_BankStatementLine line)
	{
		return Money.of(line.getTrxAmt(), CurrencyId.ofRepoId(line.getC_Currency_ID()));
	}

	@Override
	public void createSinglePaymentAndLink(final I_C_BankStatement bankStatement, final I_C_BankStatementLine bankStatementLine)
	{
		final I_C_Payment payment = createPayment(bankStatement, bankStatementLine);
		linkSinglePayment(bankStatement, bankStatementLine, payment);
		Services.get(IDocumentBL.class).processEx(payment, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private I_C_Payment createPayment(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bankStatementLine.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			throw new AdempiereException("Bank statement line's BPartner is not set");
		}

		// final CurrencyId currencyId = CurrencyId.ofRepoId(line.getC_Currency_ID());
		final OrgId orgId = OrgId.ofRepoId(bankStatementLine.getAD_Org_ID());
		final LocalDate acctLineDate = TimeUtil.asLocalDate(bankStatementLine.getDateAcct());
		final BankAccountId orgBankAccountId = BankAccountId.ofRepoId(bankStatement.getC_BP_BankAccount_ID());

		final Money trxAmt = extractTrxAmt(bankStatementLine);
		final boolean inboundPayment = trxAmt.signum() >= 0;
		final Money payAmount = trxAmt.negateIf(!inboundPayment);

		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(bankStatementLine.getC_Invoice_ID());

		final TenderType tenderType = paymentBL.getTenderType(orgBankAccountId);

		final DefaultPaymentBuilder paymentBuilder = inboundPayment
				? paymentBL.newInboundReceiptBuilder()
				: paymentBL.newOutboundPaymentBuilder();

		paymentBuilder
				.adOrgId(orgId)
				.bpartnerId(bpartnerId)
				.orgBankAccountId(orgBankAccountId)
				.currencyId(payAmount.getCurrencyId())
				.payAmt(payAmount.toBigDecimal())
				.dateAcct(acctLineDate)
				.dateTrx(acctLineDate) // Note: DateTrx should be the same as Line.DateAcct, and not Line.StatementDate.
				.tenderType(tenderType);

		if (invoiceId != null)
		{
			paymentBuilder.invoiceId(invoiceId);
		}

		return paymentBuilder.createDraft(); // note: don't complete the payment now, else onComplete interceptors might link this payment to a different Bank Statement Line.
	}

	@Override
	public void linkSinglePayment(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine,
			@NonNull final PaymentId paymentId)
	{
		final I_C_Payment payment = paymentBL.getById(paymentId);
		linkSinglePayment(bankStatement, bankStatementLine, payment);
	}

	@Override
	public void linkSinglePayment(
			@NonNull final I_C_BankStatement bankStatement,
			@NonNull final I_C_BankStatementLine bankStatementLine,
			@NonNull final I_C_Payment payment)
	{
		// a payment is already linked
		if (bankStatementLine.isReconciled())
		{
			throw new AdempiereException("Linking payment to an already reconciled bank statement line is not allowed");
		}

		bankStatementLine.setIsReconciled(true);
		bankStatementLine.setIsMultiplePaymentOrInvoice(false);
		bankStatementLine.setIsMultiplePayment(false);

		bankStatementLine.setC_Payment_ID(payment.getC_Payment_ID());
		bankStatementLine.setC_BPartner_ID(payment.getC_BPartner_ID());
		bankStatementLine.setC_Invoice_ID(payment.getC_Invoice_ID());

		//
		final PaymentDirection paymentDirection = extractPaymentDirection(payment);
		final Money payAmt = extractPayAmt(payment);
		final Money trxAmt = paymentDirection.convertPayAmtToStatementAmt(payAmt);
		// NOTE: don't touch the StmtAmt!
		bankStatementLine.setC_Currency_ID(trxAmt.getCurrencyId().getRepoId());
		bankStatementLine.setTrxAmt(trxAmt.toBigDecimal());

		final BigDecimal bankFeeAmt = BankStatementLineAmounts.of(bankStatementLine)
				.addDifferenceToBankFeeAmt()
				.getBankFeeAmt();
		bankStatementLine.setBankFeeAmt(bankFeeAmt);

		bankStatementDAO.save(bankStatementLine);

		//
		// Reconcile payment if bank statement is processed
		final DocStatus bankStatementDocStatus = DocStatus.ofCode(bankStatement.getDocStatus());
		if (bankStatementDocStatus.isCompleted())
		{
			final PaymentReconcileReference reconcileRef = PaymentReconcileReference.bankStatementLine(
					BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID()),
					BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID()));
			paymentBL.markReconciledAndSave(payment, reconcileRef);
		}

		bankStatementListenersService.firePaymentLinked(PaymentLinkResult.builder()
				.bankStatementId(BankStatementId.ofRepoId(bankStatementLine.getC_BankStatement_ID()))
				.bankStatementLineId(BankStatementLineId.ofRepoId(bankStatementLine.getC_BankStatementLine_ID()))
				.bankStatementLineRefId(null)
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.statementTrxAmt(trxAmt)
				.paymentMarkedAsReconciled(payment.isReconciled())
				.build());
	}

	private static PaymentDirection extractPaymentDirection(final I_C_Payment payment)
	{
		return PaymentDirection.ofReceiptFlag(payment.isReceipt());
	}

	private static Money extractPayAmt(@NonNull final I_C_Payment payment)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(payment.getC_Currency_ID());
		return Money.of(payment.getPayAmt(), currencyId);
	}

	@Override
	public BankStatementLineMultiPaymentLinkResult linkMultiPayments(@NonNull final BankStatementLineMultiPaymentLinkRequest request)
	{
		return BankStatementLineMultiPaymentLinkCommand.builder()
				.bankStatementBL(bankStatementBL)
				.bankStatementDAO(bankStatementDAO)
				.paymentBL(paymentBL)
				.bankStatementListenersService(bankStatementListenersService)
				.moneyService(moneyService)
				//
				.request(request)
				//
				.build()
				.execute();
	}
}
