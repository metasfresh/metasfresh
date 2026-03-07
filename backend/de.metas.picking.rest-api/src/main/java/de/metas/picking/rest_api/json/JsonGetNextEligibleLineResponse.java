package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonGetNextEligibleLineResponse
{
	@Nullable PickingJobLineId lineId;
	@Nullable List<String> logs;
}
