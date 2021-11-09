package de.metas.manufacturing.order;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@Builder
public class PPOrderIssueSchedule
{
	@NonNull PPOrderIssueScheduleId id;
	@NonNull PPOrderId ppOrderId;
	@NonNull PPOrderBOMLineId ppOrderBOMLineId;

	int seqNo;

	@NonNull ProductId productId;
	@NonNull Quantity qtyToIssue;
	@NonNull HuId issueFromHUId;
	@NonNull LocatorId issueFromLocatorId;

	@Nullable Issued issued;

	@Value
	@Builder
	public static class Issued
	{
		@NonNull Quantity qtyIssued;
		@Nullable QtyRejectedReasonCode qtyRejectedReasonCode;
		@Nullable HuId actualIssuedHUId;
		@Nullable PPCostCollectorId ppCostCollectorId;
	}
}
