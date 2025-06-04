package de.metas.hu_consolidation.mobile.shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

@Service
@RequiredArgsConstructor
public class HUConsolidationShipmentService
{
	@NonNull private final IShipmentService shipmentService;

	public void createShipmentForLUs(@NonNull final HUConsolidationJob job, @NonNull final Set<HuId> luIds)
	{
		shipmentService.generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest.builder()
				.quantityTypeToUse(TYPE_PICKED_QTY)
				.onlyLUIds(ImmutableSet.copyOf(luIds))
				.isCompleteShipment(true)
				.isCloseShipmentSchedules(false)
				// since we are not going to immediately create invoices, we want to move on and to not wait for shipments
				.waitForShipments(false)
				.build());
	}

}
