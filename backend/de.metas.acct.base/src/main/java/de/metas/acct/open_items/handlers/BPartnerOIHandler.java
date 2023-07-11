package de.metas.acct.open_items.handlers;

import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.PaymentAllocationLineId;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;

import java.util.Optional;

abstract class BPartnerOIHandler implements FAOpenItemsHandler
{
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);

	@Override
	public Optional<FAOpenItemTrxInfo> computeTrxInfo(final FAOpenItemTrxInfoComputeRequest request)
	{
		final String tableName = request.getTableName();
		if (I_C_Invoice.Table_Name.equals(tableName))
		{
			return Optional.of(computeTrxInfoFromInvoice(InvoiceId.ofRepoId(request.getRecordId())));
		}
		else if (I_C_AllocationHdr.Table_Name.equals(tableName))
		{
			return extractMatchingKeyFromAllocation(PaymentAllocationLineId.ofRepoId(request.getRecordId(), request.getLineId()));
		}
		else
		{
			return Optional.empty();
		}
	}

	private FAOpenItemTrxInfo computeTrxInfoFromInvoice(final InvoiceId invoiceId)
	{
		return FAOpenItemTrxInfo.openItem(FAOpenItemKey.ofTableAndRecord(I_C_Invoice.Table_Name, invoiceId.getRepoId()));
	}

	private Optional<FAOpenItemTrxInfo> extractMatchingKeyFromAllocation(final PaymentAllocationLineId paymentAllocationLineId)
	{
		// TODO handle the case when we have invoice-to-invoice allocation
		return allocationBL.getInvoiceId(paymentAllocationLineId)
				.map(invoiceId -> FAOpenItemTrxInfo.clearing(FAOpenItemKey.ofTableAndRecord(I_C_Invoice.Table_Name, invoiceId.getRepoId())));
	}

}
