package de.metas.pos.rest_api.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.invoice.InvoiceId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * The state of one card settlement of an open invoice, as the till needs it. Local state only, no SumUp call.
 */
@Value
@Builder
@Jacksonized
@JsonAutoDetect(isGetterVisibility = JsonAutoDetect.Visibility.NONE) // boolean is-getters would add a second property ("abandoned"); the boolean fields name theirs explicitly
public class JsonPOSInvoiceCardSettlement
{
	@NonNull POSInvoiceCardSettlementId id;
	@NonNull InvoiceId invoiceId;
	@NonNull String documentNo;

	/** Amount charged to the card, in the currency {@link #currencyCode}. */
	@NonNull BigDecimal amount;

	/** The part of the card payment allocated to the invoice; zero while there is no payment. */
	@NonNull BigDecimal allocatedAmount;

	@NonNull String currencyCode;

	/** Status code: PE, UN, AP, BE, SU, CR, RF, FA, CA or AB. */
	@NonNull String status;

	@JsonProperty("isAbandoned") boolean isAbandoned;

	/**
	 * ISO-8601: from this instant the cashier may release the settlement.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NonNull Instant releasableAt;

	/** Whether SumUp could not be asked for the current status in the last check. */
	@JsonProperty("checkFailed") boolean checkFailed;

	public static JsonPOSInvoiceCardSettlement of(
			@NonNull final POSInvoiceCardSettlement settlement,
			@NonNull final String documentNo,
			@NonNull final BigDecimal allocatedAmount,
			@NonNull final String currencyCode,
			@NonNull final Instant releasableAt,
			final boolean checkFailed)
	{
		return builder()
				.id(settlement.getId())
				.invoiceId(settlement.getInvoiceId())
				.documentNo(documentNo)
				.amount(settlement.getAmount().toBigDecimal())
				.allocatedAmount(allocatedAmount)
				.currencyCode(currencyCode)
				.status(settlement.getStatus().getCode())
				.isAbandoned(settlement.isAbandoned())
				.releasableAt(releasableAt)
				.checkFailed(checkFailed)
				.build();
	}
}
