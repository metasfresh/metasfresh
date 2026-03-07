package de.metas.inoutcandidate.qty_reservation;

import de.metas.inout.InOutId;
import de.metas.inoutcandidate.qty_reservation.qty_delivered_update.UpdateQtyDeliveredDispatcher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Synchronizes QtyDelivered on M_QtyReservation records
 * based on actual shipment completions (M_InOut).
 */
@Service
@RequiredArgsConstructor
public class QtyReservationService
{
	@NonNull private final UpdateQtyDeliveredDispatcher updateQtyDeliveredDispatcher;

	public void scheduleUpdateQtyDelivered(@NonNull final InOutId shipmentId)
	{
		updateQtyDeliveredDispatcher.fireShipmentChanged(shipmentId);
	}
}
