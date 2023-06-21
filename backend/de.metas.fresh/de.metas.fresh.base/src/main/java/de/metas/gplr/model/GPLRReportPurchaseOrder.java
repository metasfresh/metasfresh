package de.metas.gplr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportPurchaseOrder
{
	// 024 - Order
	// i.e. Purchase order number metasfresh
	@NonNull String documentNo;

	// 025 - Purchase From
	// i.e. SAP Code and Name and VAT ID of vendor (business partner)
	@NonNull BPartnerName purchasedFrom;

	// 026 - Vendors PO
	// i.e. Order reference in PO
	@Nullable String vendorReference;

	// 027 - Terms of Payment
	// i.e. (1) Terms of payment code, (2) payment term description
	@NonNull PaymentTermName paymentTerm;

	// 028 - Terms of Delivery
	// i.e. Incoterm Value and Location
	@Nullable IncotermsInfo incoterms;

	// 029 - Currency & Rate
	// i.e. Currency "/" Rate <CR> "FEC No.:" FEC ID All FECs related order
	@Nullable ForexInfo forexInfo;
}
