package de.metas.allocation.api;

import de.metas.invoice.InvoiceDocBaseType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InvoiceOpenResult
{
	@NonNull InvoiceDocBaseType invoiceDocBaseType;
	@NonNull MoneyWithInvoiceFlags invoiceGrandTotal;
	@NonNull MoneyWithInvoiceFlags allocatedAmt;
	@NonNull MoneyWithInvoiceFlags openAmt;
	boolean hasAllocations;

	public boolean isFullyAllocated() {return openAmt.isZero();}

	public boolean isCreditMemo() {return invoiceDocBaseType.isCreditMemo();}
}
