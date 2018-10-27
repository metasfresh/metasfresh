package org.eevolution.api;

import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Cost;

import de.metas.util.ISingletonService;

public interface IPPOrderCostBL extends ISingletonService
{

	/**
	 * Save standard costs records into {@link I_PP_Order_Cost}.
	 * 
	 * These costswill be used for calculating standard costs variances.
	 */
	void createStandardCosts(I_PP_Order ppOrder);

}
