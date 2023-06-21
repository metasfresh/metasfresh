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
	// 017 - Delivery
	// i.e. Delivery (Goods issue) DocNo metasfresh
	@NonNull String documentNo;

	// 018 - Ship To
	// i.e. Customer code, customer name of ship-to, if no different ship-to, print business partner
	@NonNull BPartnerName shipTo;

	// 019 - Dest
	// i.e. 2 digit ISO code of ship-to country
	@NonNull CountryCode shipToCountry;

	// 020 - Warehouse
	// i.e. ast 4 digits SAP Warehouse No and warehouse name, for back to back print XXXX
	@NonNull String warehouse;

	// 021 - GI Date
	// i.e. Goods issue date
	@NonNull LocalDateAndOrgId movementDate;

	// 022 - Terms of Delivery
	// i.e. Incoterm and Incoterm Location
	@Nullable IncotermsInfo incoterms;

	// 023 - Vessel Name & Shipped By
	// i.e. Print Vessel name field content, if empty print Means of Transport
	@Nullable String shipper;
}
