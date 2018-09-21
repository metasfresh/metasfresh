package org.eevolution.api;

import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Cost;

import de.metas.util.ISingletonService;

public interface IPPOrderCostDAO extends ISingletonService
{

	/**
	 * Delete all {@link I_PP_Order_Cost} records for given manufacturing order.
	 * 
	 * @param ppOrder
	 */
	void deleteOrderCosts(I_PP_Order ppOrder);

}
