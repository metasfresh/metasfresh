package de.metas.invoicecandidate.api;

import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
public class InvoiceCandidateInOutLineToUpdate
{
	private final I_C_InvoiceCandidate_InOutLine iciol;

	@Getter
	private final StockQtyAndUOMQty qtyInvoiced;

	public InvoiceCandidateInOutLineToUpdate(
			@NonNull final I_C_InvoiceCandidate_InOutLine iciol,
			@NonNull final StockQtyAndUOMQty qtyInvoiced)
	{
		this.iciol = iciol;
		this.qtyInvoiced = qtyInvoiced;
	}

	public I_C_InvoiceCandidate_InOutLine getC_InvoiceCandidate_InOutLine()
	{
		return iciol;
	}
}
