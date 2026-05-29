package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.lock.spi.ExistingLockInfo;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ScheduledPackageableLocks
{
	@NonNull public static final ScheduledPackageableLocks EMPTY = new ScheduledPackageableLocks(ImmutableSetMultimap.of());
	@NonNull private final ImmutableSetMultimap<ShipmentScheduleAndJobScheduleId, ExistingLockInfo> multimap;

	private ScheduledPackageableLocks(@NonNull final SetMultimap<ShipmentScheduleAndJobScheduleId, ExistingLockInfo> multimap)
	{
		this.multimap = ImmutableSetMultimap.copyOf(multimap);
	}

	public static ScheduledPackageableLocks of(@NonNull final SetMultimap<ShipmentScheduleAndJobScheduleId, ExistingLockInfo> multimap)
	{
		return !multimap.isEmpty()
				? new ScheduledPackageableLocks(multimap)
				: EMPTY;
	}

	public boolean isLocked(@NonNull final ShipmentScheduleAndJobScheduleId scheduleId)
	{
		return multimap.containsKey(scheduleId);
	}

	public boolean isLockedAnyOf(final ImmutableSet<ShipmentScheduleAndJobScheduleId> scheduleIds)
	{
		if (scheduleIds.isEmpty()) {return false;}
		return scheduleIds.stream().anyMatch(this::isLocked);
	}
}
