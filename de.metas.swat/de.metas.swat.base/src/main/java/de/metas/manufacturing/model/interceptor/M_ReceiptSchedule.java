package de.metas.manufacturing.model.interceptor;

import java.time.Instant;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.material.event.ManufacturingEventService;
import de.metas.material.event.ReceiptScheduleEvent;

@Interceptor(I_M_ReceiptSchedule.class)
public class M_ReceiptSchedule
{
	static final M_ReceiptSchedule INSTANCE = new M_ReceiptSchedule();

	private M_ReceiptSchedule()
	{
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE /* beforeDelete because we still need the M_TransAction_ID */ })
	public void fireEvent(final I_M_ReceiptSchedule schedule, final int timing)
	{
		final boolean deleted = timing == ModelValidator.TYPE_AFTER_DELETE;
		final ReceiptScheduleEvent event = ReceiptScheduleEvent.builder()
				.when(Instant.now())
				.reference(TableRecordReference.of(schedule))
				.productId(schedule.getM_Product_ID())
				.warehouseId(schedule.getM_Warehouse_ID())
				.qtyOrdered(schedule.getQtyOrdered())
				.receiptScheduleDeleted(deleted)
				.promisedDate(schedule.getMovementDate())
				.build();

		final String trxName = InterfaceWrapperHelper.getTrxName(schedule);
		ManufacturingEventService.get().fireEventAfterCommit(event, trxName);
	}
}
