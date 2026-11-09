package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import de.metas.pos.POSTerminalService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService.SettleableInvoice;
import de.metas.pos.payment_gateway.POSPaymentProcessRequest;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Settles an existing, still-open sales invoice by card on the till's SumUp reader. Every attempt is a persisted
 * {@link POSInvoiceCardSettlement} with a small state machine (DESIGN, "State machine"); every status change is made
 * under the settlement's row lock and conditional on the status it expects.
 * <p>
 * Lock order (DESIGN decision 7): invoice before settlement, wherever both are needed.
 */
@Service
@RequiredArgsConstructor
public class POSInvoiceCardSettlementService
{
	private static final int ERRORMSG_MAX_LENGTH = 4000;
	private static final String BLOCKING_INDEX_NAME = "c_pos_invoicecardsettlement_blocking_uq";
	private static final AdMessageKey MSG_CardPaymentStillOpen = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final POSInvoiceSettlementService invoiceSettlementService;
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSPaymentProcessorService paymentProcessorService;
	@NonNull private final MoneyService moneyService;
	@NonNull private final SumUpTransactionRepository sumUpTransactionRepository;

	@NonNull
	public POSInvoiceCardSettlement getById(@NonNull final POSInvoiceCardSettlementId id)
	{
		return repository.getById(id);
	}

