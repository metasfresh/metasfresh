package de.metas.handlingunits.pporder.api;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Provides specific business logic for interconnection between manufacturing order and handling units module.
 *
 * @author tsa
 *
 */
public interface IHUPPOrderBL extends ISingletonService
{
	I_PP_Order getById(PPOrderId ppOrderId);

	List<I_PP_Order> getByIds(@NonNull Set<PPOrderId> ppOrderIds);

	/**
	 * Create a {@link IDocumentLUTUConfigurationManager} for HUs that can be received for the given {@code ppOrder}.<br>
	 * If the given {@code ppOrder} does <b>not</b> have a {@link de.metas.handlingunits.model.I_PP_Order#COLUMNNAME_M_HU_LUTU_Configuration_ID PP_Order.M_HU_LUTU_Configuration_ID}, then return a default config.
	 */
	IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(org.eevolution.model.I_PP_Order ppOrder);

	/**
	 * Create a {@link IDocumentLUTUConfigurationManager} for HUs that can be received for the given {@code ppOrderBOMLine}.<br>
	 * If the given {@code ppOrderBOMLine} does <b>not</b> have a {@link de.metas.handlingunits.model.I_PP_Order_BOMLine#COLUMNNAME_M_HU_LUTU_Configuration_ID PP_Order_BOMLine.M_HU_LUTU_Configuration_ID}, then return a default config.
	 */
	IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(I_PP_Order_BOMLine ppOrderBOMLine);

	/**
	 * @return the created allocation source, based on ppOrder
	 */
	IAllocationSource createAllocationSourceForPPOrder(de.metas.handlingunits.model.I_PP_Order ppOrder);

	HUPPOrderIssueProducer createIssueProducer(PPOrderId ppOrderId);

	void issueServiceProduct(PPOrderIssueServiceProductRequest request);

	IPPOrderReceiptHUProducer receivingMainProduct(PPOrderId ppOrderId);

	IPPOrderReceiptHUProducer receivingByOrCoProduct(PPOrderBOMLineId orderBOMLineId);

	/**
	 * Create a query builder that retrieves all HUs that
	 * <ul>
	 * <li>contain the given {@code ppOrderBomLine}'s product</li>
	 * <li>are in the BOM line's warehouse</li>
	 * <li>are still active</li>
	 * </lu>
	 */
	IHUQueryBuilder createHUsAvailableToIssueQuery(I_PP_Order_BOMLine ppOrderBomLine);

	void processPlanning(PPOrderPlanningStatus targetPlanningStatus, PPOrderId ppOrderId);

	boolean canChangePlanningStatus(PPOrderPlanningStatus fromPlanningStatus, PPOrderPlanningStatus toPlanningStatus);

	void addAssignedHandlingUnits(I_PP_Order ppOrder, Collection<I_M_HU> hus);

	void addAssignedHandlingUnits(I_PP_Order_BOMLine ppOrderBOMLine, Collection<I_M_HU> hus);

	void closeOrder(PPOrderId ppOrderId);
}
