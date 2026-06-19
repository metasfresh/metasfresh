package de.metas.handlingunits.shipmentschedule.api;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.qty_reservation.QtyReservation;
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
public class ShipmentScheduleAndQtyReservation
{
	@NonNull I_M_ShipmentSchedule shipmentSchedule;
	@Nullable QtyReservation qtyReservation;

	private ShipmentScheduleAndQtyReservation(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule, 
			@Nullable final QtyReservation qtyReservation)
	{
		this.shipmentSchedule = shipmentSchedule;
		this.qtyReservation = qtyReservation;
	}

	public static ShipmentScheduleAndQtyReservation of(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return new ShipmentScheduleAndQtyReservation(shipmentSchedule, null);
	}

	public static ShipmentScheduleAndQtyReservation of(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final QtyReservation qtyReservation)
	{
		return new ShipmentScheduleAndQtyReservation(shipmentSchedule, qtyReservation);
	}
}
