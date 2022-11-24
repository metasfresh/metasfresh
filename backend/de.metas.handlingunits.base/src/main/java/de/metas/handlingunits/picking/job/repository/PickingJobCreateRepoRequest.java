package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.inout.ShipmentScheduleId;
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

		@Builder.Default
		@NonNull ImmutableSet<PickFromAlternative> pickFromAlternatives = ImmutableSet.of();
	}

	@Value(staticConstructor = "of")
	public static class PickFromAlternative
	{
		@NonNull LocatorId pickFromLocatorId;
		@NonNull HuId pickFromHUId;
		@NonNull Quantity qtyAvailable;
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
		@NonNull StepPickFrom mainPickFrom;

		@Builder.Default
		@NonNull ImmutableSet<StepPickFrom> pickFromAlternatives = ImmutableSet.of();

		@NonNull PackToSpec packToSpec;
	}

	@Value
	@Builder
	public static class StepPickFrom
	{
		@NonNull LocatorId pickFromLocatorId;
		@NonNull HuId pickFromHUId;
	}
}
