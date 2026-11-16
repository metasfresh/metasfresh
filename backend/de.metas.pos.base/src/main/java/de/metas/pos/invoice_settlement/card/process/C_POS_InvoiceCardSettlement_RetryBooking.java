package de.metas.pos.invoice_settlement.card.process;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementService;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

/**
 * Back office retries the booking of a card settlement, see {@link POSInvoiceCardSettlementService#retryBooking}. If
 * the booking did not complete, the process fails with the settlement's error, so back office does not take it as done;
 * if it failed with a transient error, the process says that the booking is retried automatically.
 */
public class C_POS_InvoiceCardSettlement_RetryBooking extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_RetryBookingNotCompleted = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.RetryBookingNotCompleted");
	private static final AdMessageKey MSG_RetryBookingWillRetry = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.RetryBookingWillRetry");

	private final POSInvoiceCardSettlementService settlementService = SpringContextHolder.instance.getBean(POSInvoiceCardSettlementService.class);

	private static final ImmutableSet<POSInvoiceCardSettlementStatus> APPLICABLE_STATUSES = ImmutableSet.of(
			POSInvoiceCardSettlementStatus.BOOKING_ERROR,
			POSInvoiceCardSettlementStatus.APPROVED);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return POSInvoiceCardSettlementProcessPreconditions.acceptIfStatusIn(context, settlementService, APPLICABLE_STATUSES);
	}

	@Override
	@RunOutOfTrx // the booking runs its own transactions
	protected String doIt()
	{
		return retryBooking(POSInvoiceCardSettlementId.ofRepoId(getRecord_ID()));
	}

	/**
	 * @return the process summary: {@link #MSG_OK} if booked or ended otherwise, {@code RetryBookingWillRetry} if the
	 * background sweep retries the booking
	 * @throws AdempiereException {@code RetryBookingNotCompleted} if the booking failed again (a booking error, or an
	 * approved settlement still waiting with an error the sweep does not retry)
	 */
	@VisibleForTesting
	String retryBooking(@NonNull final POSInvoiceCardSettlementId id)
	{
		final POSInvoiceCardSettlement settlement = settlementService.retryBooking(id);
		if (settlementService.isAwaitingAutomaticBooking(settlement))
		{
			// a transient error: the background sweep retries the booking, so back office need not do anything
			return "@" + MSG_RetryBookingWillRetry.toAD_Message() + "@";
		}
		if (isBookingNotCompleted(settlement))
		{
			// the booking handled its error itself (AD_Issue, status); back office must not see "OK"
			throw new AdempiereException(MSG_RetryBookingNotCompleted, StringUtils.trimBlankToOptional(settlement.getErrorMsg()).orElse(""))
					.markAsUserValidationError()
					.setRecord(settlement.getId().toRecordRef());
		}
		return MSG_OK;
	}

	/**
	 * @return whether the retry ended as a booking error again, or still waits with an error the sweep does not retry
	 * (e.g. no open cash journal); only asked if the settlement does not await automatic booking
	 */
	private static boolean isBookingNotCompleted(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final POSInvoiceCardSettlementStatus status = settlement.getStatus();
		return status == POSInvoiceCardSettlementStatus.BOOKING_ERROR
				|| (status == POSInvoiceCardSettlementStatus.APPROVED && settlement.getErrorMsg() != null);
	}
}
