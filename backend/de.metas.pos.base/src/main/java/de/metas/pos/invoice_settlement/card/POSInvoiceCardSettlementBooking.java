package de.metas.pos.invoice_settlement.card;

import de.metas.common.util.time.SystemTime;
import de.metas.error.IErrorManager;
import de.metas.money.Money;
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

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

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
	 * Step B, under the invoice lock and then the settlement lock, only while APPROVED: allocates the payment to the
	 * invoice, adds the card line to the terminal's open cash journal and sets SUCCESSFUL, all in one transaction.
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
		final Money openAmt = invoiceSettlementService.getOpenAmt(invoice);
		if (!openAmt.isEqualByComparingTo(amount))
		{
			// TODO: the invoice was paid (partly or fully) meanwhile: book the payment as customer credit
			throw new AdempiereException("Booking a card settlement whose invoice was paid meanwhile is not supported yet")
					.setParameter("openAmt", openAmt)
					.setParameter("amount", amount)
					.setRecord(id.toRecordRef());
		}

		invoiceSettlementService.allocateFully(invoice, paymentBL.getById(paymentId));

		final POSTerminal terminal = posTerminalService.getPOSTerminalById(settlement.getPosTerminalId());
		final String description = invoiceSettlementService.getJournalDescription(invoice);
		posCashJournalService.changeJournalById(
				terminal.getCashJournalIdNotNull(),
				cashJournal -> cashJournal.addCardSettlementPayment(amount, settlement.getCashierId(), id, description));

		repository.save(settlement.toBuilder()
				.status(POSInvoiceCardSettlementStatus.SUCCESSFUL)
				.errorMsg(null)
				.build());
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
