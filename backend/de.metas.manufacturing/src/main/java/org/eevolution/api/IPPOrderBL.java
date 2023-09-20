package org.eevolution.api;

import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.material.planning.pporder.OrderQtyChangeRequest;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface IPPOrderBL extends ISingletonService
{
	I_PP_Order getById(@NonNull PPOrderId id);

	void save(I_PP_Order ppOrder);

	I_PP_Order createOrder(PPOrderCreateRequest request);

	Stream<I_PP_Order> streamManufacturingOrders(ManufacturingOrderQuery query);

	void setDefaults(I_PP_Order ppOrder);

	void addDescription(
			I_PP_Order order,
			String description);

	/**
	 * Set QtyBatchSize and QtyBatchs using Workflow and QtyOrdered
	 *
	 * @param order                    MO
	 * @param alwaysUpdateQtyBatchSize if true, will set QtyBatchSize even if is already set (QtyBatchSize!=0)
	 */
	void updateQtyBatchs(
			@NonNull I_PP_Order order,
			boolean alwaysUpdateQtyBatchSize);

	/**
	 * @return true if ANY work was delivered for this MO (i.e. Stock Issue, Stock Receipt, Activity Control Report)
	 */
	boolean isSomethingProcessed(I_PP_Order ppOrder);

	void addQty(OrderQtyChangeRequest request);

	PPOrderQuantities getQuantities(I_PP_Order order);

	/**
	 * Gets the "direct" order line.
	 * <p>
	 * A "direct" order line is the order line that directly triggered this manufacturing order. In other words order line's Product and manufacturing order's Product shall match.
	 *
	 * @param ppOrder manufacturing order
	 * @return direct order line or null
	 */
	@Nullable
	I_C_OrderLine getDirectOrderLine(I_PP_Order ppOrder);

	/**
	 * Propagate Order's AD_Org_ID/Warehouse/Locator to BOM Lines
	 */
	void updateBOMOrderLinesWarehouseAndLocator(I_PP_Order ppOrder);

	/**
	 * Sets manufacturing order's document type(s).
	 *
	 * @throws DocTypeNotFoundException if no document type was found
	 */
	void setDocType(
			I_PP_Order ppOrder,
			PPOrderDocBaseType docBaseType,
			@Nullable String docSubType);

	void closeOrder(PPOrderId ppOrderId);

	/**
	 * Set QtyOrdered=QtyDelivered, QtyClosed=QtyOrdered(old) - QtyDelivered
	 */
	void closeQtyOrdered(I_PP_Order ppOrder);

	void uncloseQtyOrdered(I_PP_Order ppOrder);

	void changeScheduling(PPOrderScheduleChangeRequest request);

	void createOrderRouting(I_PP_Order ppOrder);

	void closeAllActivities(PPOrderId orderId);

	void uncloseActivities(@NonNull PPOrderId orderId);

	Optional<QtyCalculationsBOM> getOpenPickingOrderBOM(PPOrderId pickingOrderId);

	void updateCanBeExportedAfter(@NonNull I_PP_Order order);

	void updateCanBeExportedFrom(@NonNull I_PP_Order ppOrder);

	void updateExportStatus(@NonNull APIExportStatus newExportStatus, @NonNull PInstanceId pinstanceId);

	void setC_OrderLine(@NonNull PPOrderId ppOrderId, @NonNull OrderLineId orderLineId);

	void postPPOrderCreatedEvent(@NonNull I_PP_Order ppOrder);

	void completeDocument(@NonNull I_PP_Order ppOrder);

	Set<ProductId> getProductIdsToIssue(@NonNull PPOrderId ppOrderId);
}
