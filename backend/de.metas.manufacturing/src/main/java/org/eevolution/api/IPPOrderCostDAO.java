package org.eevolution.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IPPOrderCostDAO extends ISingletonService
{
	boolean hasPPOrderCosts(@NonNull PPOrderId orderId);

	@NonNull
	PPOrderCosts getByOrderId(@NonNull PPOrderId orderId);

	void deleteByOrderId(@NonNull PPOrderId ppOrderId);

	void save(@NonNull PPOrderCosts orderCosts);
}
