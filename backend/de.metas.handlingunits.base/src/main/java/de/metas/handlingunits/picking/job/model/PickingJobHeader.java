package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class PickingJobHeader
{
	@NonNull String salesOrderDocumentNo;
	@NonNull ZonedDateTime preparationDate;
	@NonNull String customerName;
	@NonNull BPartnerLocationId deliveryBPLocationId;
	@NonNull String deliveryRenderedAddress;
	@NonNull UserId lockedBy;
}
