package de.metas.hu_consolidation.mobile.job.commands.consolidate;

import de.metas.handlingunits.HuId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ConsolidateRequest
{
	@NonNull UserId callerId;
	@NonNull HUConsolidationJobId jobId;
	@NonNull PickingSlotId fromPickingSlotId;
	@Nullable HuId huId;
}
