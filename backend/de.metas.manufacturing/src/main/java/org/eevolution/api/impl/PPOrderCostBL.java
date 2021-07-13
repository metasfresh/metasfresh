package org.eevolution.api.impl;

import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order;

import org.eevolution.api.PPOrderId;
import de.metas.util.Services;
import lombok.NonNull;

public class PPOrderCostBL implements IPPOrderCostBL
{
	private final IPPOrderCostDAO orderCostsRepo = Services.get(IPPOrderCostDAO.class);

	@Override
	public void createOrderCosts(@NonNull final I_PP_Order ppOrder)
	{
		new CreatePPOrderCostsCommand(ppOrder)
				.execute();
	}

	@Override
	public boolean hasPPOrderCosts(@NonNull final PPOrderId orderId)
	{
		return orderCostsRepo.hasPPOrderCosts(orderId);
	}

	@Override
	public PPOrderCosts getByOrderId(@NonNull final PPOrderId orderId)
	{
		return orderCostsRepo.getByOrderId(orderId);
	}

	@Override
	public void deleteByOrderId(@NonNull final PPOrderId orderId)
	{
		orderCostsRepo.deleteByOrderId(orderId);
	}

	@Override
	public void save(@NonNull final PPOrderCosts orderCosts)
	{
		orderCostsRepo.save(orderCosts);
	}
}
