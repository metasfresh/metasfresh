package de.metas.pos.returns;

import de.metas.inout.InOutId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/**
 * Outcome of {@link POSReturnService#createReturn(POSReturnRequest)}: the customer-return material document
 * that received the goods, and the invoice candidates priced at the till price for the credit.
 */
@Value
@Builder
public class POSReturnResult
{
	@NonNull InOutId returnInOutId;
	@NonNull List<InvoiceCandidateId> invoiceCandidateIds;
}
