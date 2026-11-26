package de.metas.pos.rest_api.json;

import de.metas.pos.returns.POSReturnResult;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

/**
 * Outcome of {@code POST /api/v2/pos/returns}: the credit memo the return produced, and the till's cash
 * journal after the refund's {@code CASH_INOUT} line was added (both already settled — a POS return is paid
 * out on the spot, never left open).
 */
@Value
@Builder
@Jacksonized
public class JsonPOSReturnResponse
{
	@NonNull String creditMemoDocumentNo;

	/** Total refunded, in the terminal's currency. */
	@NonNull BigDecimal refundAmount;

	@NonNull JsonCashJournalSummary journal;

	public static JsonPOSReturnResponse of(@NonNull final POSReturnResult result, @NonNull final JsonContext jsonContext)
	{
		return JsonPOSReturnResponse.builder()
				.creditMemoDocumentNo(result.getCreditMemoDocumentNo())
				.refundAmount(result.getRefundAmount().toBigDecimal())
				.journal(JsonCashJournalSummary.of(result.getJournal(), jsonContext))
				.build();
	}
}
