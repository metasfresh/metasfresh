package de.metas.order;

import static org.adempiere.model.InterfaceWrapperHelper.loadByIds;

import java.math.BigDecimal;
import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_Order;

import de.metas.bpartner.BPartnerId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IOrderDAO extends ISingletonService
{
	I_C_Order getById(final OrderId orderId);

	/**
	 * Similar to {@link #getById(OrderId)}, but allows to specify which {@link I_C_Order} sub-type the result shall be in.
	 *
	 * @param orderId
	 * @param clazz
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
	 * @param ctx
	 * @param bpartnerId
	 * @return true if given C_BPartner has any orders which are completed/closed.
	 */
	boolean hasCompletedOrders(Properties ctx, int bpartnerId);

	/**
	 * @param order
	 * @return order lines for given order
	 */
	List<I_C_OrderLine> retrieveOrderLines(I_C_Order order);

	/**
	 * Similar to {@link #retrieveOrderLines(I_C_Order)}, but allows to specify which {@link org.compiere.model.I_C_OrderLine} sub-type the result shall be in.
	 *
	 * @param order
	 * @param clazz
	 * @return order lines for given order
	 */
	<T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(I_C_Order order, Class<T> clazz);

	List<I_C_OrderLine> retrieveOrderLines(OrderId orderId);

	/** @return all C_OrderLine_IDs for given order, including the inactive ones */
	List<Integer> retrieveAllOrderLineIds(I_C_Order order);

	/**
	 * @param order
	 * @param line
	 * @param clazz
	 */
	<T extends org.compiere.model.I_C_OrderLine> T retrieveOrderLine(I_C_Order order, int lineNo, Class<T> clazz);

	/**
	 * @return {@link I_M_InOut}s for given order
	 */
	List<I_M_InOut> retrieveInOuts(I_C_Order order);

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
}
