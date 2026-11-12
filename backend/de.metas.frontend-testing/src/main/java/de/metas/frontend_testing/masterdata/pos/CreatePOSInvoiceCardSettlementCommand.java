package de.metas.frontend_testing.masterdata.pos;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalOpenJournalRequest;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementBooking;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Creates a {@code C_POS_InvoiceCardSettlement} of an invoice, in one of the statuses a back-office test needs.
 * <ul>
 * <li>{@code SU} and {@code CR}: the settlement is booked by the production booking
 * ({@link POSInvoiceCardSettlementBooking#book}) from a successful SumUp transaction, so it carries the payment, the
 * allocation and the cash journal line the real flow creates. {@code CR} needs an invoice that is already paid
 * (the real flow: the customer paid it in the meantime), so request an {@code SU} settlement of the same invoice
 * first. The terminal's cash journal is opened if it is not open.</li>
 * <li>{@code BE}, {@code FA}, {@code CA}, {@code AB}: the status, error message, attempts and abandoned flag are set
 * as the real flow sets them when it ends in that status without a payment.</li>
 * <li>A settlement is created as {@code PE} first, which the unique blocking index of the table refuses next to a blocking
 * ({@code BE}) one of the same invoice: request the non-blocking ones ({@code SU}, {@code CR}, {@code AB}, {@code FA}, {@code CA}) of an
 * invoice before its {@code BE} one.</li>
 * <li>{@code PE}, {@code UN}, {@code AP} are refused: they are in-flight states, and the background SumUp sweep
 * (the poller of every app server) books or changes such a settlement within seconds, so a test could not rely on them.
 * {@code RF} is refused: it only results from the back office running "mark as refunded" on a {@code CR} settlement.</li>
 * </ul>
 */
@Builder
public class CreatePOSInvoiceCardSettlementCommand
{
	private static final ImmutableSet<POSInvoiceCardSettlementStatus> BOOKED_STATUSES = ImmutableSet.of(
			POSInvoiceCardSettlementStatus.SUCCESSFUL,
			POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING);
	private static final ImmutableSet<POSInvoiceCardSettlementStatus> SET_STATUSES = ImmutableSet.of(
			POSInvoiceCardSettlementStatus.BOOKING_ERROR,
			POSInvoiceCardSettlementStatus.FAILED,
			POSInvoiceCardSettlementStatus.CANCELLED,
			POSInvoiceCardSettlementStatus.ABANDONED);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final POSInvoiceCardSettlementBooking booking;
	@NonNull private final POSService posService;
	@NonNull private final SumUpTransactionRepository sumUpTransactionRepository;
	@NonNull private final SumUpConfigRepository sumUpConfigRepository;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPOSInvoiceCardSettlementRequest request;
	@NonNull private final Identifier identifier;

	public JsonPOSInvoiceCardSettlementResponse execute()
	{
		final POSInvoiceCardSettlementStatus status = request.getStatus();
		if (!BOOKED_STATUSES.contains(status) && !SET_STATUSES.contains(status))
		{
			throw new AdempiereException("A card settlement cannot be created in status " + status.getCode() + ": "
					+ "PE, UN and AP are in-flight states that the background SumUp sweep books or changes, and RF only results from marking a CR settlement refunded. "
					+ "Supported: " + BOOKED_STATUSES + ", " + SET_STATUSES);
		}

		final InvoiceId invoiceId = context.getId(request.getInvoice(), InvoiceId.class);
		final I_C_Invoice invoice = InterfaceWrapperHelper.load(invoiceId, I_C_Invoice.class);
		final UserId cashierId = context.getId(request.getCashier(), UserId.class);
		final POSTerminalId posTerminalId = context.getId(request.getPosTerminal(), POSTerminalId.class);

		// committed at once: the booking below reads it in its own transactions
		final POSInvoiceCardSettlement created = trxManager.callInNewTrx(() -> repository.create(
				MasterdataContext.ORG_ID,
				posTerminalId,
				invoiceId,
				BPartnerId.ofRepoId(invoice.getC_BPartner_ID()),
				cashierId,
				Money.of(invoice.getGrandTotal(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()))));

		final POSInvoiceCardSettlement settlement = BOOKED_STATUSES.contains(status)
				? book(created, cashierId)
				: trxManager.callInNewTrx(() -> setStatus(created));

		return JsonPOSInvoiceCardSettlementResponse.builder()
				.id(settlement.getId().getRepoId())
				.build();
	}

	private POSInvoiceCardSettlement setStatus(final POSInvoiceCardSettlement created)
	{
		return repository.save(created.toBuilder()
				.status(request.getStatus())
				.isAbandoned(Boolean.TRUE.equals(request.getIsAbandoned()))
				.note(request.getNote())
				.errorMsg(request.getErrorMsg())
				.bookingAttempts(request.getBookingAttempts() != null ? request.getBookingAttempts() : 0)
				.build());
	}

	private POSInvoiceCardSettlement book(final POSInvoiceCardSettlement created, final UserId cashierId)
	{
		final POSTerminal terminal = posService.getPOSTerminalById(created.getPosTerminalId());
		final SumUpConfigId sumUpConfigId = terminal.getPaymentProcessorConfig() != null ? terminal.getPaymentProcessorConfig().getSumUpConfigId() : null;
		if (sumUpConfigId == null)
		{
			throw new AdempiereException("The POS terminal has no SumUp payment processor (create it with the CARD payment method): " + terminal.getName());
		}

		if (!terminal.isCashJournalOpen())
		{
			posService.openCashJournal(POSTerminalOpenJournalRequest.builder()
					.posTerminalId(terminal.getId())
					.cashierId(cashierId)
					.dateTrx(SystemTime.asInstant())
					.cashBeginningBalance(BigDecimal.ZERO)
					.build());
		}

		// what the checkout of the real flow saves: the successful charge, linked back to the settlement
		final String sumUpId = UUID.randomUUID().toString();
		trxManager.runInNewTrx(() -> sumUpTransactionRepository.saveNew(SumUpTransaction.builder()
				.configId(sumUpConfigId)
				.externalId(SumUpTransactionExternalId.ofString("E2E-" + sumUpId))
				.clientTransactionId(SumUpClientTransactionId.ofString("E2E-" + sumUpId))
				.merchantCode(sumUpConfigRepository.getById(sumUpConfigId).getMerchantCode())
				.timestamp(SystemTime.asInstant())
				.status(SumUpTransactionStatus.SUCCESSFUL)
				.amount(Amount.of(created.getAmount().toBigDecimal(), currencyRepository.getCurrencyCodeById(created.getAmount().getCurrencyId())))
				.clientAndOrgId(created.getClientAndOrgId())
				.posRef(SumUpPOSRef.builder().posInvoiceCardSettlementId(created.getId().getRepoId()).build())
				.build()));

		booking.book(created.getId(), true);

		final POSInvoiceCardSettlement booked = repository.getById(created.getId());
		if (booked.getStatus() != request.getStatus())
		{
			throw new AdempiereException("Booking the card settlement " + identifier + " ended in status " + booked.getStatus().getCode()
					+ " instead of the requested " + request.getStatus().getCode()
					+ (booked.getErrorMsg() != null ? " (" + booked.getErrorMsg() + ")" : "")
					+ ". CR needs an invoice that is already paid, e.g. by an SU settlement of the same invoice requested before.");
		}

		if (request.getNote() == null)
		{
			return booked;
		}
		return trxManager.callInNewTrx(() -> repository.save(booked.toBuilder().note(request.getNote()).build()));
	}
}
