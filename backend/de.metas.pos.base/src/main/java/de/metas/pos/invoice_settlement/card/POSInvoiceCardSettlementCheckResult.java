package de.metas.pos.invoice_settlement.card;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Result of {@link POSInvoiceCardSettlementService#check}.
 */
@Value
@Builder
public class POSInvoiceCardSettlementCheckResult
{
	/**
	 * The settlement as it is after the check.
	 */
	@NonNull POSInvoiceCardSettlement settlement;

	/**
	 * Whether SumUp could not be asked for the current status of the settlement's transaction; the settlement was then
	 * reconciled with the transaction's last known state.
	 */
	boolean checkFailed;
}
