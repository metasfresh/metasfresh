package de.metas.gplr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportSalesOrder
{
	// 012 - Order - Metasfresh sales order number
	@NonNull String documentNo;

	// 013 - Sold to - Customer code, customer name, customer VAT ID Of metasfresh Business partner
	@NonNull BPartnerName customer;

	// 014 - Contract - Frame contract number if invoice for call off order
	@Nullable String frameContractNo;

	// 015 - Customers PO
	// i.e. Poreference (Order reference Sales Order)
	@Nullable String poReference;

	// 016 - Terms of Delivery
	// i.e. Incoterm and incoterm Location
	@Nullable IncotermsInfo incoterms;
}
