package de.metas.handlingunits.picking.job.service.external.salesorder;

import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PickingJobSalesOrderService
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	public String getDocumentNoById(@NonNull final OrderId orderId)
	{
		return orderBL.getDocumentNoById(orderId);
	}

	public Map<OrderId, String> getDocumentNosByIds(@NonNull final Collection<OrderId> orderIds)
	{
		return orderBL.getDocumentNosByIds(orderIds);
	}

	public int getSalesOrderLineSeqNo(@NonNull final OrderAndLineId orderAndLineId)
	{
		return orderDAO.getOrderLineById(orderAndLineId).getLine();
	}
}
