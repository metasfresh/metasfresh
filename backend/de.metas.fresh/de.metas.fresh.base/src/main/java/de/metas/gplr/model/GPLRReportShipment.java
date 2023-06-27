package de.metas.gplr.model;

import de.metas.location.CountryCode;
import de.metas.organization.LocalDateAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportShipment
{
	// 017 - Delivery - Delivery (Goods issue) DocNo metasfresh
	@NonNull String documentNo;

	// 018 - Ship To - Customer code, customer name of ship-to, if no different ship-to, print business partner
	@NonNull GPLRBPartnerName shipTo;

	// 019 - Dest - 2 digit ISO code of ship-to country
	@Nullable CountryCode shipToCountry;

	// 020 - Warehouse - last 4 digits SAP Warehouse No and warehouse name, for back to back print XXXX
	@NonNull GPLRWarehouseName warehouse;

	// 021 - GI Date - Goods issue date
	@NonNull LocalDateAndOrgId movementDate;

	// 022 - Terms of Delivery - Incoterm and Incoterm Location
	@Nullable GPLRIncotermsInfo incoterms;

	// 023 - Vessel Name & Shipped By - Print Vessel name field content, if empty print Means of Transport
	@Nullable GPLRShipperRenderedString shipper;
}
