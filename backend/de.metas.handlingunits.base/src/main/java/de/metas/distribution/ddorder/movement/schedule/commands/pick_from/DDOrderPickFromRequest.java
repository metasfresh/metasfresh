package de.metas.distribution.ddorder.movement.schedule.commands.pick_from;

import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder
public class DDOrderPickFromRequest
{
	@NonNull DDOrderMoveScheduleId scheduleId;
	@NonNull HuId huId;
	@Nullable LocatorId inTransitLocatorId;
}
