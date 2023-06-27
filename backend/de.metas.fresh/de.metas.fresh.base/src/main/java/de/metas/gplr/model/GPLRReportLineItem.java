package de.metas.gplr.model;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportLineItem
{
	// 042 - Document - “Sales Order” or “Purchase Order” (see AC8)
	@Nullable String documentNo;

	// 043 - Line
	@Nullable String lineCode;

	// 044 - Description
	@Nullable String description;

	// 045 - Qty - Invoiced quantity of material on line
	// 046 - UOM - Invoiced unit of measure of quantity of material on line
	@Nullable Quantity qty;

	// 047 - Unit Price - Moving average for line (per batch/material/warehouse), the one used for goods issue accounting transaction
	@Nullable Amount priceFC;

	// 048 - LC Amt - Line Item local currency Amount (see AC8)
	// 049 - FC Amt - Line Item Foreign Currency Amount (see AC8)
	@Nullable Amount amountLC;
	@Nullable Amount amountFC;

	// 050 - Batch - Batch No
	@Nullable String batchNo;

	@Nullable
	public CurrencyCode getForeignCurrency() {return Amount.getCommonCurrencyCodeOfAll(priceFC, amountFC);}

	@Nullable
	public CurrencyCode getLocalCurrency() {return Amount.getCommonCurrencyCodeOfAll(amountLC);}
}
