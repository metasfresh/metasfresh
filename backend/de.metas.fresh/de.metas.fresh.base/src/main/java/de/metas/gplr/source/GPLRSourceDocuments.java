package de.metas.gplr.source;

import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;

import java.util.List;

@Value
@Builder
public class GPLRSourceDocuments
{
	@NonNull I_C_Invoice salesInvoice;
	@NonNull I_C_Order salesOrder;

	@NonNull List<I_M_InOut> shipments;

	public InvoiceId getSalesInvoiceId() {return InvoiceId.ofRepoId(salesInvoice.getC_Invoice_ID());}
}
