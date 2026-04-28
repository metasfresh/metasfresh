package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.order.paymentschedule.OrderPayScheduleLine;

public class C_OrderPaySchedule_StepDefData extends StepDefData<OrderPayScheduleLine>
		implements StepDefDataGetIdAware<OrderPayScheduleId, OrderPayScheduleLine>
{
	public C_OrderPaySchedule_StepDefData()
	{
		super(OrderPayScheduleLine.class);
	}

	@Override
	public OrderPayScheduleId extractIdFromRecord(final OrderPayScheduleLine line)
	{
		return line.getId();
	}
}
