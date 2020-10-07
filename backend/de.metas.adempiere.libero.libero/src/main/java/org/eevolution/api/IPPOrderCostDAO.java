package org.eevolution.api;

import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IPPOrderCostDAO extends ISingletonService
{
	boolean hasPPOrderCosts(@NonNull PPOrderId orderId);

	PPOrderCosts getByOrderId(PPOrderId orderId);

	void deleteByOrderId(PPOrderId ppOrderId);

	void save(PPOrderCosts orderCosts);
}
