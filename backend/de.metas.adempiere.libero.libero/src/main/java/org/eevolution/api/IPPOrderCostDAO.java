package org.eevolution.api;

import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.ISingletonService;

public interface IPPOrderCostDAO extends ISingletonService
{
	PPOrderCosts getByOrderId(PPOrderId orderId);

	void deleteByOrderId(PPOrderId ppOrderId);

	void save(PPOrderCosts orderCosts);
}
