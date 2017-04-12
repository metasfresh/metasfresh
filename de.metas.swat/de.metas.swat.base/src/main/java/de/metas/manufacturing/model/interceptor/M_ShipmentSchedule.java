package de.metas.manufacturing.model.interceptor;

import java.time.Instant;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.manufacturing.event.ManufacturingEventService;
import de.metas.manufacturing.event.ShipmentScheduleEvent;

/**
 * Shipment Schedule module: M_ShipmentSchedule
 *
 * @author tsa
 *
 */
@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	static final M_ShipmentSchedule INSTANCE = new M_ShipmentSchedule();

	private M_ShipmentSchedule()
	{
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE /* beforeDelete because we still need the M_TransAction_ID */ })
	public void fireEvent(final I_M_ShipmentSchedule schedule, final int timing)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final boolean deleted = timing == ModelValidator.TYPE_AFTER_DELETE;
		final ShipmentScheduleEvent event = ShipmentScheduleEvent.builder()
				.preparationDate(shipmentScheduleEffectiveBL.getPreparationDate(schedule))
				.productId(schedule.getM_Product_ID())
				.qtyOrdered(shipmentScheduleEffectiveBL.getQtyOrdered(schedule))
				.reference(TableRecordReference.of(schedule))
				.shipmentScheduleDeleted(deleted)
				.warehouseId(schedule.getM_Warehouse_ID())
				.when(Instant.now())
				.build();

		final String trxName = InterfaceWrapperHelper.getTrxName(schedule);
		ManufacturingEventService.get().fireEventAfterCommit(event, trxName);
	}
}
