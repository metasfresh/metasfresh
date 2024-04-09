package de.metas.ui.web.split_shipment;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SplitShipmentRowsService
{
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final ShipmentScheduleSplitService shipmentScheduleSplitService;

	public SplitShipmentRows getByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);
		final Quantity qtyToDeliver = shipmentScheduleBL.getQtyToDeliver(shipmentSchedule);

		return SplitShipmentRows.builder()
				.service(shipmentScheduleSplitService)
				.shipmentScheduleId(shipmentScheduleId)
				.uom(qtyToDeliver.getUOM())
				.build();
	}
}
