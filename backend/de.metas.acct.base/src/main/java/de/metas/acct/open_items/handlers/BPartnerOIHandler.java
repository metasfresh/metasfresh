package de.metas.acct.open_items.handlers;

import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.PaymentAllocationLineId;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.compiere.acct.FactLine;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;

import java.util.Optional;

abstract class BPartnerOIHandler implements FAOpenItemsHandler
{
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);

	@Override
	public Optional<FAOpenItemKey> extractMatchingKey(final FactLine line)
	{
		final FAOpenItemKey openItemKey = FAOpenItemKey.ofNullableString(line.getOpenItemKey());
		if (openItemKey != null)
		{
			return Optional.of(openItemKey);
		}

		final String tableName = TableIdsCache.instance.getTableName(AdTableId.ofRepoId(line.getAD_Table_ID()));
		if (I_C_Invoice.Table_Name.equals(tableName))
		{
			return Optional.of(extractMatchingKeyFromInvoice(InvoiceId.ofRepoId(line.getRecord_ID())));
		}
		else if (I_C_AllocationHdr.Table_Name.equals(tableName))
		{
			return extractMatchingKeyFromAllocation(PaymentAllocationLineId.ofRepoId(line.getRecord_ID(), line.getLine_ID()));
		}
		else
		{
			return Optional.empty();
		}
	}

	private FAOpenItemKey extractMatchingKeyFromInvoice(final InvoiceId invoiceId)
	{
		return createOpenItemKey(invoiceId);
	}

	private Optional<FAOpenItemKey> extractMatchingKeyFromAllocation(final PaymentAllocationLineId paymentAllocationLineId)
	{
		// TODO handle the case when we have invoice-to-invoice allocation
		return allocationBL.getInvoiceId(paymentAllocationLineId)
				.map(this::createOpenItemKey);
	}

	private FAOpenItemKey createOpenItemKey(final InvoiceId invoiceId)
	{
		return FAOpenItemKey.ofString(I_C_Invoice.Table_Name + "#" + invoiceId.getRepoId());
	}
}
