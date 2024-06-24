package de.metas.order;

import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_Order;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.loadByIds;

public interface IOrderDAO extends ISingletonService
{
	I_C_Order getById(final OrderId orderId);

	Map<ExternalId, OrderId> getOrderIdsForExternalIds(final List<ExternalId> externalIds);
	/**
	 * Similar to {@link #getById(OrderId)}, but allows to specify which {@link I_C_Order} sub-type the result shall be in.
	 *
	 * @return order for given orderId
	 */
	<T extends I_C_Order> T getById(final OrderId orderId, Class<T> clazz);

	I_C_OrderLine getOrderLineById(final int orderLineId);

	I_C_OrderLine getOrderLineById(final OrderLineId orderLineId);

	<T extends org.compiere.model.I_C_OrderLine> T getOrderLineById(final OrderLineId orderLineId, Class<T> modelClass);

	Map<OrderAndLineId, I_C_OrderLine> getOrderLinesByIds(Collection<OrderAndLineId> orderAndLineIds);

	default I_C_OrderLine getOrderLineById(@NonNull final OrderAndLineId orderAndLineId)
	{
		return getOrderLineById(orderAndLineId, I_C_OrderLine.class);
	}

	default <T extends I_C_OrderLine> T getOrderLineById(@NonNull final OrderAndLineId orderAndLineId, @NonNull final Class<T> modelClass)
	{
		return getOrderLineById(orderAndLineId.getOrderLineId(), modelClass);
	}

	default <T extends org.compiere.model.I_C_OrderLine> List<T> getOrderLinesByIds(final Collection<OrderAndLineId> orderAndLineIds, final Class<T> modelType)
	{
		return loadByIds(OrderAndLineId.getOrderLineRepoIds(orderAndLineIds), modelType);
	}

	/**
	 * @return order lines for given order
	 */
	List<I_C_OrderLine> retrieveOrderLines(I_C_Order order);

	/**
	 * Similar to {@link #retrieveOrderLines(I_C_Order)}, but allows to specify which {@link org.compiere.model.I_C_OrderLine} sub-type the result shall be in.
	 *
	 * @return order lines for given order
	 */
	<T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(I_C_Order order, Class<T> clazz);

	List<I_C_OrderLine> retrieveOrderLines(OrderId orderId);

	<T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(OrderId orderId, Class<T> modelClass);

	/** @return all C_OrderLine_IDs for given order, including the inactive ones */
	List<OrderAndLineId> retrieveAllOrderLineIds(OrderId orderId);

	<T extends org.compiere.model.I_C_OrderLine> T retrieveOrderLine(I_C_Order order, int lineNo, Class<T> clazz);

	List<I_C_OrderLine> retrieveOrderLinesByOrderIds(Set<OrderId> orderIds);

	List<I_C_OrderLine> retrieveOrderLinesByIds(Set<OrderLineId> orderLineIds);

	/**
	 * @return {@link I_M_InOut}s which have at least one matching between C_OrderLine and M_InOutLine for given order
	 */
	List<I_M_InOut> retrieveInOutsForMatchingOrderLines(I_C_Order order);

	boolean hasInOuts(I_C_Order order);

	/**
	 * Retrieves completed purchase orders with {@link X_C_Order#DELIVERYVIARULE_Pickup} for
	 * <ul>
	 * <li>a specific bPartner-location
	 * <li>and with DatePromised between <code>deliveryDateTime</code> and <code>DeliveryDateTimeMax</code> (inclusively)
	 * </ul>
	 *
	 * @return purchase orders matching the given parameters
	 */
	List<I_C_Order> retrievePurchaseOrdersForPickup(I_C_BPartner_Location bpLoc, Date deliveryDateTime, Date deliveryDateTimeMax);

	Set<UserId> retriveOrderCreatedByUserIds(Collection<Integer> orderIds);

	<T extends I_C_Order> List<T> getByIds(Collection<OrderId> orderIds, Class<T> clazz);

	List<I_C_Order> getByIds(Collection<OrderId> orderIds);

	/**
	 * Get Not Invoiced Shipments
	 *
	 * @return value in accounting currency
	 */
	BigDecimal getNotInvoicedAmt(BPartnerId bpartnerId);

	Stream<OrderId> streamOrderIdsByBPartnerId(BPartnerId bpartnerId);

	void delete(org.compiere.model.I_C_OrderLine orderLine);

	void save(org.compiere.model.I_C_Order order);

	void save(org.compiere.model.I_C_OrderLine orderLine);

	Optional<I_C_Order> retrieveByOrderCriteria(OrderQuery query);

	Set<OrderId> retrieveIdsByOrderLineIds(Set<OrderLineId> orderLineIds);

	Set<OrderLineId> retrieveSOLineIdsByPOLineId(OrderLineId orderLineId);

	Set<OrderId> getSalesOrderIdsViaPOAllocation(OrderId purchaseOrderId);

	void allocatePOLineToSOLine(OrderLineId purchaseOrderLineId, OrderLineId salesOrderLineId);

	I_C_Order assignAsyncBatchId(OrderId orderId, AsyncBatchId asyncBatchId);

	@NonNull
	List<OrderId> getUnprocessedIdsBy(@NonNull ProductId productId);
}
