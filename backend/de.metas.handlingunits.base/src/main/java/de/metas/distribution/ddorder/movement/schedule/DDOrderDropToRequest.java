package de.metas.distribution.ddorder.movement.schedule;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class DDOrderDropToRequest
{
	@NonNull Set<DDOrderMoveScheduleId> scheduleIds;
	@Nullable LocatorId dropToLocatorId;
}
