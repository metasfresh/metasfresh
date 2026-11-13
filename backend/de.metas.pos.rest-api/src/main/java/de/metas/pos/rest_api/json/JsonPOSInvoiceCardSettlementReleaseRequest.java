package de.metas.pos.rest_api.json;

import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * {@code POST /api/v2/pos/invoices/cardSettlements/{id}/release}: first without confirmation; if the response says
 * {@code confirmationRequired}, again with {@code confirmNotCharged} and the status code the confirmation prompt showed.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSInvoiceCardSettlementReleaseRequest
{
	boolean confirmNotCharged;

	/** The status the confirmation prompt showed (in JSON its status code); required if {@link #confirmNotCharged}. */
	@Nullable POSInvoiceCardSettlementStatus expectedStatus;
}
