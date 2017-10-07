package de.metas.material.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
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
				.eventDescr(EventDescr.createNew(schedule))
				.reference(TableRecordReference.of(schedule))
				.materialDescr(MaterialDescriptor.builder()
						.productId(schedule.getM_Product_ID())
						.warehouseId(schedule.getM_Warehouse_ID())
						.date(schedule.getMovementDate())
						.quantity(schedule.getQtyOrdered())
						.build())
				.receiptScheduleDeleted(deleted)
				.build();

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(schedule);
		materialEventService.fireEventAfterNextCommit(event, trxName);
	}
}
