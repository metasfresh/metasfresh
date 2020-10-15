package org.eevolution.api.impl;

import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order;

import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.Services;
import lombok.NonNull;

public class PPOrderCostBL implements IPPOrderCostBL
{
	@Override
	public void createOrderCosts(@NonNull final I_PP_Order ppOrder)
	{
		new CreatePPOrderCostsCommand(ppOrder)
				.execute();
	}

	@Override
	public PPOrderCosts getByOrderId(@NonNull final PPOrderId orderId)
	{
		final IPPOrderCostDAO orderCostsRepo = Services.get(IPPOrderCostDAO.class);
		return orderCostsRepo.getByOrderId(orderId);
	}

	@Override
	public void deleteByOrderId(@NonNull final PPOrderId orderId)
	{
		final IPPOrderCostDAO orderCostsRepo = Services.get(IPPOrderCostDAO.class);
		orderCostsRepo.deleteByOrderId(orderId);
	}

	@Override
	public void save(@NonNull final PPOrderCosts orderCosts)
	{
		final IPPOrderCostDAO orderCostsRepo = Services.get(IPPOrderCostDAO.class);
		orderCostsRepo.save(orderCosts);
	}
}
