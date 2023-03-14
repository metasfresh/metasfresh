package org.adempiere.model;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCostCloneMapper;
import de.metas.order.costs.OrderCostService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MOrderLinePOCopyRecordSupport.ClonedOrderLinesInfo;
import org.compiere.SpringContextHolder;
import org.compiere.model.GridField;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Cristina Ghita, METAS.RO
 * version for copy an order
 */
public class MOrderPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	private static final List<String> COLUMNNAMES_ToCopyDirectly = ImmutableList.of(
			I_C_Order.COLUMNNAME_PreparationDate, // task 09000
			I_C_Order.COLUMNNAME_FreightAmt
	);

	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return super.getSuggestedChildren(po, suggestedChildren)
				.stream()
				.filter(childTableInfo -> I_C_OrderLine.Table_Name.equals(childTableInfo.getTableName()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		if (COLUMNNAMES_ToCopyDirectly.contains(columnName))
		{
			return from.get_Value(columnName);
		}

		//
		// Fallback to super:
		return super.getValueToCopy(to, from, columnName);
	}

	@Override
	public Object getValueToCopy(final GridField gridField)
	{
		if (COLUMNNAMES_ToCopyDirectly.contains(gridField.getColumnName()))
		{
			return gridField.getValue();
		}

		//
		// Fallback to super:
		return super.getValueToCopy(gridField);
	}

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
