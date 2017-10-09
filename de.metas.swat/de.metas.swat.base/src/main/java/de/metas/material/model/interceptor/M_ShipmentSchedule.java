package de.metas.material.model.interceptor;

import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ShipmentScheduleEvent;

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
			ModelValidator.TYPE_BEFORE_DELETE /* before delete because we still need the M_ShipmentSchedule_ID */
	}, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Calculated,
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override,
			I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID,
			I_M_ShipmentSchedule.COLUMNNAME_AD_Org_ID,
			I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override,
			I_M_ShipmentSchedule.COLUMNNAME_PreparationDate,
			I_M_ShipmentSchedule.COLUMNNAME_IsActive /* IsActive=N shall be threaded like a deletion */ })
	public void fireEvent(final I_M_ShipmentSchedule schedule, final int timing)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final boolean deleted = timing == ModelValidator.TYPE_BEFORE_DELETE || !schedule.isActive();
		final Timestamp preparationDate = shipmentScheduleEffectiveBL.getPreparationDate(schedule);

		final ShipmentScheduleEvent event = ShipmentScheduleEvent.builder()
				.eventDescr(EventDescr.createNew(schedule))
				.materialDescr(MaterialDescriptor.builder()
						.date(preparationDate)
						.productId(schedule.getM_Product_ID())
						.warehouseId(shipmentScheduleEffectiveBL.getWarehouseId(schedule))
						.quantity(shipmentScheduleEffectiveBL.computeQtyOrdered(schedule))
						.build())
				.reference(TableRecordReference.of(schedule))
				.shipmentScheduleDeleted(deleted)
				.orderLineId(schedule.getC_OrderLine_ID())
				.build();

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(schedule);
		materialEventService.fireEventAfterNextCommit(event, trxName);
	}
}