	/**
	 * @return the unfinished settlement of the invoice that blocks any further payment of it, if any
	 */
	@NonNull
	public Optional<POSInvoiceCardSettlement> getBlockingByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return repository.findBlockingByInvoiceId(invoiceId);
	}

	/**
	 * Starts a card settlement of the invoice's full open amount (DESIGN decision 5):
	 * <ol>
	 * <li>in one transaction: lock the invoice, apply the cash guards, check that no blocking settlement exists and
	 * that a journal is open, insert the settlement as PENDING, commit;</li>
	 * <li>send the checkout to the card reader outside any transaction;</li>
	 * <li>apply the result under the settlement lock ({@link #applySendResult}).</li>
	 * </ol>
	 *
	 * @throws AdempiereException {@code CardPaymentStillOpen} — a card settlement of the invoice is unfinished;
	 * {@code NoLongerOpen} / {@code NotEligibleForSettlement} / {@code WrongOrg} / {@code CurrencyMismatch} — as for
	 * cash, see {@link POSInvoiceSettlementService#lockAndAssertSettleable}; {@code CashJournalNotOpen} — the terminal
	 * has no open cash journal. In all these cases nothing is persisted and nothing is sent.
	 */
	@NonNull
	public POSInvoiceCardSettlement start(@NonNull final POSInvoiceCardSettlementStartRequest request)
	{
		// fail before anything is persisted: the checkout must be sent outside any transaction
		trxManager.assertThreadInheritedTrxNotExists();

		final StartedSettlement started = callStartInTrx(request);

		final POSPaymentProcessResponse response = send(started);

		return applySendResult(started.getSettlement().getId(), response);
	}

	@NonNull
	private StartedSettlement callStartInTrx(@NonNull final POSInvoiceCardSettlementStartRequest request)
	{
		try
		{
			return trxManager.callInNewTrx(() -> startInTrx(request));
		}
		catch (final DBUniqueConstraintException ex)
		{
			// defense in depth: the invoice lock and the blocking check normally refuse first; if the blocking index
			// still fires, report it like the check does (the index's own error code would be a generic one)
			if (isBlockingIndexViolation(ex))
			{
				throw new AdempiereException(MSG_CardPaymentStillOpen)
						.setParameter("C_Invoice_ID", request.getInvoiceId());
			}
			throw ex;
		}
	}

	private static boolean isBlockingIndexViolation(@NonNull final DBUniqueConstraintException ex)
	{
		final Throwable cause = ex.getCause();
		final String msg = (cause != null ? cause.getMessage() : ex.getMessage());
		return msg != null && msg.toLowerCase().contains(BLOCKING_INDEX_NAME);
	}

	@NonNull
	private StartedSettlement startInTrx(@NonNull final POSInvoiceCardSettlementStartRequest request)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());

		// invoice lock first, then the guards (cash parity + the blocking check)
		final SettleableInvoice settleableInvoice = invoiceSettlementService.lockAndAssertSettleable(terminal, request.getInvoiceId());

		final POSTerminalPaymentProcessorConfig paymentProcessorConfig = terminal.getPaymentProcessorConfigNotNull();
		terminal.getCashJournalIdNotNull(); // refuse without an open journal, as the cash path does (the journal line is booked later)

		final OrgId orgId = terminal.getOrgId();
		final POSInvoiceCardSettlement settlement = repository.create(
				orgId,
				request.getPosTerminalId(),
				request.getInvoiceId(),
				BPartnerId.ofRepoId(settleableInvoice.getInvoice().getC_BPartner_ID()),
				request.getCashierId(),
				settleableInvoice.getOpen());

		return new StartedSettlement(settlement, paymentProcessorConfig, ClientAndOrgId.ofClientAndOrg(Env.getClientId(), orgId));
	}

	/**
	 * Sends the checkout, with the settlement id as description (to match it manually in the SumUp dashboard).
	 * Runs outside any transaction: the call can take long and must not hold locks.
	 */
	@NonNull
	private POSPaymentProcessResponse send(@NonNull final StartedSettlement started)
	{
		final POSInvoiceCardSettlement settlement = started.getSettlement();
		try
		{
			return paymentProcessorService.processPayment(POSPaymentProcessRequest.builder()
					.paymentProcessorConfig(started.getPaymentProcessorConfig())
					.clientAndOrgId(started.getClientAndOrgId())
					.posInvoiceCardSettlementId(settlement.getId())
					.description(String.valueOf(settlement.getId().getRepoId()))
					.amount(settlement.getAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
					.build());
		}
		catch (final Exception ex)
		{
			// the processors report their failures in the response; this is something unexpected around the call,
			// so whether the reader got the request is unknown
			final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex)
					.setRecord(TableRecordReference.of(I_C_POS_InvoiceCardSettlement.Table_Name, settlement.getId().getRepoId()));
			return POSPaymentProcessResponse.errorIndeterminate(
					started.getPaymentProcessorConfig(),
					AdempiereException.extractMessage(metasfreshException),
					errorManager.createIssue(metasfreshException));
		}
	}

	/**
	 * Applies the send result under the settlement lock (DESIGN decision 5, step 3, with ruling R2):
	 * <ul>
	 * <li>a definite rejection (an error response from SumUp, or a configuration error before any call) → FAILED, if
	 * the status is still PENDING, or UNKNOWN without a SumUp transaction (an orphan flip);</li>
	 * <li>an indeterminate failure (timeout / IO / 5xx) → UNKNOWN, if the status is still PENDING;</li>
	 * <li>the SumUp transaction, if there is one, is linked in any status.</li>
	 * </ul>
	 */
	@NonNull
	private POSInvoiceCardSettlement applySendResult(
			@NonNull final POSInvoiceCardSettlementId settlementId,
			@NonNull final POSPaymentProcessResponse response)
	{
		return trxManager.callInNewTrx(() -> {
			final POSInvoiceCardSettlement settlementBefore = repository.getByIdForUpdate(settlementId);
			POSInvoiceCardSettlement settlement = settlementBefore;

			final SumUpTransactionId sumUpTransactionId = settlement.getSumUpTransactionId() == null
					? retrieveSumUpTransactionId(settlementId)
					: null;
			if (sumUpTransactionId != null)
			{
				settlement = settlement.toBuilder().sumUpTransactionId(sumUpTransactionId).build();
			}

			if (response.getStatus() == POSPaymentProcessingStatus.FAILED)
			{
				final boolean isLinked = settlement.getSumUpTransactionId() != null;
				if (response.isIndeterminate())
				{
					if (settlement.getStatus() == POSInvoiceCardSettlementStatus.PENDING)
					{
						settlement = settlement.toBuilder()
								.status(POSInvoiceCardSettlementStatus.UNKNOWN)
								.errorMsg(truncateErrorMsg(response.getErrorMessage()))
								.build();
					}
				}
				else if (settlement.getStatus() == POSInvoiceCardSettlementStatus.PENDING
						|| (settlement.getStatus() == POSInvoiceCardSettlementStatus.UNKNOWN && !isLinked))
				{
					settlement = settlement.toBuilder()
							.status(POSInvoiceCardSettlementStatus.FAILED)
							.errorMsg(truncateErrorMsg(response.getErrorMessage()))
							.build();
				}
			}

			return settlement.equals(settlementBefore) ? settlementBefore : repository.save(settlement);
		});
	}

	/**
	 * Resolves the settlement's SumUp transaction via the back-reference
	 * {@code SUMUP_Transaction.C_POS_InvoiceCardSettlement_ID}, which the checkout sets when it saves the transaction.
	 */
	@Nullable
	private SumUpTransactionId retrieveSumUpTransactionId(@NonNull final POSInvoiceCardSettlementId settlementId)
	{
		return sumUpTransactionRepository.findIdByPOSRef(SumUpPOSRef.builder()
				.posInvoiceCardSettlementId(settlementId.getRepoId())
				.build());
	}

	@Nullable
	private static String truncateErrorMsg(@Nullable final String errorMsg)
	{
		return errorMsg != null && errorMsg.length() > ERRORMSG_MAX_LENGTH ? errorMsg.substring(0, ERRORMSG_MAX_LENGTH) : errorMsg;
	}

	@Value
	private static class StartedSettlement
	{
		@NonNull POSInvoiceCardSettlement settlement;
		@NonNull POSTerminalPaymentProcessorConfig paymentProcessorConfig;
		@NonNull ClientAndOrgId clientAndOrgId;
	}
}
