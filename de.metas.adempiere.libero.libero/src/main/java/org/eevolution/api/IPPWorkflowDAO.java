package org.eevolution.api;

import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;

import de.metas.util.ISingletonService;

public interface IPPWorkflowDAO extends ISingletonService
{
	I_AD_Workflow retrieveWorkflowForProduct(I_M_Product product);

	int retrieveWorkflowIdForProduct(I_M_Product product);

}
