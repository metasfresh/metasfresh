package de.metas.distribution.ddorder.movement.generate;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.distribution.ddorder.DDOrderAndLineId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.document.dimension.Dimension;
import de.metas.freighcost.FreightCostRule;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.eevolution.model.I_DD_Order;

@UtilityClass
public class DDOrderMovementHelper
{
	public static HUMovementGenerateRequest.HUMovementGenerateRequestBuilder prepareMovementGenerateRequest(
			@NonNull final I_DD_Order ddOrder,
			@NonNull final DDOrderLineId ddOrderLineId)
	{
		return HUMovementGenerateRequest.builder()
				//.movementDate(movementDate) // to be set by caller
				//
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ddOrder.getAD_Client_ID(), ddOrder.getAD_Org_ID()))
				.ddOrderLineId(DDOrderAndLineId.of(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()), ddOrderLineId))
				.poReference(ddOrder.getPOReference())
				.description(ddOrder.getDescription())
				.salesRepId(UserId.ofRepoIdOrNull(ddOrder.getSalesRep_ID()))
				//
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID(), ddOrder.getC_BPartner_Location_ID()))
				.bpartnerContactId(BPartnerContactId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID(), ddOrder.getAD_User_ID()))
				//
				.shipperId(ShipperId.ofRepoIdOrNull(ddOrder.getM_Shipper_ID()))
				.freightCostRule(FreightCostRule.ofNullableCode(ddOrder.getFreightCostRule()))
				.freightAmt(ddOrder.getFreightAmt())
				//
				.deliveryRule(DeliveryRule.ofNullableCode(ddOrder.getDeliveryRule()))
				.deliveryViaRule(DeliveryViaRule.ofNullableCode(ddOrder.getDeliveryViaRule()))
				.priorityRule(ddOrder.getPriorityRule())
				//
				.dimensionFields(Dimension.builder()
										 .activityId(ActivityId.ofRepoIdOrNull(ddOrder.getC_Activity_ID()))
										 .salesOrderId(OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID()))
										 .campaignId(ddOrder.getC_Campaign_ID())
										 .projectId(ProjectId.ofRepoIdOrNull(ddOrder.getC_Project_ID()))
										 .build());
	}
}
