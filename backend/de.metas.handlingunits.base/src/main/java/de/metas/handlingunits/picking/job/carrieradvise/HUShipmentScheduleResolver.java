package de.metas.handlingunits.picking.job.carrieradvise;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Resolves the shipment schedules (including carrier services) linked to a given top-level HU
 * via undelivered qty-picked records.
 *
 * <p>Both {@code PackedHUCarrierAdviseService} and {@link CarrierAdviseConsistencyService} delegate here
 * instead of duplicating the resolution logic.
 */
@Service
@RequiredArgsConstructor
public class HUShipmentScheduleResolver
{
	@NonNull private final ShipmentScheduleService shipmentScheduleService;

	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);

	/**
	 * @return an immutable map (keyed by schedule ID) of shipment schedules linked to the given top-level HU.
	 *         Carrier services are loaded (so {@link ShipmentSchedule#getCarrierServicesIfLoaded()} is safe to call).
	 *         Returns an empty map when no undelivered qty-picked records exist for the HU.
	 */
	@NonNull
	public ImmutableMap<ShipmentScheduleId, ShipmentSchedule> resolveSchedulesByIdForHU(@NonNull final I_M_HU topLevelHU)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords =
				huShipmentScheduleDAO.retrieveQtyPickedNotDeliveredForTopLevelHU(topLevelHU);
		if (qtyPickedRecords.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<ShipmentScheduleId> scheduleIds = qtyPickedRecords.stream()
				.map(r -> ShipmentScheduleId.ofRepoId(r.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return shipmentScheduleService.getByIds(scheduleIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(ShipmentSchedule::getId, s -> s));
	}
}
