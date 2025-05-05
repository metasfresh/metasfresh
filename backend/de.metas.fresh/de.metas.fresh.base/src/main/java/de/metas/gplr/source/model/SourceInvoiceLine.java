package de.metas.gplr.source.model;

import de.metas.currency.Amount;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceInvoiceLine
{
	@NonNull InvoiceAndLineId id;
	@NonNull Amount priceFC;
	@NonNull Amount lineNetAmtFC;
	@NonNull Amount taxAmtFC;
	@NonNull SourceTaxInfo tax;

	@Nullable OrderLineId orderLineId;
}
