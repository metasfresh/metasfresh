package de.metas.gplr.report.model;

import de.metas.currency.Amount;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportCharge
{
	// 051 - Document - PO Number
	@Nullable String purchaseOrderDocumentNo;

	// 052 - Line - Line no
	@Nullable String orderLineNo;

	// 053 - Charge - Name of cost type
	@NonNull String costTypeName;

	// 054 - Payee - SAP Code of cost vendor
	// 055 - Payee Name - Name of cost vendor
	@Nullable GPLRBPartnerName vendor;

	// 056 - LC Amt - Line Item Charge local Amount
	@Nullable Amount amountLC;

	// 057 - FC Amt - Line Item Charge Foreign Currency Amount
	@NonNull Amount amountFC;
}
