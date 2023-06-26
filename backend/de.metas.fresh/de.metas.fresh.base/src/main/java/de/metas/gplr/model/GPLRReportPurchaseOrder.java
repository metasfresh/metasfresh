package de.metas.gplr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportPurchaseOrder
{
	// 024 - Order - Purchase order number metasfresh
	@NonNull String documentNo;

	// 025 - Purchase From - SAP Code and Name and VAT ID of vendor (business partner)
	@NonNull GPLRBPartnerName purchasedFrom;

	// 026 - Vendors PO - Order reference in PO
	@Nullable String vendorReference;

	// 027 - Terms of Payment - (1) Terms of payment code, (2) payment term description
	@NonNull GPLRPaymentTermName paymentTerm;

	// 028 - Terms of Delivery - Incoterm Value and Location
	@Nullable GPLRIncotermsInfo incoterms;

	// 029 - Currency & Rate - Currency "/" Rate <CR> "FEC No.:" FEC ID All FECs related order
	@Nullable GPLRForexInfo forexInfo;
}
