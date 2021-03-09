package org.eevolution.api;

import lombok.NonNull;
import org.eevolution.model.I_PP_Order;

import de.metas.util.ISingletonService;

public interface IPPOrderCostBL extends ISingletonService
{

	/**
	 * Create and save standard costs records for given manufacturing order.
	 * 
	 * These costs will be used for calculating standard costs variances.
	 */
	void createOrderCosts(I_PP_Order ppOrder);

	boolean hasPPOrderCosts(@NonNull PPOrderId orderId);

	PPOrderCosts getByOrderId(PPOrderId orderId);

	void save(PPOrderCosts orderCosts);

	void deleteByOrderId(PPOrderId orderId);
}
