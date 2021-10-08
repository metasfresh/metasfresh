package de.metas.picking.workflow.model;

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
	@NonNull String customerName;
	@NonNull ZonedDateTime preparationDate;
	@NonNull String deliveryAddress;
	@NonNull UserId lockedBy;
}
