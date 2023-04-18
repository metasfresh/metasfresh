package de.metas.handlingunits.pporder.api.issue_schedule;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.warehouse.LocatorId;
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

	@With
	@NonNull SeqNo seqNo;

	@NonNull ProductId productId;
	@NonNull Quantity qtyToIssue;
	@NonNull HuId issueFromHUId;
	@NonNull LocatorId issueFromLocatorId;
	boolean isAlternativeIssue;

	@With
	@Nullable Issued issued;

	@Value
	@Builder
	public static class Issued
	{
		@NonNull Quantity qtyIssued;
		@Nullable QtyRejectedWithReason qtyRejected;
	}

	public boolean isIssued() {return issued != null;}
}
