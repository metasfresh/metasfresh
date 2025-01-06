package de.metas.handlingunits.pporder.api.issue_schedule;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
public class PPOrderIssueScheduleCreateRequest
{
	@NonNull PPOrderId ppOrderId;
	@NonNull PPOrderBOMLineId ppOrderBOMLineId;

	@NonNull SeqNo seqNo;

	@NonNull ProductId productId;
	@NonNull Quantity qtyToIssue;
	@NonNull HuId issueFromHUId;
	@NonNull LocatorId issueFromLocatorId;

	boolean isAlternativeIssue;

	@Nullable Quantity qtyIssued;

	@Builder
	private PPOrderIssueScheduleCreateRequest(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final PPOrderBOMLineId ppOrderBOMLineId,
			@NonNull final SeqNo seqNo,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToIssue,
			@NonNull final HuId issueFromHUId,
			@NonNull final LocatorId issueFromLocatorId,
			final boolean isAlternativeIssue,
			@Nullable final Quantity qtyIssued)
	{
		if (qtyIssued != null)
		{
			Quantity.assertSameUOM(qtyToIssue, qtyIssued);
		}

		this.ppOrderId = ppOrderId;
		this.ppOrderBOMLineId = ppOrderBOMLineId;
		this.seqNo = seqNo;
		this.productId = productId;
		this.qtyToIssue = qtyToIssue;
		this.issueFromHUId = issueFromHUId;
		this.issueFromLocatorId = issueFromLocatorId;
		this.isAlternativeIssue = isAlternativeIssue;
		this.qtyIssued = qtyIssued;
	}
}
