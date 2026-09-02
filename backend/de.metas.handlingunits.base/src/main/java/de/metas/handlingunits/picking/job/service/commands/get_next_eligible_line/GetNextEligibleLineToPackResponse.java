package de.metas.handlingunits.picking.job.service.commands.get_next_eligible_line;

import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class GetNextEligibleLineToPackResponse
{
	@Nullable PickingJobLineId lineId;
	@Nullable HUInfo huInfo;

	@Nullable List<String> logs;

	public boolean isFound() {return lineId != null;}
}
