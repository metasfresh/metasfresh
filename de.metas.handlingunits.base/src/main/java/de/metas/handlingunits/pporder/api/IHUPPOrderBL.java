package de.metas.handlingunits.pporder.api;

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;

/**
 * Provides specific business logic for interconnection between manufacturing order and handling units module.
 *
 * @author tsa
 *
 */
public interface IHUPPOrderBL extends ISingletonService
{
	IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(I_PP_Order ppOrder);

	IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(I_PP_Order_BOMLine ppOrderBOMLine);

	/**
	 * @param ppOrder
	 * @return the created allocation source, based on ppOrder
	 */
	IAllocationSource createAllocationSourceForPPOrder(de.metas.handlingunits.model.I_PP_Order ppOrder);

	IHUPPOrderIssueProducer createIssueProducer();

	IHUQueryBuilder createHUsAvailableToIssueQuery(int productId);

	void processPlanning(String targetPlanningStatus, int ppOrderId);

	boolean canChangePlanningStatus(String fromPlanningStatus, String toPlanningStatus);


}
