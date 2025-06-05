package de.metas.inoutcandidate.api;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Set;

@Value
public class ShipmentScheduleAllocQuery
{
	@Nullable ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;
	boolean alreadyShipped;
	@Nullable ImmutableSet<HuId> onlyLUIds;

	@Builder
	private ShipmentScheduleAllocQuery(
			@Nullable final Set<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final Boolean alreadyShipped,
			@Nullable final Set<HuId> onlyLUIds)
	{
		if ((shipmentScheduleIds == null || shipmentScheduleIds.isEmpty())
				&& (onlyLUIds == null || onlyLUIds.isEmpty()))
		{
			throw new AdempiereException("One of shipmentScheduleIds or onlyLUIds must be specified");
		}

		this.shipmentScheduleIds = shipmentScheduleIds != null ? ImmutableSet.copyOf(shipmentScheduleIds) : null;
		this.alreadyShipped = alreadyShipped;
		this.onlyLUIds = onlyLUIds != null ? ImmutableSet.copyOf(onlyLUIds) : null;
	}
}
