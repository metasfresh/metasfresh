package de.metas.handlingunits.shipmentschedule.api;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.qty_reservation.QtyReservation;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Pairs a shipment schedule with an optional qty reservation.
 * <p>
 * When {@code qtyReservation} is non-null, HU picking should be filtered by the reservation's
 * {@code attributesKey} and capped at the reservation's effective qty.
 * When {@code qtyReservation} is null, picking falls back to the shipment schedule's own ASI.
 */
@Value
@Builder
public class ShipmentScheduleAndQtyReservation
{
	@NonNull I_M_ShipmentSchedule shipmentSchedule;
	@Nullable QtyReservation qtyReservation;

	/**
	 * Convenience factory for the fallback case: no specific reservation, use SS ASI.
	 */
	public static ShipmentScheduleAndQtyReservation of(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return builder().shipmentSchedule(shipmentSchedule).build();
	}
}
