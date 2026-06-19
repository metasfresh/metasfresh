package de.metas.handlingunits.picking.job.service.external.shipmentschedule;

import de.metas.inout.ShipmentScheduleId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ShipmentScheduleInfoLoadingCache
{
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;

	private final HashMap<ShipmentScheduleId, ShipmentScheduleInfo> byId = new HashMap<>();

	ShipmentScheduleInfoLoadingCache(@NonNull final PickingJobShipmentScheduleService shipmentScheduleService)
	{
		this.shipmentScheduleService = shipmentScheduleService;
	}

	public void warmUpByIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		getByIds(ids);
	}

	public ShipmentScheduleInfo getById(@NonNull final ShipmentScheduleId id)
	{
		return byId.computeIfAbsent(id, shipmentScheduleService::getById);
	}

	public Collection<ShipmentScheduleInfo> getByIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return CollectionUtils.getAllOrLoad(this.byId, ids, shipmentScheduleService::getByIds);
	}
}
