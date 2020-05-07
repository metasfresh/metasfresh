package de.metas.inoutcandidate.modelvalidator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;

@Validator(I_C_OrderLine.class)
public class C_OrderLine_ReceiptSchedule
{
	/**
	 * Invokes {@link IReceiptScheduleProducer#createOrUpdateReceiptSchedules(Object, List)} (synchronously!)
	 * @param orderLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			// I_C_OrderLine.COLUMNNAME_QtyOrderedOverUnder, this column is *only* set from the receipt schedule, so there is no point in creating/updating the rs if QtyOrderedOverUnder was changed from the rs.
			I_C_OrderLine.COLUMNNAME_QtyOrdered })
	public void createReceiptSchedules(final I_C_OrderLine orderLine)
	{
		if (!C_Order_ReceiptSchedule.isEligibleForReceiptSchedule(orderLine.getC_Order()))
		{
			return;
		}

		final IReceiptScheduleProducerFactory receiptScheduleProducerFactory = Services.get(IReceiptScheduleProducerFactory.class);
		final boolean async = false;
		final IReceiptScheduleProducer producer = receiptScheduleProducerFactory.createProducer(I_C_OrderLine.Table_Name, async);

		producer.updateReceiptSchedules(orderLine);
	}
}
