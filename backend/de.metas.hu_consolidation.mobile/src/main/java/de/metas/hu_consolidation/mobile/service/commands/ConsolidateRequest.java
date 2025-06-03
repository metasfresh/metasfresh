package de.metas.hu_consolidation.mobile.service.commands;

import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ConsolidateRequest
{
	@NonNull HUConsolidationJob job;
	@NonNull PickingSlotId fromPickingSlotId;
}
