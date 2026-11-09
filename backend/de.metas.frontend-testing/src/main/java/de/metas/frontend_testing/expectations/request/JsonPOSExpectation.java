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
 * Expectation for a single POS product-return flow, and — the {@code invoices} field only — for a plain
 * invoice settlement, unrelated to a return and needing neither {@code externalId} nor {@code posTerminal}.
 *
 * <p>Consumer-side JSON shape:
 * <pre>
 * Backend.expect({
 *   pos: {
 *     externalId: '3fae2b19-...',      // the same UUID the client POSTed to /api/v2/pos/returns
 *     posTerminal: 'T1',               // masterdata identifier — needed only by cashJournalLines
 *     returns: [{ product: 'P1', qty: '0.300 KGM', warehouse: 'quality' }],
 *     creditMemos: [{ lines: [{ product: 'P1', qty: '0.300 KGM', price: '15.50', taxRate: 7 }] }],
 *     cashJournalLines: [{ type: 'CASH_INOUT', amount: '-4.65' }, { type: 'CARD_PAY', amount: '16.07', cardSettlement: true }],
 *     invoices: [{ invoice: 'INV1', isPaid: true, hasAllocatedPayment: true, allocatedPayments: [{ tenderType: 'X' }] }],
 *     cardSettlements: [{ invoice: 'INV1', status: 'SU' }]
 *   }
 * });
 * </pre>
 *
 * <p>A POS return document carries no direct FK to its POS terminal (see
 * {@code AssertExpectationsCommandServices#getPOSReturnInOutIdByExternalId}), so {@code externalId} — the SAME
 * value the client generated for the return and POSTed to the REST endpoint — is what scopes {@code returns} and
 * {@code creditMemos} to the one return this flow just created. {@code posTerminal} is separate because a
 * cash-journal line has no such externalId to key off; the terminal it belongs to is the only handle.
 *
 * <p><b>{@code cashJournalLines} scoping — read before using it.</b> It asserts the COMPLETE, ordered set of
 * lines on the terminal's CURRENTLY-OPEN cash journal since it was opened — not just the lines a single action
 * (e.g. one return) just added. A caller must therefore open a fresh journal (or otherwise start from a known-
 * empty one) via its own masterdata immediately before the flow under test, and list every line the journal
 * will carry by the time this expectation runs, in the order they were added. Reusing an already-active journal
 * across scenarios, or a flow that adds other lines first (a sale, a withdrawal, an earlier settlement), makes
 * this either false-fail on a size mismatch or — silently — compare against the wrong lines entirely.
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

	/**
	 * The invoice card settlements ({@code C_POS_InvoiceCardSettlement}) of the listed invoices. Per invoice, the
	 * expected statuses are matched in the order stated against the invoice's settlements, oldest first, and their
	 * number must be equal. An entry without {@code status} asserts that the invoice has NO card settlement.
	 * <p>
	 * Unlike {@code cashJournalLines}, an empty list asserts nothing: the settlements belong to invoices, so only
	 * an invoice named in an entry is checked.
	 */
	@Nullable List<JsonPOSCardSettlementExpectation> cardSettlements;

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

	/** The credit memo the return produced — currently always exactly one per return. */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSCreditMemoExpectation
	{
		@Nullable List<JsonPOSCreditMemoLineExpectation> lines;
	}

	/** One line of the credit memo — each returned product priced at the till's price, with ITS OWN tax rate. */
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

	/** An arbitrary invoice — e.g. a plain sales invoice settled in cash — by its masterdata/context identifier. */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSInvoiceExpectation
	{
		@NonNull String invoice;
		@Nullable Boolean isPaid;
		@Nullable Boolean hasAllocatedPayment;

		/**
		 * The invoice's allocated payments, in no particular guaranteed order — asserted as a set: only the
		 * SIZE and, per expected entry, that SOME actual payment has that {@code tenderType} (not a positional
		 * match). {@code null} (the default) skips this check entirely.
		 */
		@Nullable List<JsonPOSAllocatedPaymentExpectation> allocatedPayments;
	}

	/** One payment allocated against an invoice (see {@link JsonPOSInvoiceExpectation#getAllocatedPayments()}). */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSAllocatedPaymentExpectation
	{
		/** {@code C_Payment.TenderType} code, e.g. {@code 'X'} for cash or {@code 'K'} for credit card. */
		@Nullable String tenderType;
	}

	/** One line of the till's cash journal (e.g. the return's refund, {@code CASH_INOUT} negative). */
	@Value
	@Builder
	@Jacksonized
	public static class JsonCashJournalLineExpectation
	{
		@Nullable POSCashJournalLineType type;
		@Nullable BigDecimal amount;

		/**
		 * Whether the line was created by an invoice card settlement ({@code CARD_PAY} of a settled invoice) rather
		 * than by a POS order payment. {@code null} (the default) skips this check.
		 */
		@Nullable Boolean cardSettlement;
	}

	/** One card settlement of an invoice, by the settlement status code ({@code PE}, {@code UN}, {@code AP}, {@code BE}, {@code SU}, {@code CR}, {@code RF}, {@code FA}, {@code CA}, {@code AB}). */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPOSCardSettlementExpectation
	{
		/** Masterdata/context identifier of the invoice. */
		@NonNull String invoice;

		/** Settlement status code; omit it to assert that the invoice has no card settlement at all. */
		@Nullable String status;
	}
}
