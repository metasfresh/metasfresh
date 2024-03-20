package de.metas.manufacturing.workflows_api.rest_api.json;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.util.Check;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
public class JsonFinishGoodsReceiveQRCodesGenerateRequest
{
	@NonNull WFProcessId wfProcessId;
	@NonNull FinishedGoodsReceiveLineId finishedGoodsReceiveLineId;
	@NonNull HuPackingInstructionsId huPackingInstructionsId;
	int numberOfHUs;
	@Nullable Integer numberOfCopies;

	@Builder
	@Jacksonized
	private JsonFinishGoodsReceiveQRCodesGenerateRequest(
			final @NonNull WFProcessId wfProcessId,
			final @NonNull FinishedGoodsReceiveLineId finishedGoodsReceiveLineId,
			final @NonNull HuPackingInstructionsId huPackingInstructionsId,
			final int numberOfHUs,
			final @Nullable Integer numberOfCopies)
	{
		Check.assume(numberOfHUs > 0, "numberOfHUs is positive");
		Check.assume(numberOfCopies == null ||  numberOfCopies > 0, "numberOfCopies is missing or positive");

		this.wfProcessId = wfProcessId;
		this.finishedGoodsReceiveLineId = finishedGoodsReceiveLineId;
		this.huPackingInstructionsId = huPackingInstructionsId;
		this.numberOfHUs = numberOfHUs;
		this.numberOfCopies = numberOfCopies;
	}
}
