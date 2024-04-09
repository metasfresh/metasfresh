package de.metas.ui.web.split_shipment;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitService;
import de.metas.ui.web.split_shipment.SplitShipmentRows.SplitShipmentRowsBuilder;
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
		return newSplitShipmentRows()
				.shipmentScheduleId(shipmentScheduleId)
				.build();
	}

	private SplitShipmentRowsBuilder newSplitShipmentRows()
	{
		return SplitShipmentRows.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.shipmentScheduleSplitService(shipmentScheduleSplitService);
	}
}
