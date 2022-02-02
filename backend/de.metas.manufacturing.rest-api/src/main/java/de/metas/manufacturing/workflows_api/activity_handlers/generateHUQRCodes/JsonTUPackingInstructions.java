package de.metas.manufacturing.workflows_api.activity_handlers.generateHUQRCodes;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.QtyTU;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonTUPackingInstructions
{
	@NonNull String caption;
	@NonNull HuPackingInstructionsId tuPackingInstructionsId;
	@NonNull FinishedGoodsReceiveLineId finishedGoodsReceiveLineId;
	@NonNull QtyTU qtyTUs;
}
