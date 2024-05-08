package de.metas.manufacturing.workflows_api.rest_api.json;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.QtyTU;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.util.Check;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
public class JsonFinishGoodsReceiveQRCodesGenerateRequest
{
	@NonNull WFProcessId wfProcessId;
	@NonNull FinishedGoodsReceiveLineId finishedGoodsReceiveLineId;
	@NonNull HuPackingInstructionsId tuPackingInstructionsId;
	@NonNull QtyTU qtyTUs;

	@Builder
	@Jacksonized
	private JsonFinishGoodsReceiveQRCodesGenerateRequest(
			final @NonNull WFProcessId wfProcessId,
			final @NonNull FinishedGoodsReceiveLineId finishedGoodsReceiveLineId,
			final @NonNull HuPackingInstructionsId tuPackingInstructionsId,
			final @NonNull QtyTU qtyTUs)
	{
		Check.assume(qtyTUs.isPositive(), "qtyTUs is positive");

		this.wfProcessId = wfProcessId;
		this.finishedGoodsReceiveLineId = finishedGoodsReceiveLineId;
		this.tuPackingInstructionsId = tuPackingInstructionsId;
		this.qtyTUs = qtyTUs;
	}
}
