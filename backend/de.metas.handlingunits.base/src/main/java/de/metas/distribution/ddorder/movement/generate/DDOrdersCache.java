package de.metas.distribution.ddorder.movement.generate;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import lombok.NonNull;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.HashMap;

final class DDOrdersCache
{
	private final DDOrderService ddOrderService;

	private final HashMap<DDOrderId, I_DD_Order> ddOrders = new HashMap<>();
	private final HashMap<DDOrderLineId, I_DD_OrderLine> ddOrderLines = new HashMap<>();

	DDOrdersCache(
			@NonNull final DDOrderService ddOrderService)
	{
		this.ddOrderService = ddOrderService;
	}

	public I_DD_Order getById(final DDOrderId ddOrderId)
	{
		return ddOrders.computeIfAbsent(ddOrderId, ddOrderService::getById);
	}

	public I_DD_OrderLine getLineById(final DDOrderLineId ddOrderLineId)
	{
		return ddOrderLines.computeIfAbsent(ddOrderLineId, ddOrderService::getLineById);
	}
}
