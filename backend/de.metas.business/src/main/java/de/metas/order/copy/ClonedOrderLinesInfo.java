package de.metas.order.copy;

import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.util.HashMap;

class ClonedOrderLinesInfo
{
	private static final String DYNATTR_ClonedOrderLinesInfo = "ClonedOrderLinesInfo";

	public static ClonedOrderLinesInfo getOrCreate(I_C_Order targetOrder)
	{
		return InterfaceWrapperHelper.computeDynAttributeIfAbsent(targetOrder, DYNATTR_ClonedOrderLinesInfo, ClonedOrderLinesInfo::new);
	}

	@Nullable
	static ClonedOrderLinesInfo getOrNull(final I_C_Order targetOrder)
	{
		return InterfaceWrapperHelper.getDynAttribute(targetOrder, DYNATTR_ClonedOrderLinesInfo);
	}

	private final HashMap<OrderLineId, OrderLineId> original2targetOrderLineIds = new HashMap<>();

	public void addOriginalToClonedOrderLineMapping(@NonNull final OrderLineId originalOrderLineId, @NonNull final OrderLineId targetOrderLineId)
	{
		original2targetOrderLineIds.put(originalOrderLineId, targetOrderLineId);
	}

	public OrderLineId getTargetOrderLineId(@NonNull final OrderLineId originalOrderLineId)
	{
		final OrderLineId targetOrderLineId = original2targetOrderLineIds.get(originalOrderLineId);
		if (targetOrderLineId == null)
		{
			throw new AdempiereException("No target order line found for " + originalOrderLineId);
		}
		return targetOrderLineId;
	}
}
