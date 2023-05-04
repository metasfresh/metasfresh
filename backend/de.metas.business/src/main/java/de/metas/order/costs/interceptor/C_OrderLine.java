package de.metas.order.costs.interceptor;

import de.metas.order.OrderAndLineId;
import de.metas.order.costs.OrderCostDetailOrderLinePart;
import de.metas.order.costs.OrderCostService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final OrderCostService orderCostService;

	public C_OrderLine(
			@NonNull final OrderCostService orderCostService)
	{
		this.orderCostService = orderCostService;
	}

	@NonNull
	private static OrderAndLineId extractOrderAndLineId(final I_C_OrderLine record) {return OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID());}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void onBeforeChange(final I_C_OrderLine record)
	{
		final OrderCostDetailOrderLinePart orderLineInfo = extractOrderLineInfoIfHasChanges(record);
		if (orderLineInfo != null)
		{
			orderCostService.updateOrderCostsOnOrderLineChanged(orderLineInfo);
		}
	}

	@Nullable
	private static OrderCostDetailOrderLinePart extractOrderLineInfoIfHasChanges(final I_C_OrderLine record)
	{
		final I_C_OrderLine recordBeforeChanges = InterfaceWrapperHelper.createOld(record, I_C_OrderLine.class);
		final OrderCostDetailOrderLinePart lineInfoBeforeChanges = OrderCostDetailOrderLinePart.ofOrderLine(recordBeforeChanges);

		final OrderCostDetailOrderLinePart lineInfo = OrderCostDetailOrderLinePart.ofOrderLine(record);
		if (lineInfo.equals(lineInfoBeforeChanges))
		{
			return null; // no changes
		}

		return lineInfo;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeDelete(final I_C_OrderLine record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			orderCostService.deleteByCreatedOrderLineId(extractOrderAndLineId(record));
		}
	}
}
