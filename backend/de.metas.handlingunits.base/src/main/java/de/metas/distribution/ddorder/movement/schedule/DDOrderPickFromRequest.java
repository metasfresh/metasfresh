package de.metas.distribution.ddorder.movement.schedule;

import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DDOrderPickFromRequest
{
	@NonNull DDOrderMoveScheduleId scheduleId;
	@NonNull HuId huId;
}
