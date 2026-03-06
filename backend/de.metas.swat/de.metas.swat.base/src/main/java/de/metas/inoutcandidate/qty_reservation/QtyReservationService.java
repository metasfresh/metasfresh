package de.metas.inoutcandidate.qty_reservation;

import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.qty_reservation.commands.QtyDeliveredAllocateCommand;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * Synchronizes QtyDelivered on M_QtyReservation records
 * based on actual shipment completions (M_InOut).
 */
@Service
public class QtyReservationService
{
	@NonNull private final QtyReservationRepository qtyReservationRepository;
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	public QtyReservationService(@NonNull final QtyReservationRepository qtyReservationRepository)
	{
		this.qtyReservationRepository = qtyReservationRepository;
	}

	public void updateQtyDeliveredFromShipment(@NonNull final InOutId shipmentId)
	{
		QtyDeliveredAllocateCommand.builder()
				.qtyReservationRepository(qtyReservationRepository)
				.inOutBL(inOutBL)
				.triggeringShipmentLineId(shipmentId)
				.build().execute();
	}

}
