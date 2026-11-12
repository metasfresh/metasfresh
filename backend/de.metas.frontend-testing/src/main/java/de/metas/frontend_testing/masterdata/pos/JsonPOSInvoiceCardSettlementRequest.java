package de.metas.frontend_testing.masterdata.pos;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * A card settlement of an open invoice ({@code C_POS_InvoiceCardSettlement}) in a given status, for tests of the
 * back-office window. The business partner, the amount and the currency are those of the invoice.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSInvoiceCardSettlementRequest
{
	/** Identifier of an already-created {@code posTerminals} entry. */
	@NonNull Identifier posTerminal;

	/** Identifier of an already-created {@code invoices} entry. */
	@NonNull Identifier invoice;

	/** Identifier of an already-created {@code login} user. */
	@NonNull Identifier cashier;

	/** Defaults to {@code PE} (pending). */
	@Nullable POSInvoiceCardSettlementStatus status;

	@Nullable Boolean isAbandoned;
	@Nullable String note;
	@Nullable String errorMsg;
	@Nullable Integer bookingAttempts;
}
