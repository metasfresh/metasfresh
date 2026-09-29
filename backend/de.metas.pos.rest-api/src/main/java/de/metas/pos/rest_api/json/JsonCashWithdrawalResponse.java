package de.metas.pos.rest_api.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.metas.pos.withdrawal.POSCashWithdrawalResult;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Outcome of a POS cash withdrawal; carries the data printed on the withdrawal slip.
 */
@Value
@Builder
@Jacksonized
public class JsonCashWithdrawalResponse
{
	@NonNull String documentNo;

	/**
	 * The chosen category's name; also the text printed on the underlying journal line.
	 */
	@NonNull String category;

	@NonNull BigDecimal amount;
	/**
	 * ISO-8601 (e.g. {@code 2026-09-24T10:15:30Z}), independent of whether the object mapper writes dates as timestamps.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NonNull Instant date;
	@NonNull String cashier;
	@NonNull String terminal;
	@NonNull JsonCashJournalSummary journal;

	public static JsonCashWithdrawalResponse of(@NonNull final POSCashWithdrawalResult result, @NonNull final JsonContext jsonContext)
	{
		return JsonCashWithdrawalResponse.builder()
				.documentNo(result.getDocumentNo())
				.category(result.getChargeName())
				.amount(result.getAmount().toBigDecimal())
				.date(result.getDateTrx())
				.cashier(result.getCashierName())
				.terminal(result.getTerminalName())
				.journal(JsonCashJournalSummary.of(result.getJournal(), jsonContext))
				.build();
	}
}
