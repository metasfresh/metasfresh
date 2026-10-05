package de.metas.pos.rest_api.json;

import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

/**
 * {@code POST /api/v2/pos/returns}: a customer handing goods back at the till, no HUs, no prior sales order.
 * The cashier is taken from the logged-in session, never from the request body (mirrors every other POS REST
 * endpoint — e.g. {@code /cashWithdrawal}).
 */
@Value
@Builder
@Jacksonized
public class JsonPOSReturnRequest
{
	@NonNull POSTerminalId posTerminalId;

	/** Identifies this return so a retried request does not create a second one. */
	@NonNull UUID externalId;

	@NonNull List<JsonPOSReturnLine> lines;
}
