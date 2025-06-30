package de.metas.ui.web.split_shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentDateRule;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

public class SplitShipmentView_ProcessAllRows extends SplitShipmentView_ProcessTemplate
{
	@NonNull private static final String SYS_CFG_ShipDateRule = "de.metas.ui.web.split_shipment.SplitShipmentView_ProcessAllRows.ShipDateRule";

	@NonNull private final IShipmentService shipmentService = SpringContextHolder.instance.getBean(IShipmentService.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

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
		final ShipmentDateRule.ShipmentDateRuleFlags shipmentDateRuleFlags = sysConfigBL.getReferenceListAware(SYS_CFG_ShipDateRule, ShipmentDateRule.DeliveryDate, ShipmentDateRule.class).getFlags();
		
		shipmentService.generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest.builder()
				.scheduleIds(ImmutableSet.of(view.getShipmentScheduleId()))
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_SPLIT_SHIPMENT)
				.onTheFlyPickToPackingInstructions(true)
				.isCompleteShipment(true)
				.isShipDateToday(shipmentDateRuleFlags.isShipDateToday())
				.isShipDateDeliveryDay(shipmentDateRuleFlags.isShipDateDeliveryDay())
				.build());
		
		view.invalidateAll();

		ViewChangesCollector.getCurrentOrAutoflush().collectFullyChanged(view);

		return MSG_OK;
	}
}
