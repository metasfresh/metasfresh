package de.metas.pos.invoice_settlement.card;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * The cashier's release of a card settlement, see {@link POSInvoiceCardSettlementService#release}. It has two steps:
 * first without confirmation; if the result says {@code confirmationRequired}, the till asks the cashier and sends the
 * confirmation with the status the prompt showed.
 */
@Value
@Builder
public class POSInvoiceCardSettlementReleaseRequest
{
	@NonNull POSInvoiceCardSettlementId id;
	@NonNull UserId cashierId;

	/**
	 * Whether the cashier confirmed that the card was not charged (the second step).
	 */
	boolean confirmNotCharged;

	/**
	 * The status the confirmation prompt showed; required if {@link #confirmNotCharged}. The settlement is released only
	 * if its status is still this one.
	 */
	@Nullable POSInvoiceCardSettlementStatus expectedStatus;
}
