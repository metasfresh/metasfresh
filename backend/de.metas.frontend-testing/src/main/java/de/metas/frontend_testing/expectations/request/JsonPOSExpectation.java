package de.metas.frontend_testing.expectations.request;

import de.metas.pos.POSCashJournalLineType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Expectation for a single POS product-return flow (AC4/AC4b/AC4c/AC4e), and — the {@code invoices} field only
 * — for a plain invoice settlement (AC2/AC2b, consumed by Task 4.4; unrelated to a return, needs neither
 * {@code externalId} nor {@code posTerminal}).
 *
 * <p>Consumer-side JSON shape:
 * <pre>
 * Backend.expect({
 *   pos: {
 *     externalId: '3fae2b19-...',      // the same UUID the client POSTed to /api/v2/pos/returns
 *     posTerminal: 'T1',               // masterdata identifier — needed only by cashJournalLines
 *     returns: [{ product: 'P1', qty: '0.300 KGM', warehouse: 'quality' }],
 *     creditMemos: [{ lines: [{ product: 'P1', qty: '0.300 KGM', price: '15.50', taxRate: 7 }] }],
 *     cashJournalLines: [{ type: 'CASH_INOUT', amount: '-4.65' }],
 *     invoices: [{ invoice: 'INV1', isPaid: true, hasAllocatedPayment: true }]
 *   }
 * });
 * </pre>
 *
 * <p>A POS return document carries no direct FK to its POS terminal (see
 * {@code AssertExpectationsCommandServices#getPOSReturnInOutIdByExternalId}), so {@code externalId} — the SAME
 * value the client generated for the return and POSTed to the REST endpoint — is what scopes {@code returns} and
 * {@code creditMemos} to the one return this flow just created. {@code posTerminal} is separate because a
 * cash-journal line has no such externalId to key off; the terminal it belongs to is the only handle.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSExpectation
{
	@Nullable String externalId;
	@Nullable String posTerminal;

	@Nullable List<JsonPOSReturnLineExpectation> returns;
	@Nullable List<JsonPOSCreditMemoExpectation> creditMemos;
	@Nullable List<JsonPOSInvoiceExpectation> invoices;
	@Nullable List<JsonCashJournalLineExpectation> cashJournalLines;

	/** One line of the return's own {@code M_InOut} — the goods received into the quality-return warehouse. */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSReturnLineExpectation
	{
		@Nullable String product;
		@Nullable QtyAndUOMString qty;
		@Nullable String warehouse;
	}

	/** The credit memo the return produced (AC4b) — currently always exactly one per return. */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSCreditMemoExpectation
	{
		@Nullable List<JsonPOSCreditMemoLineExpectation> lines;
	}

	/** One line of the credit memo — each returned product priced at the till's price, with ITS OWN tax rate (AC4b). */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSCreditMemoLineExpectation
	{
		@Nullable String product;
		@Nullable QtyAndUOMString qty;
		@Nullable BigDecimal price;

		/** Percentage, e.g. {@code 7} for 7 %. */
		@Nullable BigDecimal taxRate;
	}

	/** An arbitrary invoice (T4: a plain sales invoice settled in cash), by its masterdata/context identifier. */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSInvoiceExpectation
	{
		@NonNull String invoice;
		@Nullable Boolean isPaid;
		@Nullable Boolean hasAllocatedPayment;
	}

	/** One line of the till's cash journal (e.g. the return's refund, {@code CASH_INOUT} negative). */
	@Value
	@Builder
	@Jacksonized
	public static class JsonCashJournalLineExpectation
	{
		@Nullable POSCashJournalLineType type;
		@Nullable BigDecimal amount;
	}
}
