package de.metas.handlingunits.picking.job.service.commands.get_next_eligible_line;

import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GetNextEligibleLineToPackRequest
{
	@NonNull UserId callerId;
	@NonNull PickingJobId pickingJobId;
	@Nullable PickingJobLineId excludeLineId;
	@NonNull ScannedCode huScannedCode;
}
