package de.metas.ui.web.invoice.match_receipt_costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.invoice.InvoiceLineId;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.order.costs.invoice.CreateMatchInvoiceRequest;
import de.metas.process.ProcessPreconditionsResolution;

public class ReceiptCostsView_CreateMatchInv extends ReceiptCostsViewBasedProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	protected String doIt()
	{
		final ImmutableList<ReceiptCostRow> rows = getSelectedRows();
		final ImmutableSet<InOutCostId> inoutCostIds = rows.stream().map(ReceiptCostRow::getInoutCostId).collect(ImmutableSet.toImmutableSet());

		final ReceiptCostsView view = getView();
		final InvoiceLineId invoiceLineId = view.getInvoiceLineId();

		orderCostService.createMatchInvoice(CreateMatchInvoiceRequest.builder()
				.invoiceLineId(invoiceLineId)
				.inoutCostIds(inoutCostIds)
				.build());

		view.invalidateAll();

		return MSG_OK;
	}
}
