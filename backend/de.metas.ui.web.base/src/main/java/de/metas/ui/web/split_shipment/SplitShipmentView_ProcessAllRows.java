package de.metas.ui.web.split_shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.event.ViewChangesCollector;
import org.compiere.SpringContextHolder;

public class SplitShipmentView_ProcessAllRows extends SplitShipmentView_ProcessTemplate
{
	private final IShipmentService shipmentService = SpringContextHolder.instance.getBean(IShipmentService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getView().hasRowsToProcess())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final SplitShipmentView view = getView();
		
		shipmentService.generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest.builder()
				.scheduleIds(ImmutableSet.of(view.getShipmentScheduleId()))
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_SPLIT_SHIPMENT)
				.onTheFlyPickToPackingInstructions(true)
				.isCompleteShipment(true)
				.build());
		
		view.invalidateAll();

		ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(view);

		return MSG_OK;
	}
}
