package de.metas.gplr.source;

import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SourceDocuments
{
	@NonNull SourceInvoice salesInvoice;
	@NonNull SourceOrder salesOrder;

	@NonNull List<SourceShipment> shipments;

	@NonNull List<SourceOrder> purchaseOrders;

	public InvoiceId getSalesInvoiceId() {return salesInvoice.getId();}
}
