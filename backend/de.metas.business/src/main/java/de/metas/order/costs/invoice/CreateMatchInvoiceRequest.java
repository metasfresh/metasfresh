package de.metas.order.costs.invoice;

import com.google.common.collect.ImmutableSet;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class CreateMatchInvoiceRequest
{
	@NonNull InvoiceAndLineId invoiceAndLineId;
	@NonNull ImmutableSet<InOutCostId> inoutCostIds;

	@Builder
	private CreateMatchInvoiceRequest(
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@NonNull final ImmutableSet<InOutCostId> inoutCostIds)
	{
		Check.assumeNotEmpty(inoutCostIds, "inoutCostIds not empty");
		this.invoiceAndLineId = invoiceAndLineId;
		this.inoutCostIds = inoutCostIds;
	}
}
