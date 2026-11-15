package de.metas.frontend_testing.masterdata.pos;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * A card settlement of an invoice ({@code C_POS_InvoiceCardSettlement}) in a given status, for tests of the back-office
 * window. The business partner, the amount and the currency are those of the invoice. See
 * {@link CreatePOSInvoiceCardSettlementCommand} for the statuses that can be requested.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSInvoiceCardSettlementRequest
{
	/** Identifier of an already-created {@code posTerminals} entry; for SU / CR it needs the CARD payment method. */
	@NonNull Identifier posTerminal;

	/** Identifier of an already-created {@code invoices} entry. */
	@NonNull Identifier invoice;

	/** Identifier of an already-created {@code login} user. */
	@NonNull Identifier cashier;

	@NonNull POSInvoiceCardSettlementStatus status;

	@Nullable Boolean isAbandoned;
	@Nullable String note;
	@Nullable String errorMsg;
	@Nullable Integer bookingAttempts;
}
