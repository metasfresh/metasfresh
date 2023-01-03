package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.order.OrderLineId;
import de.metas.product.ResourceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface IPPOrderDAO extends ISingletonService
{
	I_PP_Order getById(PPOrderId ppOrderId);

	<T extends I_PP_Order> T getById(PPOrderId ppOrderId, Class<T> type);

	default List<I_PP_Order> getByIds(Set<PPOrderId> orderIds)
	{
		return getByIds(orderIds, I_PP_Order.class);
	}

	<T extends I_PP_Order> List<T> getByIds(Set<PPOrderId> orderIds, Class<T> type);

	/**
	 * Gets released manufacturing orders based on {@link I_M_Warehouse}s.
	 * 
	 * @return manufacturing orders
	 */
	List<I_PP_Order> retrieveReleasedManufacturingOrdersForWarehouse(WarehouseId warehouseId);

	Stream<I_PP_Order> streamManufacturingOrders(@NonNull ManufacturingOrderQuery query);

	int getLastSeqNoPerOrderDate(@NonNull final I_PP_Order ppOrder);
	/**
	 * @return PP_Order_ID or -1 if not found.
	 */
	PPOrderId retrievePPOrderIdByOrderLineId(final OrderLineId orderLineId);

	void changeOrderScheduling(PPOrderId orderId, Instant scheduledStartDate, Instant scheduledFinishDate);

	Stream<I_PP_Order> streamOpenPPOrderIdsOrderedByDatePromised(ResourceId plantId);

	List<I_PP_Order> retrieveManufacturingOrders(@NonNull ManufacturingOrderQuery query);

	void save(I_PP_Order order);

	void saveAll(Collection<I_PP_Order> orders);

	void exportStatusMassUpdate(@NonNull final Map<PPOrderId, APIExportStatus> exportStatuses);

	IQueryBuilder<I_PP_Order> createQueryForPPOrderSelection(IQueryFilter<I_PP_Order> userSelectionFilter);

	ImmutableList<I_PP_OrderCandidate_PP_Order> getPPOrderAllocations(PPOrderId ppOrderId);

	ImmutableList<I_PP_Order> getByProductBOMId(ProductBOMId productBOMId);
}
