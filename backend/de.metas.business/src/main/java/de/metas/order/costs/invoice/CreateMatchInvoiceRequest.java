package de.metas.order.costs.invoice;

import com.google.common.collect.ImmutableSet;
import de.metas.invoice.InvoiceLineId;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class CreateMatchInvoiceRequest
{
	@NonNull InvoiceLineId invoiceLineId;
	@NonNull ImmutableSet<InOutCostId> inoutCostIds;

	@Builder
	private CreateMatchInvoiceRequest(
			@NonNull final InvoiceLineId invoiceLineId,
			@NonNull final ImmutableSet<InOutCostId> inoutCostIds)
	{
		Check.assumeNotEmpty(inoutCostIds, "inoutCostIds not empty");
		this.invoiceLineId = invoiceLineId;
		this.inoutCostIds = inoutCostIds;
	}
}
