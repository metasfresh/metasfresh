package org.adempiere.model;

import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCostCloneMapper;
import de.metas.order.costs.OrderCostService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MOrderLinePOCopyRecordSupport.ClonedOrderLinesInfo;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.PO;

import javax.annotation.Nullable;

/**
 * @author Cristina Ghita, METAS.RO
 * version for copy an order
 */
public class MOrderPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	protected void onRecordAndChildrenCopied(final PO to, final PO from)
	{
		final I_C_Order toOrder = InterfaceWrapperHelper.create(to, I_C_Order.class);
		final I_C_Order fromOrder = InterfaceWrapperHelper.create(from, I_C_Order.class);
		onRecordAndChildrenCopied(toOrder, fromOrder);
	}

	private void onRecordAndChildrenCopied(final I_C_Order targetOrder, final I_C_Order fromOrder)
	{
		final OrderCostService orderCostService = SpringContextHolder.instance.getBean(OrderCostService.class);

		final OrderId fromOrderId = OrderId.ofRepoId(fromOrder.getC_Order_ID());
		orderCostService.cloneAllByOrderId(
				fromOrderId,
				OrderCostCloneMapperImpl.builder()
						.fromOrderId(fromOrderId)
						.targetOrderId(OrderId.ofRepoId(targetOrder.getC_Order_ID()))
						.clonedOrderLinesInfo(MOrderLinePOCopyRecordSupport.getClonedOrderLinesInfo(targetOrder))
						.build()
		);
	}

	@Builder
	private static class OrderCostCloneMapperImpl implements OrderCostCloneMapper
	{
		@NonNull private final OrderId fromOrderId;
		@NonNull private final OrderId targetOrderId;
		@Nullable private final ClonedOrderLinesInfo clonedOrderLinesInfo;

		@Override
		public @NonNull OrderId getTargetOrderId(@NonNull final OrderId orderId)
		{
			if (OrderId.equals(orderId, fromOrderId))
			{
				return targetOrderId;
			}
			else
			{
				// shall not happen
				throw new AdempiereException("Cannot map original order: " + orderId);
			}
		}

		@Override
		public @NonNull OrderLineId getTargetOrderLineId(@NonNull final OrderLineId originalOrderLineId)
		{
			// shall not happen because this method shall not be called in case there are no order lines
			if (clonedOrderLinesInfo == null)
			{
				throw new AdempiereException("No cloned order lines info available");
			}

			return clonedOrderLinesInfo.getTargetOrderLineId(originalOrderLineId);
		}
	}
}
