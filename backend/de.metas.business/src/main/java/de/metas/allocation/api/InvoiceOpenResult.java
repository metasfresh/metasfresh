package de.metas.allocation.api;

import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.service.impl.InvoiceTotal;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InvoiceOpenResult
{
	@NonNull InvoiceDocBaseType invoiceDocBaseType;
	@NonNull InvoiceTotal invoiceGrandTotal;
	@NonNull InvoiceTotal allocatedAmt;
	@NonNull InvoiceTotal openAmt;
	boolean hasAllocations;

	public boolean isFullyAllocated() {return openAmt.isZero();}

	public boolean isCreditMemo() {return invoiceDocBaseType.isCreditMemo();}
}
