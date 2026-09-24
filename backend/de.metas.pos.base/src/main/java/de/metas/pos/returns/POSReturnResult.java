package de.metas.pos.returns;

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.pos.POSCashJournal;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/**
 * Outcome of {@link POSReturnService#createReturn(POSReturnRequest)}: the customer-return material document
 * that received the goods, the invoice candidates priced at the till price for the credit, the credit memo
 * generated from them, and its cash settlement (outbound payment + cash-journal refund line).
 */
@Value
@Builder
public class POSReturnResult
{
	@NonNull InOutId returnInOutId;
	@NonNull List<InvoiceCandidateId> invoiceCandidateIds;

	@NonNull InvoiceId creditMemoId;
	@NonNull String creditMemoDocumentNo;

	/** Total refunded, in the terminal's currency (the credit memo's own {@code GrandTotal}). */
	@NonNull Money refundAmount;

	/** Completed outbound cash payment settling the credit memo. */
	@NonNull PaymentId paymentId;

	/** The till's cash journal after the refund's {@code CASH_INOUT} line was added. */
	@NonNull POSCashJournal journal;
}
