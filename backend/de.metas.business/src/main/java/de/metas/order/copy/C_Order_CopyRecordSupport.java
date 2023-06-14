package de.metas.order.copy;

import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.order.OrderId;
import de.metas.order.costs.OrderCostService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.PO;

public class C_Order_CopyRecordSupport extends GeneralCopyRecordSupport
{
	private final OrderCostService orderCostService = SpringContextHolder.instance.getBean(OrderCostService.class);

	@Override
	protected void onRecordAndChildrenCopied(final PO to, final PO from, final CopyTemplate template)
	{
		final I_C_Order toOrder = InterfaceWrapperHelper.create(to, I_C_Order.class);
		final I_C_Order fromOrder = InterfaceWrapperHelper.create(from, I_C_Order.class);

		if (template.hasChildTableName(I_C_Order_Cost.Table_Name))
		{
			cloneOrderCosts(toOrder, fromOrder);
		}
	}

	private void cloneOrderCosts(final I_C_Order targetOrder, final I_C_Order fromOrder)
	{
		final OrderId fromOrderId = OrderId.ofRepoId(fromOrder.getC_Order_ID());
		orderCostService.cloneAllByOrderId(
				fromOrderId,
				OrderCostCloneMapperImpl.builder()
						.fromOrderId(fromOrderId)
						.targetOrderId(OrderId.ofRepoId(targetOrder.getC_Order_ID()))
						.clonedOrderLinesInfo(ClonedOrderLinesInfo.getOrNull(targetOrder))
						.build()
		);
	}
}
