package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class PickingJobCreateRepoRequest
{
	@NonNull OrgId orgId;
	@NonNull OrderId salesOrderId;
	@NonNull InstantAndOrgId preparationDate;
	@NonNull BPartnerLocationId deliveryBPLocationId;
	@NonNull String deliveryRenderedAddress;
	@NonNull UserId pickerId;

	@Singular @NonNull ImmutableList<Line> lines;

	//
	// -------------------------------------------
	//

	@Value
	@Builder
	public static class Line
	{
		@NonNull ProductId productId;
		@Singular @NonNull ImmutableList<Step> steps;
	}

	@Value
	@Builder
	public static class Step
	{
		@NonNull ShipmentScheduleId shipmentScheduleId;
		@NonNull OrderAndLineId salesOrderLineId;
		//
		// What?
		@NonNull ProductId productId;
		@NonNull Quantity qtyToPick;
		//
		// From where?
		@NonNull LocatorId locatorId;
		@NonNull HuId pickFromHUId;
	}
}
