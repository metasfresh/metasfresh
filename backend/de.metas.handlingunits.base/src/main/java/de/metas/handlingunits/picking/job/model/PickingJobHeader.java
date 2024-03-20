package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/** Non-changable picking job header */
@Value
@Builder(toBuilder = true)
public class PickingJobHeader
{
	@NonNull String salesOrderDocumentNo;
	@NonNull ZonedDateTime preparationDate;
	@NonNull ZonedDateTime deliveryDate;
	@NonNull String customerName;
	@NonNull BPartnerLocationId deliveryBPLocationId;
	@NonNull String deliveryRenderedAddress;
	boolean isAllowPickingAnyHU;
	@Nullable UserId lockedBy;
	boolean isPickingReviewRequired;
	@Nullable BPartnerLocationId handoverLocationId;

	public BPartnerId getCustomerId() {return this.deliveryBPLocationId.getBpartnerId();}

}
