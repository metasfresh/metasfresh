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

import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkRequest;
import de.metas.banking.payment.BankStatementLineMultiPaymentLinkResult;
import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.banking.payment.PaymentLinkResult;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocate;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationService;
import de.metas.banking.service.IBankStatementBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.i18n.AdMessageKey;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
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
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BankStatementPaymentBL implements IBankStatementPaymentBL
{
	private static final AdMessageKey MSG_ORDER_AMOUNT_MISMATCH = AdMessageKey.of("ERR_BANKSTATEMENT_ORDER_AMOUNT_MISMATCH");
	private static final AdMessageKey MSG_ORDER_ALREADY_PAID = AdMessageKey.of("ERR_BANKSTATEMENT_ORDER_ALREADY_PAID");
	private static final AdMessageKey MSG_ORDER_NOT_WAITING = AdMessageKey.of("ERR_BANKSTATEMENT_ORDER_NOT_WAITING");
	private static final AdMessageKey MSG_ORDER_NOT_A_PREPAYMENT = AdMessageKey.of("ERR_BANKSTATEMENT_ORDER_NOT_A_PREPAYMENT");

	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IBankStatementListenerService bankStatementListenersService = Services.get(IBankStatementListenerService.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IBankStatementBL bankStatementBL;
	@NonNull private final MoneyService moneyService;
	@NonNull private final PaymentAllocationService paymentAllocationService;

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
											   .limit(QueryLimit.ofInt(limit))
											   .docStatus(DocStatus.Completed)
											   .reconciled(false)
											   .direction(expectedPaymentDirection)
											   .bpartnerId(bpartnerId)
											   .payAmt(expectedPaymentAmount)
											   .orderId(extractOrderIdToPay(bankStatementLine, trxAmt))
											   .excludePaymentIds(excludePaymentIds)
											   .build());
	}

	/**
	 * @return the order that the payment of this line has to carry, i.e. the order of an incoming line without invoice;
	 * null for a debit, 0.00 or invoice line (these never get the order).
	 */
	@Nullable
	private static OrderId extractOrderIdToPay(@NonNull final I_C_BankStatementLine bankStatementLine, @NonNull final Money trxAmt)
	{
		return bankStatementLine.getC_Invoice_ID() <= 0 && trxAmt.signum() > 0
				? OrderId.ofRepoIdOrNull(bankStatementLine.getC_Order_ID())
				: null;
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
		final LocalDate dateTrx = TimeUtil.asLocalDate(
				bankStatementLine.getValutaDate(),
				orgDAO.getTimeZone(orgId));
		final BankAccountId orgBankAccountId = BankAccountId.ofRepoId(bankStatement.getC_BP_BankAccount_ID());

		final Money trxAmt = extractTrxAmt(bankStatementLine);
		final boolean inboundPayment = trxAmt.signum() >= 0;
		final Money payAmount = trxAmt.negateIf(!inboundPayment);

		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(bankStatementLine.getC_Invoice_ID());

		final OrderId orderId = extractOrderIdToPay(bankStatementLine, trxAmt);

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
				.dateAcct(dateTrx)
				.dateTrx(dateTrx)
				.tenderType(tenderType)
				.paymentCurrencyContext(bankStatementBL.getPaymentCurrencyContext(bankStatementLine));

		if (invoiceId != null)
		{
			final ZonedDateTime evaluationDate = bankStatementLine
					.getValutaDate()
					.toLocalDateTime()
					.atZone(orgDAO.getTimeZone(orgId));

			final InvoiceToAllocate invoiceToAllocate = getInvoiceToAllocate(payAmount.getCurrencyId(), invoiceId, evaluationDate).orElse(null);
			
			if (invoiceToAllocate != null)
			{
				// Skonto, or the payment bonus, or both: the one that the paid amount matches
				paymentAllocationService.matchDeductions(
								invoiceToAllocate,
								payAmount.toAmount(moneyService::getCurrencyCodeByCurrencyId),
								PaymentDirection.ofInboundPaymentFlag(inboundPayment))
						.ifPresent(matched -> {
							paymentBuilder.discountAmt(matched.getDiscountAmt().toBigDecimal());
							paymentBuilder.paymentBonusAmt(matched.getBonusAmt().toBigDecimal());
						});
			}

			paymentBuilder.invoiceId(invoiceId);
		}
		else if (orderId != null)
		{
			// invoice matching comes first; a debit, 0.00 or invoice line never gives its payment the order (see extractOrderIdToPay)
			assertOrderCanBePaid(orderId, trxAmt);
			paymentBuilder.orderId(orderId);
		}

		return paymentBuilder.createDraft(); // note: don't complete the payment now, else onComplete interceptors might link this payment to a different Bank Statement Line.
	}

	/**
	 * Full prepayment of an active sales prepay order only: the order must be a prepay order, still wait for payment, no payment may carry it yet,
	 * and the line amount and currency must equal the order total.
	 *
	 * @throws AdempiereException if not, so completing the bank statement fails visibly and the order stays waiting
	 */
	private void assertOrderCanBePaid(@NonNull final OrderId orderId, @NonNull final Money trxAmt)
	{
		final I_C_Order order = orderDAO.getById(orderId);

		// the lookups and the "Auftrag" dropdown only offer such orders, but the field can also be filled by other means
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		if (!order.isActive()
				|| !order.isSOTrx()
				|| docTypeId == null
				|| !docTypeDAO.getDocBaseAndSubTypeById(docTypeId).getDocSubType().isPrepay())
		{
			throw new AdempiereException(MSG_ORDER_NOT_A_PREPAYMENT, order.getDocumentNo());
		}

		// name the payment that carries the order, if there is one: the one the order points to, else an active payment pointing to the order
		final PaymentId orderPaymentId = PaymentId.ofRepoIdOrNull(order.getC_Payment_ID());
		final PaymentId carryingPaymentId = orderPaymentId != null
				? orderPaymentId
				: paymentBL.getActivePaymentIdCarryingOrder(orderId).orElse(null);
		if (carryingPaymentId != null)
		{
			throw new AdempiereException(MSG_ORDER_ALREADY_PAID, order.getDocumentNo(), paymentBL.getById(carryingPaymentId).getDocumentNo());
		}
		if (!DocStatus.ofCode(order.getDocStatus()).isWaitingForPayment())
		{
			throw new AdempiereException(MSG_ORDER_NOT_WAITING, order.getDocumentNo());
		}

		final CurrencyId orderCurrencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());
		if (order.getGrandTotal().compareTo(trxAmt.toBigDecimal()) != 0 || !CurrencyId.equals(orderCurrencyId, trxAmt.getCurrencyId()))
		{
			throw new AdempiereException(MSG_ORDER_AMOUNT_MISMATCH,
										 order.getDocumentNo(),
										 order.getGrandTotal().toPlainString() + " " + moneyService.getCurrencyCodeByCurrencyId(orderCurrencyId).toThreeLetterCode());
		}
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

	@NonNull
	private Optional<InvoiceToAllocate> getInvoiceToAllocate(
			@NonNull final CurrencyId paymentCurrencyId,
			@NonNull final InvoiceId invoiceId,
			@NonNull final ZonedDateTime evaluationDate)
	{
		final InvoiceToAllocateQuery query = InvoiceToAllocateQuery.builder()
				.currencyId(paymentCurrencyId)
				.evaluationDate(evaluationDate)
				.onlyInvoiceId(invoiceId)
				.build();

		return paymentAllocationService.retrieveInvoicesToAllocate(query)
				.stream()
				.filter(invoiceToAllocate -> {
					// just to be sure
					final InvoiceId invId = invoiceToAllocate.getInvoiceId();
					if (invId == null)
					{
						return false;
					}

					return invId.equals(invoiceId);
				})
				.findFirst();
	}
}
