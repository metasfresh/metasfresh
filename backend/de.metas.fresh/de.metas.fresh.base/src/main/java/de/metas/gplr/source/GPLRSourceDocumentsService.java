package de.metas.gplr.source;

import de.metas.inout.IInOutBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GPLRSourceDocumentsService
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	public GPLRSourceDocuments getByInvoiceId(final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		if (invoice.isSOTrx())
		{
			final OrderId salesOrderId = OrderId.ofRepoIdOrNull(invoice.getC_Order_ID());
			if (salesOrderId == null)
			{
				throw new AdempiereException("Cannot determine sales order for sales invoice " + invoice.getDocumentNo());
			}

			final I_C_Order salesOrder = orderBL.getById(salesOrderId);

			final List<I_M_InOut> shipments = inOutBL.getByOrderId(salesOrderId);

			return GPLRSourceDocuments.builder()
					.salesInvoice(invoice)
					.salesOrder(salesOrder)
					.shipments(shipments)
					.build();
		}
		else
		{
			throw new UnsupportedOperationException("starting from purchase invoice not yet implemented"); // TODO
		}
	}

}
