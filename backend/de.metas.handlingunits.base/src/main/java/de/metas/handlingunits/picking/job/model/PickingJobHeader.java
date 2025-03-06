package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
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
	@NonNull PickingJobAggregationType aggregationType;
	@Nullable String salesOrderDocumentNo;
	@Nullable ZonedDateTime preparationDate;
	@Nullable ZonedDateTime deliveryDate;
	@Nullable String customerName;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable String deliveryRenderedAddress;
	boolean isAllowPickingAnyHU;
	@Nullable UserId lockedBy;
	boolean isPickingReviewRequired;
	@Nullable BPartnerLocationId handoverLocationId;

	@Nullable
	public BPartnerId getCustomerId() {return this.deliveryBPLocationId != null ? this.deliveryBPLocationId.getBpartnerId() : null;}

}
