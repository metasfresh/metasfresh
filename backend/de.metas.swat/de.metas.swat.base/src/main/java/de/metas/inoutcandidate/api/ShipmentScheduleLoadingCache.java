package de.metas.inoutcandidate.api;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;

public class ShipmentScheduleLoadingCache<T extends I_M_ShipmentSchedule>
{
	final IShipmentSchedulePA shipmentSchedulePA;
	private final Class<T> modelClass;

	private final HashMap<ShipmentScheduleId, T> byId = new HashMap<>();

	@Builder
	private ShipmentScheduleLoadingCache(
			@NonNull final IShipmentSchedulePA shipmentSchedulePA,
			@NonNull final Class<T> modelClass)
	{
		this.shipmentSchedulePA = shipmentSchedulePA;
		this.modelClass = modelClass;
	}

	public void warmUpByIds(final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		getByIds(shipmentScheduleIds);
	}

	public Collection<T> getByIds(final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		return CollectionUtils.getAllOrLoad(
				byId,
				shipmentScheduleIds,
				shipmentScheduleIdsToLoad -> shipmentSchedulePA.getByIds(shipmentScheduleIdsToLoad, modelClass)
		);
	}

	public T getById(final ShipmentScheduleId shipmentScheduleId)
	{
		return byId.computeIfAbsent(
				shipmentScheduleId,
				shipmentScheduleIdToLoad -> shipmentSchedulePA.getById(shipmentScheduleIdToLoad, modelClass)
		);
	}
}
