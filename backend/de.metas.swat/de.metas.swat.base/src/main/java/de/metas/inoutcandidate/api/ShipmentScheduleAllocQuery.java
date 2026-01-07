package de.metas.inoutcandidate.api;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Set;

@Value
public class ShipmentScheduleAllocQuery
{
	@Nullable ShipmentScheduleAndJobScheduleIdSet scheduleIds;
	@Nullable Boolean alreadyShipped;
	@Nullable ImmutableSet<HuId> onlyLUIds;

	@Builder
	private ShipmentScheduleAllocQuery(
			@Nullable final ShipmentScheduleAndJobScheduleIdSet scheduleIds,
			@NonNull final Boolean alreadyShipped,
			@Nullable final Set<HuId> onlyLUIds)
	{
		if ((scheduleIds == null || scheduleIds.isEmpty())
				&& (onlyLUIds == null || onlyLUIds.isEmpty()))
		{
			throw new AdempiereException("One of scheduleIds or onlyLUIds must be specified");
		}

		this.scheduleIds = scheduleIds;
		this.alreadyShipped = alreadyShipped;
		this.onlyLUIds = onlyLUIds != null ? ImmutableSet.copyOf(onlyLUIds) : null;
	}
}
