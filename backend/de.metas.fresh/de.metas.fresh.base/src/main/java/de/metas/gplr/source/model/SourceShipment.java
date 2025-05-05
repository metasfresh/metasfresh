package de.metas.gplr.source.model;

import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceShipment
{
	@NonNull OrgId orgId;
	@NonNull String documentNo;
	@NonNull LocalDateAndOrgId movementDate;
	@NonNull SourceWarehouseInfo shipFrom;
	@NonNull SourceBPartnerInfo shipTo;
	boolean isBackToBack;
	@Nullable SourceShipperInfo shipper;
	@Nullable SourceIncotermsAndLocation incoterms;
}
