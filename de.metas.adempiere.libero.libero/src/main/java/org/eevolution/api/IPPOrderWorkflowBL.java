package org.eevolution.api;

import org.eevolution.model.I_PP_Order;

import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.ISingletonService;

public interface IPPOrderWorkflowBL extends ISingletonService
{
	void createOrderWorkflow(I_PP_Order ppOrder);

	void closeAllActivities(PPOrderId orderId);
}
