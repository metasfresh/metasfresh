package de.metas.pos.invoice_settlement.card;

import de.metas.common.util.time.SystemTime;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.money.Money;
import de.metas.notification.INotificationBL;
import de.metas.notification.NotificationGroupName;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Books an approved card settlement in two steps, each in its own transaction: step A creates the payment
 * ({@link #bookPaymentIfApproved}), step B journals and allocates it ({@link #completeBooking}). Each step re-reads
 * the settlement under its row lock and acts only on the status it expects, so a repeated or concurrent delivery of
 * the same approval books exactly once.
 */
@Component
@RequiredArgsConstructor
public class POSInvoiceCardSettlementBooking
{
	/**
	 * {@code ErrorMsg} of a settlement whose charge SumUp reports as approved and already refunded before it was booked.
	 */
	public static final String ERRORMSG_RefundedAtSumUp = "refunded at SumUp";

	private static final NotificationGroupName NOTIFICATION_GROUP_NAME = NotificationGroupName.of("de.metas.pos.InvoiceCardSettlement");
	private static final AdMessageKey MSG_CreditRefundPending = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.CreditRefundPending");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final POSInvoiceSettlementService invoiceSettlementService;
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posCashJournalService;
	@NonNull private final SumUpTransactionRepository sumUpTransactionRepository;

	/**
	 * Step A, under the settlement lock: if the settlement's SumUp transaction is approved, creates the credit-card
	 * payment and sets APPROVED; a charge already refunded at SumUp is never booked.
	 */
	public void bookPaymentIfApproved(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.runInNewTrx(() -> bookPaymentIfApprovedInTrx(id));
	}

	private void bookPaymentIfApprovedInTrx(@NonNull final POSInvoiceCardSettlementId id)
	{
		final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
		final SumUpTransaction sumUpTrx = findSumUpTransaction(id).orElse(null);
		if (sumUpTrx == null || !SumUpTransactionStatus.SUCCESSFUL.equals(sumUpTrx.getStatus()))
		{
			return;
		}

		final POSInvoiceCardSettlementStatus status = settlement.getStatus();
		if (sumUpTrx.isRefunded())
		{
			// a refund also fires a status event that still says SUCCESSFUL; an abandoned settlement stays as it is
			if (status == POSInvoiceCardSettlementStatus.PENDING || status == POSInvoiceCardSettlementStatus.UNKNOWN)
			{
				repository.save(settlement.toBuilder()
						.status(POSInvoiceCardSettlementStatus.FAILED)
						.errorMsg(ERRORMSG_RefundedAtSumUp)
						.sumUpTransactionId(getLinkedOrResolvedSumUpTransactionId(settlement))
						.build());
			}
			return;
		}

		if (status == POSInvoiceCardSettlementStatus.PENDING
				|| status == POSInvoiceCardSettlementStatus.UNKNOWN
				|| status == POSInvoiceCardSettlementStatus.ABANDONED)
		{
			final PaymentId paymentId = createPayment(settlement);
			repository.save(settlement.toBuilder()
					.status(POSInvoiceCardSettlementStatus.APPROVED) // IsAbandoned is kept
					.paymentId(paymentId)
					.sumUpTransactionId(getLinkedOrResolvedSumUpTransactionId(settlement))
					.build());
		}
		else if (status == POSInvoiceCardSettlementStatus.FAILED || status == POSInvoiceCardSettlementStatus.CANCELLED)
		{
			// the reader took money for a settlement metasfresh had already given up on: back office must reconcile it
			errorManager.createIssue(new AdempiereException("SumUp reports the card charge as successful, but the card settlement was already " + status + "; nothing was booked")
					.setParameter("SumUpClientTransactionId", sumUpTrx.getClientTransactionId())
					.setParameter("amount", sumUpTrx.getAmount())
					.setRecord(id.toRecordRef()));
		}
	}

	@NonNull
	private PaymentId createPayment(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(settlement.getPosTerminalId());
		final Money amount = settlement.getAmount();
		final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
				.adOrgId(terminal.getOrgId())
				.orgBankAccountId(terminal.getCashbookId())
				.bpartnerId(settlement.getBpartnerId())
				.payAmt(amount.toBigDecimal())
				.currencyId(amount.getCurrencyId())
				.tenderType(TenderType.CreditCard)
				.dateTrx(SystemTime.asInstant())
				.createAndProcess();
		return PaymentId.ofRepoId(payment.getC_Payment_ID());
	}

	/**
	 * Step B, under the invoice lock and then the settlement lock, only while APPROVED, all in one transaction:
	 * allocates the payment to the invoice up to its open amount (nothing if the invoice is no longer open), adds the
	 * card line to the terminal's open cash journal, and sets SUCCESSFUL, or CREDIT_REFUND_PENDING (plus a
	 * notification to the back office) if part of the payment stays unallocated. Throws, leaving the settlement
	 * APPROVED, if the invoice is still open but the payment could not be allocated.
	 */
	public void completeBooking(@NonNull final POSInvoiceCardSettlementId id)
	{
		// cheap pre-check without locks; re-checked below under the locks
		if (repository.getById(id).getStatus() != POSInvoiceCardSettlementStatus.APPROVED)
		{
			return;
		}

		trxManager.runInNewTrx(() -> completeBookingInTrx(id));
	}

	private void completeBookingInTrx(@NonNull final POSInvoiceCardSettlementId id)
	{
		// the settlement's invoice never changes, so it can be read before taking the locks
		final I_C_Invoice invoice = invoiceSettlementService.lockInvoice(repository.getById(id).getInvoiceId());
		final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
		if (settlement.getStatus() != POSInvoiceCardSettlementStatus.APPROVED)
		{
			return;
		}

		final PaymentId paymentId = settlement.getPaymentId();
		if (paymentId == null)
		{
			throw new AdempiereException("APPROVED card settlement without C_Payment_ID").setRecord(id.toRecordRef());
		}

		final Money amount = settlement.getAmount();

		// an invoice that is no longer open (paid or reversed meanwhile) takes nothing; one that is still open takes the
		// payment up to its open amount. Whatever stays unallocated is customer credit that back office has to refund.
		final boolean isInvoiceOpen = invoiceSettlementService.isStillOpen(invoice);
		if (isInvoiceOpen)
		{
			invoiceSettlementService.allocateUpToOpenAmt(invoice, paymentBL.getById(paymentId));
		}
		final boolean isCreditRefundPending = amount.subtract(getAllocatedAmount(settlement)).signum() > 0;

		if (isInvoiceOpen && isCreditRefundPending && invoiceSettlementService.isStillOpen(invoice))
		{
			// the allocation silently did nothing (e.g. the payment was allocated elsewhere meanwhile): the invoice is
			// still open and the payment is not used up, so this is no customer credit but a booking that did not work
			throw new AdempiereException("Card payment could not be allocated to the invoice that is still open")
					.setParameter("openAmt", invoiceSettlementService.getOpenAmt(invoice))
					.setParameter("amount", amount)
					.setParameter("allocatedAmount", getAllocatedAmount(settlement))
					.setRecord(id.toRecordRef());
		}

		final POSTerminal terminal = posTerminalService.getPOSTerminalById(settlement.getPosTerminalId());
		final String description = invoiceSettlementService.getJournalDescription(invoice);
		posCashJournalService.changeJournalById(
				terminal.getCashJournalIdNotNull(),
				cashJournal -> cashJournal.addCardSettlementPayment(amount, settlement.getCashierId(), id, description));

		repository.save(settlement.toBuilder()
				.status(isCreditRefundPending ? POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING : POSInvoiceCardSettlementStatus.SUCCESSFUL)
				.errorMsg(null)
				.build());

		if (isCreditRefundPending)
		{
			notifyCreditRefundPending(settlement, paymentId, invoice);
		}
	}

	/**
	 * Tells the back office, after the booking transaction committed, that a card payment waits to be refunded.
	 */
	private void notifyCreditRefundPending(
			@NonNull final POSInvoiceCardSettlement settlement,
			@NonNull final PaymentId paymentId,
			@NonNull final I_C_Invoice invoice)
	{
		notificationBL.sendAfterCommit(UserNotificationRequest.builder()
				.notificationGroupName(NOTIFICATION_GROUP_NAME)
				.recipient(Recipient.allRolesContainingGroup(NOTIFICATION_GROUP_NAME))
				.contentADMessage(MSG_CreditRefundPending)
				.contentADMessageParam(TableRecordReference.of(I_C_Payment.Table_Name, paymentId.getRepoId()))
				.contentADMessageParam(TableRecordReference.of(I_C_Invoice.Table_Name, invoice.getC_Invoice_ID()))
				.targetAction(UserNotificationRequest.TargetRecordAction.of(settlement.getId().toRecordRef()))
				.build());
	}

	/**
	 * @return the part of the settlement's card payment that is allocated to the settlement's invoice; zero while the
	 * settlement has no payment
	 */
	@NonNull
	Money getAllocatedAmount(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return invoiceSettlementService.getAllocatedAmt(settlement.getInvoiceId(), settlement.getPaymentId(), settlement.getAmount().getCurrencyId());
	}

	/**
	 * @return the settlement's SumUp transaction as stored locally, if the checkout created one
	 */
	@NonNull
	Optional<SumUpTransaction> findSumUpTransaction(@NonNull final POSInvoiceCardSettlementId id)
	{
		final SumUpTransactionId sumUpTransactionId = resolveSumUpTransactionId(id);
		if (sumUpTransactionId == null)
		{
			return Optional.empty();
		}
		return sumUpTransactionRepository.stream(SumUpTransactionQuery.ofLocalId(sumUpTransactionId)).findFirst();
	}

	/**
	 * @return the SumUp transaction the settlement is already linked to, else the one that references it (if any)
	 */
	@Nullable
	SumUpTransactionId getLinkedOrResolvedSumUpTransactionId(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return settlement.getSumUpTransactionId() != null
				? settlement.getSumUpTransactionId()
				: resolveSumUpTransactionId(settlement.getId());
	}

	/**
	 * Resolves the settlement's SumUp transaction via the back-reference {@code SUMUP_Transaction.C_POS_InvoiceCardSettlement_ID},
	 * which the checkout sets when it saves the transaction.
	 */
	@Nullable
	private SumUpTransactionId resolveSumUpTransactionId(@NonNull final POSInvoiceCardSettlementId id)
	{
		return sumUpTransactionRepository.findIdByPOSRef(SumUpPOSRef.builder()
				.posInvoiceCardSettlementId(id.getRepoId())
				.build());
	}
}
