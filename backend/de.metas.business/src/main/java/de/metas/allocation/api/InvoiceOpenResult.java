package de.metas.allocation.api;

import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoicePaymentStatus;
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

	public boolean isCreditMemo() {return invoiceDocBaseType.isCreditMemo();}

	@NonNull
	public InvoicePaymentStatus getPaymentStatus()
	{
		if (!hasAllocations)
		{
			return InvoicePaymentStatus.NOT_PAID;
		}
		else if (openAmt.isZero())
		{
			return InvoicePaymentStatus.FULLY_PAID;
		}
		else
		{
			return InvoicePaymentStatus.PARTIALLY_PAID;
		}
	}

}
