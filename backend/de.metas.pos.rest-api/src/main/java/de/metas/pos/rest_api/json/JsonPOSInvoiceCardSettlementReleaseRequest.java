package de.metas.pos.rest_api.json;

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

	/** Status code the confirmation prompt showed; required if {@link #confirmNotCharged}. */
	@Nullable String expectedStatus;
}
