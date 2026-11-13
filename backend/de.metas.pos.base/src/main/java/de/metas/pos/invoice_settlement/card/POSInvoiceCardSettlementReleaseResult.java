package de.metas.pos.invoice_settlement.card;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Result of {@link POSInvoiceCardSettlementService#release}.
 */
@Value
@Builder
public class POSInvoiceCardSettlementReleaseResult
{
	/**
	 * The settlement as it is after the request: released (ABANDONED), booked or rejected by SumUp meanwhile, or unchanged.
	 */
	@NonNull POSInvoiceCardSettlement settlement;

	/**
	 * Whether the settlement is still waiting / unknown after the re-check, so the cashier must confirm that the card was
	 * not charged before it is released; the confirmation sends {@link #settlement}'s status as expected status.
	 */
	boolean confirmationRequired;

	/**
	 * Whether SumUp could not be asked for the current status during the re-check (see
	 * {@link POSInvoiceCardSettlementCheckResult#isCheckFailed()}).
	 */
	boolean checkFailed;

	/**
	 * Why the release was refused (nothing was changed), to be shown to the cashier; null if not refused.
	 */
	@Nullable ITranslatableString refusedMessage;

	/**
	 * The AD_Message of {@link #refusedMessage}, for callers that react to the reason; null if not refused.
	 */
	@Nullable AdMessageKey refusedMessageKey;

	public boolean isRefused() {return refusedMessageKey != null;}
}
