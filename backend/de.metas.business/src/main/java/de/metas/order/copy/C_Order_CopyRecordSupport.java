package de.metas.order.copy;

import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCostService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.PO;

public class C_Order_CopyRecordSupport extends GeneralCopyRecordSupport
{
	private final OrderCostService orderCostService = SpringContextHolder.instance.getBean(OrderCostService.class);

	@Override
	protected boolean isCopyChildRecord(final CopyTemplate parentTemplate, final PO fromChildPO, final CopyTemplate childTemplate)
	{
		return isCopyOrderCosts(parentTemplate) || !isCostGeneratedOrderLine(fromChildPO);
	}

	private static boolean isCopyOrderCosts(final CopyTemplate parentTemplate)
	{
		return parentTemplate.hasChildTableName(I_C_Order_Cost.Table_Name);
	}

	private boolean isCostGeneratedOrderLine(final PO fromChildPO)
	{
		final I_C_OrderLine fromOrderLine = InterfaceWrapperHelper.create(fromChildPO, I_C_OrderLine.class);
		final OrderLineId orderLineId = OrderLineId.ofRepoId(fromOrderLine.getC_OrderLine_ID());
		return orderCostService.isCostGeneratedOrderLine(orderLineId);
	}

	@Override
	protected void onRecordAndChildrenCopied(final PO to, final PO from, final CopyTemplate template)
	{
		final I_C_Order toOrder = InterfaceWrapperHelper.create(to, I_C_Order.class);
		final I_C_Order fromOrder = InterfaceWrapperHelper.create(from, I_C_Order.class);

		if (isCopyOrderCosts(template))
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
